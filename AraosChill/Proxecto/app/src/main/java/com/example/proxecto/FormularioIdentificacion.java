package com.example.proxecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class FormularioIdentificacion extends DialogFragment {


    AutoCompleteTextView xenero;
    AutoCompleteTextView especie;
    Button cancelar;
    Button aceptar;



    public Dialog onCreateDialog(Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.info_especie, null);
        xenero = alertView.findViewById(R.id.xeneroId);
        especie = alertView.findViewById(R.id.especieID);
        cancelar = alertView.findViewById(R.id.cancelarID);
        aceptar = alertView.findViewById(R.id.aceptarID);

        final String [] xeneros=  MainActivity.bb_dd.getXeneros();
        final String [] especies  = MainActivity.bb_dd.getEspecies();

        

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, xeneros);
        xenero.setAdapter(adapter);

        ArrayAdapter<String> adapterEsp = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, especies);

        especie.setAdapter(adapterEsp);



        //builder.show();
        return builder.create();
    }
}