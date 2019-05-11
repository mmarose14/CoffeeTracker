package com.coffeetracker.addcaffeine

import com.coffeetracker.util.DataManager
import java.util.*

class AddCaffeinePresenter(view: AddCaffeineContract.View, var dataManager: DataManager) : AddCaffeineContract.Presenter {

    override fun getCaffeineNames(): MutableList<String> {
        return dataManager.getCaffeineNames().toMutableList()

        //val caffeineNames = ArrayList(Arrays.asList<String>(*caffeineNamesArray))
    }


}