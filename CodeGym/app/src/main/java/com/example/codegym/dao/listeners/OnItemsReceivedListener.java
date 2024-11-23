package com.example.codegym.dao.listeners;

import java.util.List;

public interface OnItemsReceivedListener<T> {
    void onItemsReceived(List<T> item);
    void onError(Exception e);
}
