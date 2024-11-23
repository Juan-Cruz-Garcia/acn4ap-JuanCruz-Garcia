package com.example.codegym.listeners;

public interface OnItemReceivedListener<T> {
    void onItemReceived(T item);
    void onError(Exception e);
}
