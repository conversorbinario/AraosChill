package com.example.proxecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;


import androidx.fragment.app.DialogFragment;

public class ReproducirAudio extends DialogFragment {

    MediaPlayer mediaPla;
    private boolean audioLocal;
    private String elementoReproducir;
    private String xenero;
    private String especie;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            mediaPla = new MediaPlayer();

            mediaPla.setDataSource(elementoReproducir);


            mediaPla.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPla.prepare();
            mediaPla.start();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("")
                    .setMessage(getResources().getString(R.string.cantoDe) + " " + xenero + " " + especie)
                    .setPositiveButton(R.string.deter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deterRep();
                        }
                    });
            return builder.create();

        } catch (Exception e) {

        }
        return null;

    }

    public void deterRep() {
        if (mediaPla.isPlaying()) mediaPla.stop();
        if (mediaPla != null) mediaPla.release();
        mediaPla = null;
        dismiss();


    }

    public String getXenero() {
        return xenero;
    }

    public void setXenero(String xenero) {
        this.xenero = xenero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public boolean isAudioLocal() {
        return audioLocal;
    }

    public void setAudioLocal(boolean audioLocal) {
        this.audioLocal = audioLocal;
    }

    public String getElementoReproducir() {
        return elementoReproducir;
    }

    public void setElementoReproducir(String elementoReproducir) {
        this.elementoReproducir = elementoReproducir;
    }
}
