package com.example.proxecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AxudaIdentificacion extends AppCompatActivity {

    Button identificar;
    Button nomesVulgares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axuda_identificacion);
        identificar = findViewById(R.id.identificar);
        nomesVulgares = findViewById(R.id.nomeVulg);

        nomesVulgares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inte = new Intent(getApplicationContext(), AmosarTodas.class);
                inte.putExtra("actividade", true);
                startActivity(inte);

            }
        });

        identificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent amosarNonIdentificadas = new Intent(getApplicationContext(), AmosarAvistadas.class);
                amosarNonIdentificadas.putExtra("identificar", true);
                startActivity(amosarNonIdentificadas);
            }
        });

    }
}