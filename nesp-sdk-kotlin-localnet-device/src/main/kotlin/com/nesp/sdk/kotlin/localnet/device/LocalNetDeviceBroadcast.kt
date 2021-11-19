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
    var localNetDeviceFactory: LocalNetDeviceFactory = object : LocalNetDeviceFactory {
        override fun buildDevice(): LocalNetDevice {
            return LocalNetDevice.newBuilder().apply {
                did = ""
                uid = ""
                ip = LocalNetDeviceUtil.localIp()
                udpPort = -1
                flag = -1
                linkedCount = -1
                name = ""
                model = ""
                brand = ""
                board = ""
                systemName = LocalNetDeviceUtil.systemName()
                systemVersionCode = -1
                systemVersionName = LocalNetDeviceUtil.systemVersion()
                systemArch = LocalNetDeviceUtil.systemArch()
                appName = ""
                appVersionCode = -1
                appVersionName = ""
            }.build()
        }
    }
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