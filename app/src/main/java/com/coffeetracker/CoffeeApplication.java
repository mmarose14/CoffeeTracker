package com.coffeetracker;

import android.app.Application;

public class CoffeeApplication extends Application {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
