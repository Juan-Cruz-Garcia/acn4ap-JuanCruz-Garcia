package com.example.codegym.dao.listeners;

public interface OnItemReceivedListener<T> {
    void onItemReceived(T item);
    void onError(Exception e);
}
