package com.example.codegym.listeners;

public interface OnLoginListener {
    void onLoginSuccess(boolean e);
    void onLoginError(Exception e);
}
