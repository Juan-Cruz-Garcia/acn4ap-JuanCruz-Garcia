package com.example.codegym.singelton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;

public class FirestoreSingleton {
    private static FirebaseFirestore instance;

    public static synchronized FirebaseFirestore getInstance() {
        if (instance == null) {
            FirebaseApp.initializeApp(context);
            instance = FirebaseFirestore.getInstance();
        }
        return instance;
    }
}