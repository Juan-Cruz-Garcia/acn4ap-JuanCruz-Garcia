package com.example.codegym.listeners;

public interface OnDeleteListener<T> {
    void onDelete(T item, int position);
}