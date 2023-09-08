package com.namyesol.tuesday.dto.member;

import javax.validation.constraints.NotBlank;

public class LoginForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
