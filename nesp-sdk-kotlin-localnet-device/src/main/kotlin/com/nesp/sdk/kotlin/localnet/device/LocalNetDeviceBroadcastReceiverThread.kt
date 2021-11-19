/*
 * Copyright 2021 NESP Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nesp.sdk.kotlin.localnet.device

import com.nesp.sdk.kotlin.localnet.device.protocol.LocalNetDevice
import java.io.ByteArrayInputStream
import java.net.DatagramPacket
import java.net.DatagramSocket

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/19 下午10:32
 * Description:
 **/
internal class LocalNetDeviceBroadcastReceiverThread(
    private val listenPort: Int,
) : Thread() {

    var exit = false
        private set
    private var datagramSocket: DatagramSocket? = null

    var onDeviceReceiveListener: OnDeviceReceiveListener? = null

    override fun run() {
        super.run()
        exit = false
        try {
            datagramSocket = DatagramSocket(listenPort)
            while (!exit) {
                val buffer = ByteArray(512)
                val receiveDatagramPacket = DatagramPacket(buffer, buffer.size)
                datagramSocket?.receive(receiveDatagramPacket)

                val remoteDeviceIpAddress = receiveDatagramPacket.address.hostAddress
                val remoteDeviceUdpPort = receiveDatagramPacket.port

                try {
                    LocalNetDevice.parseFrom(
                        ByteArrayInputStream(
                            receiveDatagramPacket.data,
                            receiveDatagramPacket.offset, receiveDatagramPacket.length
                        )
                    ).toBuilder()
                        .setIp(remoteDeviceIpAddress)
                        .setUdpPort(remoteDeviceUdpPort).build().let {
                            if (it.ip == LocalNetDeviceUtil.localIp()) return@let
                            onDeviceReceiveListener?.onReceive(it)
                        }
                } catch (e: Exception) {
                    println("$TAG.run: exception when parse device information e = $e")
                }
            }
        } catch (e: Exception) {
            println("$TAG.run: exception when receive device e = $e")
        } finally {
            close()
        }
    }

    fun exit() {
        exit = true
        close()
    }

    private fun close() {
        datagramSocket?.close()
        datagramSocket = null
    }


    companion object {

        private const val TAG = "LocalNetDeviceBroadcastReceiverThread"

    }
}