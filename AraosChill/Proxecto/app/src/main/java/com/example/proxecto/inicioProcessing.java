package com.example.proxecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import processing.android.PFragment;
import processing.core.PApplet;

public class inicioProcessing extends AppCompatActivity {
    TextView iniciarSes;
    TextView rexistr;
    EditText usuario, novoUsuario;
    EditText password, novoPassword, novoPasswordCompro;
    TextView aceptar, novoAcpetar;
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
        novoPassword = findViewById(R.id.novoPassword);
        novoPasswordCompro = findViewById(R.id.novoPasswordComp);
        novoUsuario = findViewById(R.id.novoUsuario);
        aceptar = findViewById(R.id.aceptar);
        novoAcpetar=findViewById(R.id.novoAceptar);
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

        probaDBFirebase();

        iniciarSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoPassword.setVisibility(View.GONE);
                novoPasswordCompro.setVisibility(View.GONE);
                novoAcpetar.setVisibility(View.GONE);
                novoUsuario.setVisibility(View.GONE);

                usuario.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                aceptar.setVisibility(View.VISIBLE);

            }
        });

        rexistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                aceptar.setVisibility(View.GONE);

                novoPassword.setVisibility(View.VISIBLE);
                novoPasswordCompro.setVisibility(View.VISIBLE);
                novoAcpetar.setVisibility(View.VISIBLE);
                novoUsuario.setVisibility(View.VISIBLE);

            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuario.getText()==null || password.getText()==null) {
                    Toast.makeText(getApplicationContext(), R.string.avisoUsuarioContra, Toast.LENGTH_LONG).show();
                }else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("usuarios_contrasinais");


                }
                Intent activityPrincipal = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activityPrincipal);

            }
        });

        novoAcpetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (novoUsuario.getText()==null || novoPassword.getText()==null) {
                    Toast.makeText(getApplicationContext(), R.string.avisoUsuarioContra, Toast.LENGTH_LONG).show();
                }

                else if (!novoPassword.getText().toString().equals(novoPasswordCompro.getText().toString())){

                    Toast.makeText(getApplicationContext(), R.string.passwordNonCoincide, Toast.LENGTH_LONG).show();

                }
                else{

                    Toast.makeText(getApplicationContext(), R.string.rexistradoExito, Toast.LENGTH_LONG).show();
                    iniciarSes.performClick();

                }
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

    public void probaDBFirebase(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios_contrasinais");

        myRef.push().setValue("ola");
        myRef.push().setValue("ola");
        myRef.push().setValue("ola");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //   String value = dataSnapshot.getValue(String.class);
                // Log.d("cambioData", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("erro", "Failed to read value.", error.toException());
            }
        });
    }




    @Override
    public void onNewIntent(Intent intent) {

        ///
        super.onNewIntent(intent);
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }
}