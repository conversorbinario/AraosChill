package com.example.proxecto;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NomeVulgar extends DialogFragment {
    String especieRenomear;
    IndividuoFB individuo;
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.info_especie, null);

        alertView.setPadding(10,10,10,10);
        builder.setView(alertView);

         TextView xenEsp= alertView.findViewById(R.id.titNomeEsp);
        EditText nomeEsp = (EditText) alertView.findViewById(R.id.nomeVulgar);
        EditText nomeZoa = (EditText) alertView.findViewById(R.id.zoaNomeVulgar);

        xenEsp.setText(especieRenomear);

        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        builder.setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                    DatabaseReference myRef = database.getReference("Especie_nome");
            String xen = String.valueOf(xenEsp.getText());
                final boolean[] semaforo = {true};
                    DatabaseReference fillo = myRef.child(String.valueOf(xen));
                    fillo.child("nome").setValue(String.valueOf(nomeEsp.getText()));
                   // fillo.setValue(String.valueOf(nomeEsp.getText()) + " " + String.valueOf(xenEsp.getText()));
                Toast.makeText(getActivity().getApplicationContext(), R.string.gardado, Toast.LENGTH_LONG).show();

                fillo.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                fillo.child("area").setValue(String.valueOf(nomeZoa.getText()));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            }
        });


        return  builder.create();

    }

    public String getEspecieRenomear() {
        return especieRenomear;
    }

    public void setEspecieRenomear(String especieRenomear) {
        this.especieRenomear = especieRenomear;
    }

    public IndividuoFB getIndividuo() {
        return individuo;
    }

    public void setIndividuo(IndividuoFB individuo) {
        this.individuo = individuo;
    }

}
