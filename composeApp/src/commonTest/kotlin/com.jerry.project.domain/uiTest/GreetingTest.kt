package com.jerry.project.domain.uiTest

import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.jerry.project.GreetingCompose
import kotlin.test.Test

class GreetingTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun greetingTest() = runComposeUiTest {

        setContent {
            GreetingCompose()
        }

        // Act: Click the button
        onNodeWithText("Click me!").performClick()
    }
}