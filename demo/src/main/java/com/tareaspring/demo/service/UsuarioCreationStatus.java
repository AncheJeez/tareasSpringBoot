package com.tareaspring.demo.service;

public enum UsuarioCreationStatus {
    SUCCESS,
    EMAIL_ALREADY_EXISTS,
    USERNAME_ALREADY_EXISTS,
    EMPRESA_NOT_FOUND,
    PASSWORD_REQUIRED,
    PASSWORDS_DO_NOT_MATCH,
    AUTHENTICATION_FAILED
}
