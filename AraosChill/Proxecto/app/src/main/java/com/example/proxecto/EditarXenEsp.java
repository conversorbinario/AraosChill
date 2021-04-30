package com.example.proxecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class EditarXenEsp extends DialogFragment {

    Button b;

    public void setB(Button b) {
        this.b = b;
    }

    private int idPaxaro;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EditText especie = new EditText(getContext());
        especie.setHint(R.string.pDom);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.iden)
                .setView(especie)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();

                    }
                })
                .setPositiveButton(R.string.act, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String xen_esp = String.valueOf(especie.getText());
                        try{
                            String xen = xen_esp.split(" ")[0];
                            String esp =  xen_esp.split(" ")[1];
                            if (!MainActivity.bb_dd.existeXenero(xen)){
                                throw new Exception();
                            }else{

                                int pk_xenero = MainActivity.bb_dd.getIdTaxon(xen);
                                if (!MainActivity.bb_dd.existeEspecie(esp, pk_xenero)) {
                                    throw new Exception();
                                }
                            }

                            int novaEspecie = MainActivity.bb_dd.getIdEspecie(xen, esp);
                            MainActivity.bb_dd.setEspecieIndiv(novaEspecie, idPaxaro);
                            b.performClick();

                        }
                        catch(Exception e){
                            Toast.makeText(getContext(),R.string.nonTP, Toast.LENGTH_LONG).show();


                        }
                    }
                });


        return builder.create();

    }

    public int getIdPaxaro() {
        return idPaxaro;
    }

    public void setIdPaxaro(int idPaxaro) {
        this.idPaxaro = idPaxaro;
    }
}