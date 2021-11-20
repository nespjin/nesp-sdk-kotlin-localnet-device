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

package com.nesp.sdk.kotlin.localnet.device;

import com.nesp.sdk.kotlin.localnet.device.protocol.LocalNetDevice

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/20 上午9:32
 * Description:
 **/

object DefaultLocalNetDeviceFactory : LocalNetDeviceFactory {

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
            systemVersionCode = -1
            systemType = LocalNetDevice.SystemType.UNKNOWN
            systemName = LocalNetDeviceUtil.systemName()
            systemVersionName = LocalNetDeviceUtil.systemVersion()
            systemArch = LocalNetDeviceUtil.systemArch()
            appName = ""
            appVersionCode = -1
            appVersionName = ""
        }.build()
    }
}

