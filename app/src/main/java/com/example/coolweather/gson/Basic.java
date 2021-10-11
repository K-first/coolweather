package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("cid")
    public String weatherId;

    @SerializedName("location")
    public String cityName;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTime;
    }
}
