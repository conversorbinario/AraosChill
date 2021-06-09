package com.example.proxecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioIdentificacion extends DialogFragment {


    AutoCompleteTextView xenero;
    AutoCompleteTextView especie_insert;
    Button cancelar;
    Button aceptar;


    private String claveIndiv;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Individuo/");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.formulario_identificar, null);
        builder.setView(alertView);
        xenero = alertView.findViewById(R.id.xeneroId);
        especie_insert= alertView.findViewById(R.id.especieID);
        cancelar = alertView.findViewById(R.id.cancelarID);
        aceptar = alertView.findViewById(R.id.aceptarID);

        final String[] xeneros = MainActivity.bb_dd.getXeneros();
        final String[] especies = MainActivity.bb_dd.getEspecies();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, xeneros);
        xenero.setAdapter(adapter);

        ArrayAdapter<String> adapterEsp = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, especies);

        especie_insert.setAdapter(adapterEsp);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xener = String.valueOf(xenero.getText());
                String specie = String.valueOf(especie_insert.getText());
                try {
                    if (!MainActivity.bb_dd.existeXenero(xener)) {
                        Toast.makeText(getContext(), R.string.nonXen, Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        int pk_xenero = MainActivity.bb_dd.getIdTaxon(xener);
                        if (!MainActivity.bb_dd.existeEspecie(specie, pk_xenero)) {
                            Toast.makeText(getContext(), R.string.nonSp, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    int especie = MainActivity.bb_dd.getIdEspecie(xener, specie);

                    DatabaseReference islandRef = myRef.child(claveIndiv + "/especie");
                    islandRef.setValue(especie);
                    Toast.makeText(getContext(), R.string.identificado, Toast.LENGTH_LONG).show();
                    xenero.setText("", false);
                    especie_insert.setText("", false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //builder.show();
        return builder.create();
    }

    public String getClaveIndiv() {
        return claveIndiv;
    }

    public void setClaveIndiv(String claveIndiv) {
        this.claveIndiv = claveIndiv;
    }
}