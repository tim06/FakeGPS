package com.tim.fakegps

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.rule.GrantPermissionRule
import com.tim.fakegps.feature.map.common.commonui.test.TestTags.GOOGLE_MAP
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

// Before run test exec: adb shell settings put secure mock_location 1
class FakeLocationGoogleSuccessTest {

    @get:Rule
    val runtimePermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_MOCK_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION"
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun setFakeLocation() = runTest(UnconfinedTestDispatcher()) {
        val button = composeTestRule.onNodeWithContentDescription("Start")
        button.assertIsNotEnabled()
        composeTestRule.onNodeWithTag(GOOGLE_MAP).performTouchInput {
            click()
        }
        // await google maps reaction
        runBlocking { delay(500) }
        button.assertIsEnabled()

        // action
        button.performClick()
        composeTestRule.onNodeWithContentDescription("Stop").assertExists()

        // reset to initial state
        composeTestRule.onNodeWithContentDescription("Stop").performClick()
        composeTestRule.onNodeWithContentDescription("Start").assertExists()
    }
}