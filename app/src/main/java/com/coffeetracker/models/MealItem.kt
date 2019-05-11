package com.coffeetracker.models

data class MealItem(var formattedOutputString: String = "",
                    var metabolized: Boolean = false,
                    var caffeineLeft: Double = 0.0,
                    var xid: String = "",
                    var created: Long = 0L,
                    var details: MutableMap<Any?, Any?> = mutableMapOf(),
                    var title: String = ""
                    )