package com.example.api_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUser {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
}
