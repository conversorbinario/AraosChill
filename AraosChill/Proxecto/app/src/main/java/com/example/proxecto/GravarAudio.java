package com.example.proxecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.io.File;

public class GravarAudio extends DialogFragment {
    
    private MediaRecorder mr;
    public GravarAudio(File dirGravacion) {
        mr = new MediaRecorder();
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);
        mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mr.setMaxDuration(10000);
        mr.setAudioEncodingBitRate(32768);
        mr.setAudioSamplingRate(8000); // No emulador só 8000 coma
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        if (Build.VERSION.SDK_INT >= 26) {
            mr.setOutputFile(dirGravacion);
        }
        try {
            mr.prepare();
        } catch (Exception e) {
            mr.reset();
        }
        mr.start();
    }

    @Override
    public void onSaveInstanceState(Bundle estado) {
        super.onSaveInstanceState(estado);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder venta = new AlertDialog.Builder(getContext());

        venta.setTitle("Gravacion");
        venta.setMessage("Fai click cando desexes parar a gravación");

        venta.setCancelable(false);
        venta.setPositiveButton("Deter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mr.stop();
                mr.release();
                mr=null;
                dismiss();
            }
        });


        return venta.create();
    }
    
}
