package com.example.tob.serviceimpl;

import com.example.tob.model.UserModel.Address;
import com.example.tob.model.UserModel.Company;
import com.example.tob.model.UserModel.Geo;
import com.example.tob.model.UserModel.UserModel;
import com.example.tob.model.UserWeatherModel;
import com.example.tob.model.WeatherModel.*;
import com.example.tob.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public ResponseEntity<UserWeatherModel> getUser(final Long id) throws JSONException {
            String user = getJSON("https://jsonplaceholder.typicode.com/users/"+id,100000);
            JSONObject userObj = new JSONObject(user);
            Double lat = userObj.getJSONObject("address").getJSONObject("geo").getDouble("lat");
            Double lng = userObj.getJSONObject("address").getJSONObject("geo").getDouble("lng");
            String weather = getJSON("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&appid=81d89dfc95a387dc3fa059530fb30f7f",1000000);
            JSONObject weatherObject = new JSONObject(weather);
            UserModel userModel = stringToUser(userObj);
            WeatherModel weatherModel = stringToWeather(weatherObject);
            UserWeatherModel userWeatherModel = new UserWeatherModel(userModel,weatherModel);
            return new ResponseEntity<UserWeatherModel>(userWeatherModel, HttpStatus.OK);
    }



    private String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    private UserModel stringToUser(JSONObject userObject) throws JSONException {
        Long id = userObject.getLong("id");
        String name = userObject.getString("name");
        String userName = userObject.getString("username");
        String email = userObject.getString("email");
        String street = userObject.getJSONObject("address").getString("street");
        String suite = userObject.getJSONObject("address").getString("suite");
        String city = userObject.getJSONObject("address").getString("city");
        String zipcode = userObject.getJSONObject("address").getString("zipcode");
        Double lat = userObject.getJSONObject("address").getJSONObject("geo").getDouble("lat");
        Double lng = userObject.getJSONObject("address").getJSONObject("geo").getDouble("lng");
        String phone = userObject.getString("phone");
        String website = userObject.getString("website");
        String companyName = userObject.getJSONObject("company").getString("name");
        String catchPhrase = userObject.getJSONObject("company").getString("catchPhrase");
        String bs = userObject.getJSONObject("company").getString("bs");
        Company company = new Company(companyName,catchPhrase,bs);
        Geo geo = new Geo(lat,lng);
        Address address = new Address(street,suite,city,zipcode,geo);
        UserModel userModel = new UserModel(id,name,userName,email,address,phone,website,company);
        return userModel;
    }
    private WeatherModel stringToWeather(JSONObject weatherObject) throws JSONException {
        Double lon = weatherObject.getJSONObject("coord").getDouble("lon");
        Double lat = weatherObject.getJSONObject("coord").getDouble("lat");
        JSONArray arr = weatherObject.getJSONArray("weather");
        List<Weather> weatherList = new ArrayList<>();
        for(int i=0;i<arr.length();i++){
            Long weatherId = arr.getJSONObject(i).getLong("id");
            String main = arr.getJSONObject(i).getString("main");
            String description = arr.getJSONObject(i).getString("description");
            String icon = arr.getJSONObject(i).getString("icon");
            Weather weather = new Weather(weatherId,main,description,icon);
            weatherList.add(weather);
        }
        String base = weatherObject.getString("base");
        Double temp = weatherObject.getJSONObject("main").getDouble("temp");
        Double feelsLike = weatherObject.getJSONObject("main").getDouble("feels_like");
        Double tempMin = weatherObject.getJSONObject("main").getDouble("temp_min");
        Double tempMax = weatherObject.getJSONObject("main").getDouble("temp_max");
        Double pressure = weatherObject.getJSONObject("main").getDouble("pressure");
        Double humidity = weatherObject.getJSONObject("main").getDouble("humidity");
        Long visibility = weatherObject.getLong("visibility");
        Double speed = weatherObject.getJSONObject("wind").getDouble("speed");
        Integer deg = weatherObject.getJSONObject("wind").getInt("deg");
        Integer all = weatherObject.getJSONObject("clouds").getInt("all");
        Long dt = weatherObject.getLong("dt");

        Long sunrise = weatherObject.getJSONObject("sys").getLong("sunrise");
        Long sunset = weatherObject.getJSONObject("sys").getLong("sunset");
        Integer timezone = weatherObject.getInt("timezone");
        Long id = weatherObject.getLong("id");
        String name = weatherObject.getString("name");
        Integer cod = weatherObject.getInt("cod");
        Sys sys = new Sys(sunrise,sunset);
        Clouds clouds = new Clouds(all);
        Wind wind = new Wind(speed,deg);
        Main main1 = new Main(temp,feelsLike,tempMin,tempMax,pressure,humidity);
        Coord coord = new Coord(lon,lat);
        WeatherModel weatherModel = new WeatherModel(coord,weatherList,base,main1,visibility,wind,clouds,dt
        ,sys,timezone,id,name,cod);
        return weatherModel;
    }
}
