package com.tareaspring.demo.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordChangeDto {
    @NotBlank
    @Size(min = 6, max = 30)
    private String password;

    @NotBlank
    private String confirmPassword;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}