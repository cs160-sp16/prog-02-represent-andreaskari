package com.example.andreaskarinam.mylibrary;

import android.graphics.Color;

/**
 * Created by andreaskarinam on 2/29/16.
 */
public class Representative {

    public static String DEMOCRAT_COLOR = "#2F80ED";
    public static String REPUBLICAN_COLOR = "#E44A4A";
    public static String INDEPENDENT_COLOR = "#FFFFFF";

    public String rep_name;
    public String party;
    public String email;
    public String website;
    public String last_tweet;
    public String image_name;

    public Representative(String name, String party, String email, String website, String tweet, String image) {
        this.rep_name = name;
        this.party = party;
        this.email = email;
        this.website = website;
        this.last_tweet = tweet;
        this.image_name = image;
    }

    public int getColor() {
        if (this.party.equals("D")) {
            return Color.parseColor(DEMOCRAT_COLOR);
        } else if (this.party.equals("R")) {
            return Color.parseColor(REPUBLICAN_COLOR);
        }
        return Color.parseColor(INDEPENDENT_COLOR);
    }
}