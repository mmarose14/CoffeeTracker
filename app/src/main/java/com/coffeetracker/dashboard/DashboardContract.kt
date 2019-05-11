package com.coffeetracker.dashboard

import com.coffeetracker.models.MealItem
import java.util.ArrayList

class DashboardContract {
    interface View {
        fun buildCaffeineList(itemDisplayList: ArrayList<MealItem>)

    }

    interface Presenter {
        fun getUserData()
        fun getMeals()
        fun calculateTimings(itemsList: ArrayList<MealItem>): ArrayList<MealItem>

    }
}