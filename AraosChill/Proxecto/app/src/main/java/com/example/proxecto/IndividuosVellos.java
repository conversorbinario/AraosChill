package com.example.proxecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class IndividuosVellos extends AppCompatActivity {
    TextView seleccionado;
    Button indivSelButton;
    Spinner lvSelecion;

    IndoIndiv indivObjSel;
    int indivSelec;
    Avistamento av;
    Boolean avistamentoVello = false;

    ArrayList<IndoIndiv> indivs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuos_vellos);
        seleccionado = findViewById(R.id.indivSeleccionadoED);
        indivSelButton = findViewById(R.id.indivSeleccionado);
        lvSelecion = findViewById(R.id.selecIndivAv);
        Intent inte = getIntent();
        av = (Avistamento) inte.getSerializableExtra("idAvis");
        avistamentoVello = inte.getBooleanExtra("existente", false);

        indivs = MainActivity.bb_dd.getInfoIndiv();
        if (indivs != null) {
            loadListView();
        }

        indivSelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mandamos o avistamento e o individuo
                Intent inte = new Intent(getApplicationContext(), Rexistro.class);
                inte.putExtra("idIndiv", indivSelec);
                inte.putExtra("idAvis", (Serializable) av);
                inte.putExtra("existente", avistamentoVello);
                startActivity(inte);

            }
        });

        if (savedInstanceState != null) {
            indivs = MainActivity.bb_dd.getInfoIndiv();
            indivSelec = savedInstanceState.getInt("pk");
            seleccionado.setText(savedInstanceState.getString("avisSt"));
            indivObjSel = indivs.get(indivSelec);
            av = (Avistamento) inte.getSerializableExtra("obxAvis");
            avistamentoVello = inte.getBooleanExtra("existente", false);
        }
    }

    public void loadListView() {
        indivs = MainActivity.bb_dd.getInfoIndiv();
        ArrayList<Integer> avisPK = new ArrayList<Integer>();

        ArrayList<String> indivStr = new ArrayList<String>();

        for (IndoIndiv ind : indivs) {
            indivStr.add( ind.toString() + " " + MainActivity.bb_dd.getXeneroEspecie(ind.getIdIndi()));
            avisPK.add(ind.getIdIndi());
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, indivStr);
        lvSelecion.setAdapter(adaptador);

        lvSelecion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indivObjSel = indivs.get(position);
                seleccionado.setText(indivStr.get(position));
                indivSelec = indivs.get(position).getIdIndi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("pk", indivSelec);
        outState.putString("avisSt", String.valueOf(seleccionado.getText()));
        outState.putSerializable("obxAvis", (Serializable) av);
        outState.putBoolean("existente", avistamentoVello);
    }
}