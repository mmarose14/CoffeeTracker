package com.coffeetracker.dashboard

import com.coffeetracker.models.MealItem
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

class DashboardPresenterTest {

    lateinit var presenter: DashboardPresenter
    lateinit var view: DashboardContract.View

    @Before
    fun setup() {
        view = mock()
        presenter = DashboardPresenter(view)
    }

    @Test
    fun testCalculateTimingsEmpty() {
        var itemsList = ArrayList<MealItem>()
        var details = mutableMapOf<Any?,Any?>()

        itemsList.add(MealItem("",false,0.0,"",0L,details,""))
        presenter.calculateTimings(itemsList)
    }

    @Test
    fun testCalculateTimings() {
        var itemsList = ArrayList<MealItem>()
        var details = mutableMapOf<Any?,Any?>()

        itemsList.add(MealItem("",false,0.0,"",0L,details,""))



        presenter.calculateTimings(itemsList)
    }
}