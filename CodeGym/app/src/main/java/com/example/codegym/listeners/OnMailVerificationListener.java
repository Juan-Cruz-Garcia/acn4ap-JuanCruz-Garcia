package com.example.codegym.listeners;

public interface OnMailVerificationListener {
    void onMailAvailable();

    void onMailAlreadyRegistered();

    void onError(Exception e);
}
