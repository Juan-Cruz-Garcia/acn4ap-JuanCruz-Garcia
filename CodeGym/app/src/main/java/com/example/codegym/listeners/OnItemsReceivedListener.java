package com.example.codegym.listeners;

import java.util.List;

public interface OnItemsReceivedListener<T> {
    void onItemsReceived(List<T> item);

    void onError(Exception e);
}
