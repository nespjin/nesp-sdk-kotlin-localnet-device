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

import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/19 下午11:40
 * Description:
 **/
object LocalNetDeviceUtil {

    @JvmStatic
    fun localIp(): String {
        try {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface = networkInterfaces.nextElement()
                val inetAddressesEnum = networkInterface.inetAddresses
                while (inetAddressesEnum.hasMoreElements()) {
                    val inetAddress = inetAddressesEnum.nextElement()
                    if (inetAddress is Inet4Address && !inetAddress.isLoopbackAddress) {
                        return inetAddress.hostAddress
                    }
                }
            }
        } catch (e: SocketException) {
            try {
                return Inet4Address.getLocalHost().hostAddress
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }
        }

        return "0.0.0.0"
    }


    @JvmStatic
    fun systemArch(): String = System.getProperty("os.arch", "")

    @JvmStatic
    fun systemName(): String = System.getProperty("os.name", "")

    @JvmStatic
    fun systemVersion(): String = System.getProperty("os.version", "")


}