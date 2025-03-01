package com.grupo1.pidh.dto.entrada;

public class LoginRequestEntradaDto {
    private String email;
    private String password;

    public LoginRequestEntradaDto(){}
    public LoginRequestEntradaDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}