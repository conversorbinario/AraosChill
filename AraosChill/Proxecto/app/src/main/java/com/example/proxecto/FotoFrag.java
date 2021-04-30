package com.example.proxecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

public class FotoFrag extends DialogFragment {
    String pathAmosar;
    public Dialog onCreateDialog(Bundle savedInstanceState){
        ImageView image = new ImageView(getContext());
        image.setMaxHeight(R.dimen.iamgeMax);
        image.setMaxWidth(R.dimen.imageWidthMax);
        image.setImageBitmap(BitmapFactory.decodeFile(pathAmosar));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Imaxe")
                .setView(image)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                    }
                });

        return builder.create();

    }

    public String getPathAmosar() {
        return pathAmosar;
    }

    public void setPathAmosar(String pathAmosar) {
        this.pathAmosar = pathAmosar;
    }
}
