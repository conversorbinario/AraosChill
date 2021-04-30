package com.example.proxecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class VellosAvistamento extends AppCompatActivity {

    TextView seleccionado;
    Button avistamentoSelec;
    Spinner lvSelecion;
    Avistamento avistSelec;
    int avisSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vellos_avistamento);
        seleccionado = findViewById(R.id.avistamentoSelec);
        avistamentoSelec = findViewById(R.id.seleccionadoAvis);
        lvSelecion = findViewById(R.id.avistamentosVellos);

        ArrayList<Avistamento> avis = MainActivity.bb_dd.getTodosAvistamentos();
        if (avis != null) {
            loadListView();
        }


        avistamentoSelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent individuo = new Intent(getApplicationContext(), Rexistro.class);
                individuo.putExtra("idAvis", (Serializable) avistSelec);
                individuo.putExtra("existente", true);

                startActivity(individuo);


            }
        });

        if (savedInstanceState != null) {
            avis = MainActivity.bb_dd.getTodosAvistamentos();
            avisSeleccionado = savedInstanceState.getInt("pk");
            seleccionado.setText(savedInstanceState.getString("avisSt"));
            avistSelec = avis.get(avisSeleccionado);
        }
    }

    public void loadListView() {

        ArrayList<Avistamento> avis = MainActivity.bb_dd.getTodosAvistamentos();
        ArrayList<String> avisSt = new ArrayList<String>();
        for (Avistamento av : avis) {

            avisSt.add(av.toString());
        }
        ArrayList<Integer> avisPK = new ArrayList<Integer>();

        for (Avistamento av : avis) {
            avisPK.add(av.getPkAv());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, avisSt);
        lvSelecion.setAdapter(adaptador);

        lvSelecion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                avistSelec = avis.get(position);
                seleccionado.setText(avisSt.get(position));
                avisSeleccionado = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("pk", avisSeleccionado);
        outState.putString("avisSt", String.valueOf(seleccionado.getText()));


    }
}