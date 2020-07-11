/*
 * Copyright 2019 Google LLC
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

package com.google.samples.apps.sunflower.adapters

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.window.DeviceState
import androidx.window.DeviceState.POSTURE_OPENED
import androidx.window.DeviceState.POSTURE_UNKNOWN
import androidx.window.WindowManager
import com.google.samples.apps.sunflower.GardenActivity
import com.google.samples.apps.sunflower.GardenFragment
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.foldableSupport.PlantListPortrait
import java.util.concurrent.Executor

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

// device state
const val MY_GARDEN_OPENED = 3

class SunflowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    val deviceStateChangeCallback = DeviceStateChangeCallback()
    val handler = Handler(Looper.getMainLooper())
    val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    //  lateinit var windowManager: WindowManager
    val windowManager = WindowManager(fragment.requireContext(), null)

    init {
        windowManager.registerDeviceStateChangeCallback(
                mainThreadExecutor,
                deviceStateChangeCallback
        )
    }

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    //If posture is unknown then it is not a foldable device...?
    private val tabFragmentsCreators: Map<Int, () -> Fragment> =
            if (GardenActivity.deviceState?.posture != POSTURE_UNKNOWN) {
        mapOf(
                MY_GARDEN_PAGE_INDEX to { GardenFragment() },
                PLANT_LIST_PAGE_INDEX to { PlantListPortrait() }
        )
    } else {
        mapOf(
                MY_GARDEN_PAGE_INDEX to { GardenFragment() },
                PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
        )
    }


    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    inner class DeviceStateChangeCallback : Consumer<DeviceState> {
        override fun accept(newDeviceState: DeviceState) {
            Log.d("waqas", newDeviceState.toString())
        }
    }
}