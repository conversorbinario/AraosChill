package com.example.proxecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static com.example.proxecto.MainActivity.bb_dd;

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
                avis = bb_dd.getAvis_indivCTod();
                avistadasFB = new ArrayList<>();
                DatabaseReference myRef = database.getReference("Individuo");
                myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot individuo : dataSnapshot.getChildren()) {
                            //public IndividuoFB(int especie, String sexo, String rutaFoto, String rutaAudio, String plumaxe, int peso) {
                            int esp = Integer.parseInt(individuo.child("especie").getValue().toString());
                            String sexo = individuo.child("sexo").getValue().toString();
                            String plumaxe = individuo.child("plumaxe").getValue().toString();
                            String rutaFoto = individuo.child("rutaFoto").getValue().toString();
                            String rutaAudio = individuo.child("rutaAudio").getValue().toString();
                            int peso = Integer.parseInt(individuo.child("peso").getValue().toString());
                            //falta meter todo en una rray
                            avistadasFB.add(new IndividuoFB(esp, sexo, rutaFoto, rutaAudio, plumaxe, peso));
                        }
                        long totalAmos = dataSnapshot.getChildrenCount();
                        if (totalAmos == 0) {
                            Toast.makeText(getApplicationContext(), R.string.nonSeTop, Toast.LENGTH_LONG).show();

                        } else {

                            if (totalAmos % 5 != 0) {
                                numViews = ((int) totalAmos / 5) + 1;

                            } else {
                                numViews = ((int) totalAmos / 5);
                            }

                            totAv.setText(getResources().getString(R.string.cero) + totalAmos);
                            if (ata > totalAmos) {
                                cargarFilas(dende, (int) totalAmos);
                            } else {
                                cargarFilas(dende, ata);
                            }

                        }
                    }
                });

            }
        });

    }
}