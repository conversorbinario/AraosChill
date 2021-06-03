package com.example.proxecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class AxudaIdentificacion extends AppCompatActivity {

    Button identificar;
    Button nomesVulgares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axuda_identificacion);
        identificar = findViewById(R.id.identificar);
        nomesVulgares = findViewById(R.id.nomeVulg);

        nomesVulgares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inte = new Intent(getApplicationContext(), AmosarTodas.class);
                inte.putExtra("actividade", true);
                startActivity(inte);

            }
        });

        identificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Avis_Esp> avis = new ArrayList<Avis_Esp>();
                avis = MainActivity.bb_dd.getAvis_indivCTodID();

                int nuemFilas = avis.size();
                TableLayout taboa = (TableLayout) findViewById(R.id.taboa_dinamica);
                try {
                    taboa.removeAllViews();
                    for (int i = 0; i < nuemFilas; i++) {
                        Button foto = new Button(getApplicationContext());
                        TableRow fila = new TableRow(getApplicationContext());
                        taboa.addView(fila);
                        TextView tv = new TextView(getApplicationContext());
                        try {
                            String xen_esp = avis.get(i).getXen_esp();
                            String xenSenEsp = xen_esp.substring(0, xen_esp.indexOf(" "));
                            xenSenEsp = xenSenEsp.substring(0, 1).toUpperCase() + xenSenEsp.substring(1);
                            int trimer = xen_esp.indexOf(" ") + 1;
                            String espSenXen = xen_esp.substring(trimer).toLowerCase();
                            tv.setText(xenSenEsp + " " + espSenXen);

                        } catch (StringIndexOutOfBoundsException e) {
                            tv.setText(R.string.desc);

                        }
                        //amosamos nome
                        tv.setTag(avis.get(i).getIdIndiv());

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentManager fm = getSupportFragmentManager();
                                EditarXenEsp ex = new EditarXenEsp();
                                ex.setIdPaxaro((Integer) tv.getTag());
                                ex.setB(identificar);
                                ex.show(fm, "Identificar");

                            }
                        });
                        fila.addView(tv);
                        Button canto = new Button(getApplicationContext());
                        // b.setTag(xenero + " " + especie);
                        canto.setText(R.string.escoitar);
                        String localizacionAudio = avis.get(i).getDir_audio();
                        canto.setTag(localizacionAudio);

                        fila.addView(canto);
                        canto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String dirAudio = (String) canto.getTag();
                                if (dirAudio != null) {
                                    FragmentManager fm = getSupportFragmentManager();
                                    ReproducirAudio ra = new ReproducirAudio();
                                    ra.setElementoReproducir(dirAudio);
                                    ra.setCancelable(false);

                                    ra.show(fm, "Reproducindo");
                                } else {

                                    Toast.makeText(getApplicationContext(), R.string.audioNonTopado, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        fila.addView(foto);
                        //  foto.setCompoundDrawablesWithIntrinsicBounds(R.drawable.camic,  0, 0, 0);
                        String direFoto = avis.get(i).getDir_foto();
                        foto.setTag(direFoto);
                        foto.setText(R.string.fot);
                        //  foto.setWidth(R.dimen.iconSize);
                        // foto.setHeight(R.dimen.iconSize);
                        foto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String dirFoto = (String) foto.getTag();
                                if (dirFoto != null) {
                                    FragmentManager fm = getSupportFragmentManager();
                                    FotoFrag ff = new FotoFrag();
                                    ff.setPathAmosar(dirFoto);
                                    ff.setCancelable(false);
                                    ff.show(fm, "Imaxe");

                                }
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}