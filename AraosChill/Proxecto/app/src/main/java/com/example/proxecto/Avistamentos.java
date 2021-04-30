package com.example.proxecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalTime;

public class Avistamentos extends AppCompatActivity implements Serializable {

    DatePicker dp;

    EditText concello;
    EditText lugar;
    Button seguinte;
    Button velloAvis;
    private long id_avistamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avistamentos);
        lugar = findViewById(R.id.lugar);
        concello = findViewById(R.id.concello);
        dp = findViewById(R.id.dataAvistamento);

        if (savedInstanceState!=null){

            concello.setText(savedInstanceState.getString("Concello"));
            lugar.setText(savedInstanceState.getString("Lugar"));
        }

        seguinte = findViewById(R.id.continuar);

        seguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String conce = String.valueOf(concello.getText()).toLowerCase();
                String lug = String.valueOf(lugar.getText()).toLowerCase();
                if (conce.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.concelloBal, Toast.LENGTH_LONG).show();

                } else if (lug.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), R.string.lugarAvistamento, Toast.LENGTH_LONG).show();

                }else{

                    int dia = dp.getDayOfMonth();
                    int mes = dp.getMonth();
                    int ano = dp.getYear();
                    String data = dia + "/" + mes + "/" + ano;
                    int hora = LocalTime.now().getHour();
                    int minutos = LocalTime.now().getMinute();

                    int segundos = LocalTime.now().getSecond();
                    String momento = hora + ":" + minutos + ":" + segundos;
                    try {
                       // id_avistamento = MainActivity.bb_dd.addAvistamento(new Avistamento(conce, lug, data, momento));
                        Intent individuo = new Intent(getApplicationContext(), Rexistro.class);
                        individuo.putExtra("idAvis", (Serializable) new Avistamento(conce, lug, data, momento));
                        individuo.putExtra("existente", false);

                        startActivity(individuo);
                    } catch (Exception e) {

                    }

                }
            }
        });

        velloAvis = findViewById(R.id.velloAvistamento);
        velloAvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent in = new Intent(getApplicationContext(), VellosAvistamento.class);
                    startActivity(in);
            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        String conce = String.valueOf(concello.getText());
        String lug = String.valueOf(lugar.getText());

        outState.putString("Concello", conce);
        outState.putString("Lugar", lug);

        //dp.updateDate();
    }


}