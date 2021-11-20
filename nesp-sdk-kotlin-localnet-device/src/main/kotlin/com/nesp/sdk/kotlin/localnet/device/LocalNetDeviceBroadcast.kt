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

import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/19 下午10:16
 * Description:
 *
 * Broadcast own device information to all other LAN devices
 *
 **/
class LocalNetDeviceBroadcast {

    private var datagramSocket: DatagramSocket? = null
    private var broadcastTimer: Timer? = null

    @SuppressWarnings
    var localNetDeviceFactory: LocalNetDeviceFactory = DefaultLocalNetDeviceFactory
    var pause = false

    init {
        datagramSocket = DatagramSocket()
    }

    fun start() {
        broadcastTimer = Timer()
        broadcastTimer?.schedule(object : TimerTask() {
            override fun run() {
                if (!pause) sendBroadcast()
            }
        }, 0, LocalNetConfig.BROADCAST_PERIOD)
    }

    private fun sendBroadcast() {
        val localNetDevice = localNetDeviceFactory.buildDevice()
        val bytes = localNetDevice.toByteArray()
        val requestDatagramPacket = DatagramPacket(bytes, bytes.size)
        try {
            requestDatagramPacket.address = InetAddress.getByName("255.255.255.255")
            requestDatagramPacket.port = LocalNetConfig.UDP_LISTEN_PORT
            if (!pause) {
                datagramSocket?.send(requestDatagramPacket)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        broadcastTimer?.cancel()
    }

    companion object {

        private const val TAG = "LocalNetBroadcast"

        @JvmStatic
        val shared: LocalNetDeviceBroadcast by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalNetDeviceBroadcast()
        }
    }
}