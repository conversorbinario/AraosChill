package com.example.proxecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class DialogAmosarNomesArea extends DialogFragment {
    TableLayout taboaPai;

    private ArrayList<AreaNome> areaNomes;

    public Dialog onCreateDialog(Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.amosar_area_nome, null, false);

        alertView.setPadding(10,10,10,10);
         taboaPai = (TableLayout) alertView.findViewById(R.id.taboaNomeArea);
         taboaPai.removeAllViews();
        crearFilas();

        builder.setView(alertView);



       // ImageView image = new ImageView(getContext());
        //image = (ImageView) alertView.findViewById(R.id.imaxeFB);


        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        return  builder.create();

    }


    public void crearFilas(){
        TableRow filaTit = new TableRow(getContext());
        taboaPai.addView(filaTit);
        TextView areaTitu = new TextView(getContext());
        areaTitu.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        areaTitu.setText("Area");
        TextView nomeTit = new TextView(getContext());
        nomeTit.setText(R.string.nome);
        nomeTit.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        filaTit.addView(nomeTit);
        filaTit.addView(areaTitu);



        for (AreaNome a : areaNomes){
            TableRow fila = new TableRow(getContext());
            taboaPai.addView(fila);
            String area = a.getArea();
            String nome = a.getNome();
            TextView areaTV = new TextView(getContext());
            areaTV.setText(area);
            TextView nomeTV = new TextView(getContext());
            nomeTV.setText(nome);

            fila.addView(nomeTV);
            fila.addView(areaTV);

        }

    }

    public ArrayList<AreaNome> getAreaNomes() {
        return areaNomes;
    }

    public void setAreaNomes(ArrayList<AreaNome> areaNomes) {
        this.areaNomes = areaNomes;
    }
}