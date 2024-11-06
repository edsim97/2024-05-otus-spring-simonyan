package ru.otus.hw.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private final String username;

    private final String password;
}
