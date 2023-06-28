package com.example.Challenger2.entities.DTOs.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AccountCredentialsDTO {

    private String username;
    private String password;
}
