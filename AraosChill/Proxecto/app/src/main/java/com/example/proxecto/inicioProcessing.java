package com.example.proxecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import processing.android.PFragment;
import processing.core.PApplet;

public class inicioProcessing extends AppCompatActivity {
    TextView iniciarSes;
    TextView rexistr;
    EditText usuario;
    EditText password;
    TextView aceptar;
    public static PApplet sketch;
    FrameLayout frame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicioprocessing);
        iniciarSes = findViewById(R.id.iniciarSes);
        rexistr = findViewById(R.id.rexistrarse);
        usuario = findViewById(R.id.usuario);
        password = findViewById(R.id.password);
        aceptar = findViewById(R.id.aceptar);
        frame = findViewById(R.id.layoutSketch);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = null; // the results will be higher than using the activity context object or the getWindowManager() shortcut
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        sketch = new Sketch(screenWidth, screenHeight);
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);

        iniciarSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                aceptar.setVisibility(View.VISIBLE);

            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityPrincipal = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activityPrincipal);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(
                    requestCode, permissions, grantResults);

        }
    }



    @Override
    public void onNewIntent(Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }
}