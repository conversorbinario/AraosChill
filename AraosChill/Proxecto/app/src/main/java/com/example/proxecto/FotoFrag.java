package com.example.proxecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class FotoFrag extends DialogFragment {
    String pathAmosar;
    IndividuoFB individuo;
    public Dialog onCreateDialog(Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_info, null);

        alertView.setPadding(10,10,10,10);
        builder.setView(alertView);

        ImageView image = new ImageView(getContext());
         image = (ImageView) alertView.findViewById(R.id.imaxeFB);


        image.setMaxHeight(R.dimen.imageWidthMax);
        image.setMaxWidth(R.dimen.imageWidthMax);
        image.setImageBitmap(BitmapFactory.decodeFile(pathAmosar));

        TextView tv = (TextView) alertView.findViewById(R.id.tamanhoI);
        TextView tv2 = (TextView) alertView.findViewById(R.id.sexoI);
        TextView tv3 = (TextView) alertView.findViewById(R.id.plumaxeI);

        String wind="";
        String plumaxe="";
        String sexo="";
        try {
             wind = String.valueOf(individuo.getWingspan());
            plumaxe = individuo.getPlumaxe();
            sexo = individuo.getSexo();
        }catch(Exception e){


        }

        tv.setText(" " + wind);
        tv2.setText(" " + sexo);
        tv3.setText(" " + plumaxe);
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
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

    public String getPathAmosar() {
        return pathAmosar;
    }

    public IndividuoFB getIndividuo() {
        return individuo;
    }

    public void setIndividuo(IndividuoFB individuo) {
        this.individuo = individuo;
    }

    public void setPathAmosar(String pathAmosar) {
        this.pathAmosar = pathAmosar;
    }
}
