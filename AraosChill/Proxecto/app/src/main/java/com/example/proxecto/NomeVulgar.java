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

import androidx.fragment.app.DialogFragment;

public class NomeVulgar extends DialogFragment {
    String especieRenomear;
    IndividuoFB individuo;
    public Dialog onCreateDialog(Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.info_especie, null);

        alertView.setPadding(10,10,10,10);
        builder.setView(alertView);

         TextView xenEsp= alertView.findViewById(R.id.titNomeEsp);
        EditText nomeEsp = (EditText) alertView.findViewById(R.id.nomeVulgar);
        EditText nomeZoa = (EditText) alertView.findViewById(R.id.zoaNomeVulgar);



        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        builder.setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

       /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Imaxe")
                .setView(image)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                    }
                });


        return builder.create();        TextView tv2 = (TextView) alertView.findViewById(R.id.sexoI);

        */
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
