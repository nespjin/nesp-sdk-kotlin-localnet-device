/*
 *
 *   Copyright (c) 2021  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
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