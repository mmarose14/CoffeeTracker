package com.coffeetracker.dashboard

import com.coffeetracker.models.MealItem
import java.text.SimpleDateFormat
import java.util.*

class DashboardPresenter constructor(val view: DashboardContract.View) : DashboardContract.Presenter {
    //private var itemsList = ArrayList<MealItem>()
    //private var itemDisplayList = ArrayList<MealItem>()
    private var totalCaffeine = 0.0

    override fun getMeals() {
        //calculateTimings(itemDisplayList)
        //view.buildCaffeineList(itemDisplayList)
    }

    override fun calculateTimings(itemsList: ArrayList<MealItem>): ArrayList<MealItem> {

        var itemDisplayList = ArrayList<MealItem>()

        totalCaffeine = 0.0

        for (item in itemsList) {

            var timeStamp = item.created
            timeStamp *= 1000
            val cal1 = Calendar.getInstance()
            cal1.timeInMillis = timeStamp
            val created = cal1.timeInMillis

            val cal2 = Calendar.getInstance()
            val now = cal2.timeInMillis

            val difference = now - created
            var amountLeft = 0.0
            if (difference < DashboardActivity.HOURS_IN_MILLIS) {
                if (item.details.containsKey("caffeine")) {
                    val caffeine = item.details["caffeine"] as Double
                    val seconds = difference / 1000
                    val minutes = seconds / 60
                    val hours = minutes / 60

                    var metabolized = true
                    if (hours <= 0 && minutes <= 15) {
                        metabolized = false
                    }

                    amountLeft = caffeine * Math.pow(0.5, hours / 5.7)
                    if (metabolized) {
                        totalCaffeine += amountLeft
                    }


                    val outputString = StringBuilder()
                    val targetFormat = SimpleDateFormat("EEEE, MMM dd HH:mm")

                    val cal = Calendar.getInstance()
                    cal.timeZone = TimeZone.getTimeZone("UTC")
                    cal.timeInMillis = timeStamp


                    val dateString = targetFormat.format(cal.time)

                    outputString.append(String.format("%s - ", dateString))
                    outputString.append(String.format(" "))
                    outputString.append(String.format("%s", item.title))
                    outputString.append(System.getProperty("line.separator"))

                    val mealItem = MealItem()
                    mealItem.metabolized = metabolized
                    mealItem.caffeineLeft = amountLeft
                    mealItem.formattedOutputString = outputString.toString()
                    mealItem.xid = item.xid
                    itemDisplayList.add(mealItem)
                }
            }
        }

        return itemDisplayList
    }

    override fun getUserData() {
    }

}