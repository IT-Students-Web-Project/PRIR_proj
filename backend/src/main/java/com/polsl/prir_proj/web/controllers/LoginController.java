package com.polsl.prir_proj.web.controllers;

import com.polsl.prir_proj.config.LoginCredentials;
import com.polsl.prir_proj.models.User;
import com.polsl.prir_proj.services.AuthService;
import com.polsl.prir_proj.web.dtos.UserDto;
import com.polsl.prir_proj.web.errors.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            User registered = authService.register(userDto);
            return new ResponseEntity<String>("Successfully registered user " + registered.getUsername(), HttpStatus.CREATED);
        } catch (UserAlreadyExistException uaeEx) {
            return new ResponseEntity<String>(uaeEx.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public void login(@RequestBody LoginCredentials credentials) {
    }

}
