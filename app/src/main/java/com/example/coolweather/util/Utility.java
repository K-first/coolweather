package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.Country;
import com.example.coolweather.db.Provice;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /*
    解析服务器返回的省级数据
     */
    public static boolean handleProviceResponse(String response) {
        if(!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvice = new JSONArray(response);
                for(int i = 0;i < allProvice.length(); i++) {
                    JSONObject proviceObject = allProvice.getJSONObject(i);
                    Provice provice = new Provice();
                    provice.setProviceName(proviceObject.getString("name"));
                    provice.setProviceCode(proviceObject.getInt("id"));
                    provice.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    解析服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCity = new JSONArray(response);
                for (int i = 0; i < allCity.length(); i++) {
                    JSONObject cityObject = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProviceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    解析服务器返回的县级数据
     */
    public static boolean handleCountryResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCountry = new JSONArray(response);
                for (int i = 0; i < allCountry.length(); i++) {
                    JSONObject countryObject = allCountry.getJSONObject(i);
                    Country country = new Country();
                    country.setCountryName(countryObject.getString("name"));
                    country.setWeatherId(countryObject.getString("weather_id"));
                    country.setCityId(cityId);
                    country.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    将返回的 JSON 数据解析成 Weather 实体类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
