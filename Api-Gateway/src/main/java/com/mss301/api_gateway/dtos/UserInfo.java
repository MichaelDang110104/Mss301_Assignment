package com.mss301.api_gateway.dtos;

public class UserInfo {
    private String email;

    private String role;

    public UserInfo(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public UserInfo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
