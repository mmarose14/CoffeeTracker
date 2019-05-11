package com.coffeetracker.util

import android.content.res.Resources
import com.coffeetracker.R

class DataManager(var resources: Resources) {

    fun getCaffeineNames(): Array<String> {
        return resources.getStringArray(R.array.caffeine_names)
    }

    fun getCaffeineNameCodes(): Array<String> {
        return resources.getStringArray(R.array.caffeine_name_codes)
    }
}