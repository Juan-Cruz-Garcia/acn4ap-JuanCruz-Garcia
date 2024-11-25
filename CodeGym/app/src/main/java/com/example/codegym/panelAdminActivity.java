package com.example.codegym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class panelAdminActivity extends AppCompatActivity {
    private Button PanelEjerciciosButton, PanelUsuariosButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admin);
        PanelEjerciciosButton = findViewById(R.id.PanelEjerciciosButton);
        PanelUsuariosButton = findViewById(R.id.PanelUsuariosButton);

        PanelEjerciciosButton.setOnClickListener(View ->{
            Intent intent = new Intent(panelAdminActivity.this,CrudEjercicosActivity.class);
            startActivity(intent);
        });
        PanelUsuariosButton.setOnClickListener(View ->{
            Intent intent = new Intent(panelAdminActivity.this, CrudUsuariosActivity.class);
            startActivity(intent);
        });


    }
}