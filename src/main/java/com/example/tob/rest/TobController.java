package com.example.tob.rest;

import com.example.tob.model.UserWeatherModel;
import com.example.tob.service.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TobController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home(){
        return "Hello TOB";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserWeatherModel> getUser(@PathVariable Long id) throws JSONException {
        return userService.getUser(id);
    }
}
