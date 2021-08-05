package com.example.tob.service;

import com.example.tob.model.UserWeatherModel;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public interface UserService {

    public ResponseEntity<UserWeatherModel> getUser(final Long id) throws JSONException;

}
