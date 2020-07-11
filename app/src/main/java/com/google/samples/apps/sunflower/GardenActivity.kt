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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.window.DeviceState
import androidx.window.DeviceState.POSTURE_UNKNOWN
import androidx.window.WindowManager
import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding
import com.google.samples.apps.sunflower.databinding.ActivityGardenFoldableBinding
import com.google.samples.apps.sunflower.databinding.ActivityGardenFoldableBindingImpl

class GardenActivity : AppCompatActivity() {

    private lateinit var windowManager: WindowManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowManager = WindowManager(this, null)
        deviceState = windowManager.deviceState
        if (windowManager.deviceState.posture == POSTURE_UNKNOWN) {
            setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
        } else {
            setContentView<ActivityGardenFoldableBinding>(this, R.layout.activity_garden_foldable)
        }
    }

    companion object {
        //access this globally
        var deviceState: DeviceState? = null

    }
}