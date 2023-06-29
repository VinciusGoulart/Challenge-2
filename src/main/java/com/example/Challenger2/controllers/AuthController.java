package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.DTOs.security.AccountCredentialsDTO;
import com.example.Challenger2.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authServices;

    @PostMapping(value = "/signin")
    public ResponseEntity signIn(@RequestBody @Valid AccountCredentialsDTO data) {

        var token = authServices.signIn(data);

        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return token;
    }
}
