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