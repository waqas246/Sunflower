/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.databinding.DataBindingUtil.setContentView
import androidx.window.DeviceState
import androidx.window.WindowLayoutInfo
import androidx.window.WindowManager
import backend.MidScreenFoldBackend
import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding
import java.util.concurrent.Executor

class GardenActivity : AppCompatActivity() {

    open lateinit var windowManager: WindowManager
    private val deviceStateChangeCallback = DeviceStateChangeCallback()
    private val layoutStateChangeCallback = LayoutStateChangeCallback()

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowManager = WindowManager(this, MidScreenFoldBackend())
        DEVICE_STATE = windowManager.deviceState
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)

        windowManager.registerDeviceStateChangeCallback(
                mainThreadExecutor,
                deviceStateChangeCallback
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        windowManager.registerLayoutChangeCallback(mainThreadExecutor, layoutStateChangeCallback)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback(layoutStateChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.unregisterDeviceStateChangeCallback(deviceStateChangeCallback)
    }

    inner class DeviceStateChangeCallback : Consumer<DeviceState> {
        override fun accept(newDeviceState: DeviceState) {
            Log.d("waqas", newDeviceState.toString())
//            updateStateLog(newDeviceState)
//            updateStateAndFeatureViews()
        }
    }

    inner class LayoutStateChangeCallback : Consumer<WindowLayoutInfo> {
        override fun accept(newLayoutInfo: WindowLayoutInfo) {
//            updateStateLog(newLayoutInfo)
//            updateStateAndFeatureViews()
        }
    }

    companion object {
        lateinit var DEVICE_STATE: DeviceState
        lateinit var windowManager: WindowManager
    }

}