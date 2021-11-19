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