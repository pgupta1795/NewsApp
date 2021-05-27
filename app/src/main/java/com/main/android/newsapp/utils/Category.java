package com.main.android.newsapp.utils;

import com.main.android.newsapp.R;

public enum Category {
    GENERAL("General"),
    BUSINESS("Business"),
    ENTERTAINMENT("Entertainment"),
    HEALTH("Health"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    TECHNOLOGY("Technology");

    public final String title;

    private static final int[] categoryIcons = {
            R.drawable.ic_headlines,
            R.drawable.ic_business,
            R.drawable.ic_entertainment,
            R.drawable.ic_health,
            R.drawable.ic_science,
            R.drawable.ic_sports,
            R.drawable.ic_tech,
    };


    Category(String title) {
        this.title = title;
    }

    public static String[] getAllCategories(){
        return new String[]{
                Category.GENERAL.name(),
                Category.BUSINESS.name(),
                Category.ENTERTAINMENT.name(),
                Category.HEALTH.name(),
                Category.SCIENCE.name(),
                Category.SPORTS.name(),
                Category.TECHNOLOGY.name()
        };
    }

    public static int[] getAllCategoryIcons(){
        return categoryIcons;
    }
}
