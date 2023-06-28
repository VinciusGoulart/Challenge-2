package com.example.Challenger2.entities.DTOs.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AccountCredentialsDTO {

    @NotBlank(message = "Username can't be blank")
    @NotNull(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password can't be blank")
    @NotNull(message = "Password is mandatory")
    private String password;
}
