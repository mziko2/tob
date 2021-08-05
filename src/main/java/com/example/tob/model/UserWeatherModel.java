package com.example.tob.model;

import com.example.tob.model.UserModel.UserModel;
import com.example.tob.model.WeatherModel.WeatherModel;

public class UserWeatherModel {
    private UserModel userModel;
    private WeatherModel weatherModel;

    public UserWeatherModel(UserModel userModel, WeatherModel weatherModel) {
        this.userModel = userModel;
        this.weatherModel = weatherModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public WeatherModel getWeatherModel() {
        return weatherModel;
    }

    public void setWeatherModel(WeatherModel weatherModel) {
        this.weatherModel = weatherModel;
    }
}
