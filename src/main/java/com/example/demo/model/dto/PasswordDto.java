package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class PasswordDto {

    @NotEmpty(message = "Please enter password")
    @Size(min = 8, message = "At least 8 characters required")
    private String password;

}
