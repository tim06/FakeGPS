package com.tim.feature.gmschecker.mock

import com.tim.feature.gmschecker.GmsChecker

class GmsCheckerMock(private val value: Boolean) : GmsChecker {
    override fun isPlayServicesAvailable(): Boolean = value
}