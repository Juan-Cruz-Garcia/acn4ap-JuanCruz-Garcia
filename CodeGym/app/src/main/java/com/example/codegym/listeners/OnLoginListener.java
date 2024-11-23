package com.example.codegym.listeners;

public interface OnLoginListener {
    void onLoginSuccess();
    void onLoginError(Exception e);
}
