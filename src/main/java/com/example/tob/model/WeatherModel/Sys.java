package com.example.tob.model.WeatherModel;

public class Sys {
    private Long sunrise;
    private Long sunset;

    public Sys(Long sunrise, Long sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }
}