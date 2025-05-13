package com.example.api_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUser implements Cloneable{
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;

    @Override
    public RegisterUser clone() {
        try {
            RegisterUser clone = (RegisterUser) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
