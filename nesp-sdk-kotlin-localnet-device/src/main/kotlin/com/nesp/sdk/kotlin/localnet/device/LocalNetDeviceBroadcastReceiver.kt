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
import java.util.*

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/19 下午10:30
 * Description:
 *
 * Receive device information sent by other LAN devices
 *
 **/
class LocalNetDeviceBroadcastReceiver {

    /** cache for local net device */
    private val deviceReceived: Queue<LocalNetDevice> = LinkedList()

    private var receiverThread: LocalNetDeviceBroadcastReceiverThread? = null
    private var notifyReceiveTimer: Timer? = null

    @SuppressWarnings
    val onDeviceReceiveListeners = arrayListOf<OnDeviceReceiveListener>()

    init {
        receiverThread = newReceiveThread()
    }

    fun start() {
        if (receiverThread == null || receiverThread?.exit == false) {
            receiverThread = newReceiveThread()
            receiverThread?.start()
        }

        notifyReceiveTimer = Timer()
        notifyReceiveTimer?.schedule(object : TimerTask() {
            override fun run() {
                deviceReceived.poll()?.let { localNetDevice ->
                    notifyReceiveDevice(localNetDevice)
                }
            }
        }, 0L, LocalNetConfig.BROADCAST_RECEIVE_PERIOD)
    }

    fun stop() {
        receiverThread?.exit()
        receiverThread?.interrupt()

        notifyReceiveTimer?.cancel()
    }

    private fun notifyReceiveDevice(device: LocalNetDevice) {
        onDeviceReceiveListeners.forEach {
            it.onReceive(device)
        }
    }

    private fun newReceiveThread() =
        LocalNetDeviceBroadcastReceiverThread(LocalNetConfig.UDP_LISTEN_PORT).apply {
            isDaemon = true
            onDeviceReceiveListener = object : OnDeviceReceiveListener {
                override fun onReceive(device: LocalNetDevice) {
                    deviceReceived.offer(device)
                }
            }
        }

    companion object {

        private const val TAG = "LocalNetDeviceBroadcastReceiver"

        @JvmStatic
        val shared: LocalNetDeviceBroadcastReceiver by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalNetDeviceBroadcastReceiver()
        }

    }
}