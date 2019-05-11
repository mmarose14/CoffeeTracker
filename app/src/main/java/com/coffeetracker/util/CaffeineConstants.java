package com.coffeetracker.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CaffeineConstants {

    public static final HashMap<String, String> caffeineRanges = new HashMap<>();

    static {
        caffeineRanges.put("NONE", "0 MG");
        caffeineRanges.put("SOME", "1 - 149 MG");
        caffeineRanges.put("MORE", "150 - 299 MG");
        caffeineRanges.put("LOTS", "300 - 449 MG");
        caffeineRanges.put("TONS", "450 + MG");
    }

    public static final String COFFEE = "COFFEE";
    public static final String GREEN_TEA = "GREEN_TEA";
    public static final String COCA_COLA = "COCA_COLA";
    public static final String ESPRESSO = "ESPRESSO";
    public static final String CAPPUCINO = "CAPPUCINO";

    public static final String[] sodas = {"COCA_COLA"};
    public static final String[] coffees = {"COFFEE"};
    public static final String[] espressos = {"ESPRESSO","CAPPUCINO"};

    public static int getCaffeineAmount(String code, String amountCode) {
        switch (code) {
            case COFFEE:
                switch (amountCode) {
                    case "4": return 53;
                    case "8": return 95;
                    case "12": return 150;
                    case "16": return 190;
                }
                break;
            case ESPRESSO:
            case CAPPUCINO:
                switch (amountCode) {
                    case "SINGLE": return 75;
                    case "DOUBLE": return 150;
                }
                break;
            case GREEN_TEA:
                switch (amountCode) {
                    case "8": return 30;
                    case "12": return 45;
                    case "16": return 60;
                }
                break;
            case COCA_COLA:
                switch (amountCode) {
                    case "12": return 34;
                    case "16": return 45;
                    case "21": return 59;
                    case "30": return 85;
                }
                break;
            default:
                return 0;

        }

        return 0;
    }

    public static boolean isSoda(String code) {
        return Arrays.asList(sodas).contains(code);
    }

    public static boolean isCoffee(String code) {
        return Arrays.asList(coffees).contains(code);
    }

    public static boolean isEspresso(String code) {
        return Arrays.asList(espressos).contains(code);
    }
}

