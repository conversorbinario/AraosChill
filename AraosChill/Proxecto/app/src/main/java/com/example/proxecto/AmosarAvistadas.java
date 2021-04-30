package com.example.proxecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AmosarAvistadas extends AppCompatActivity {

    ImageView cocnello;
    TableLayout taboa;
    int numViews;
    TextView totAv;
    ImageView todas;
    EditText conc;
    Button anterior;
    Button seguinte;
    int dende = 0;
    int ata = 5;

    ArrayList<Avis_Esp> avis = new ArrayList<Avis_Esp>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amosar_avistadas);

        cocnello = findViewById(R.id.buscarConcello);
        conc = findViewById(R.id.edPorConcello);
        todas = findViewById(R.id.buscarTodas);
        taboa = (TableLayout) findViewById(R.id.tabAvist);
        totAv = findViewById(R.id.totalAvis);



        todas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avis = MainActivity.bb_dd.getAvis_indivCTod();
                if (avis.size() == 0) {

                    Toast.makeText(getApplicationContext(), R.string.nonSeTop, Toast.LENGTH_LONG).show();

                } else {
                    int totalAmos = avis.size();
                    if (totalAmos % 5 != 0) {
                        numViews = (totalAmos / 5) + 1;

                    } else {
                        numViews = (totalAmos / 5);
                    }

                    totAv.setText(getResources().getString(R.string.cero) + totalAmos);
                    if (ata > totalAmos) {
                        cargarFilas(dende, totalAmos);
                    } else {
                        cargarFilas(dende, ata);
                    }


                }

            }
        });
        cocnello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String concS = String.valueOf(conc.getText());

                if (concS.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), R.string.campobaleiro, Toast.LENGTH_LONG).show();

                } else {
                    avis = MainActivity.bb_dd.getAvis_indivCon(concS);
                    if (avis.size() == 0) {

                        Toast.makeText(getApplicationContext(), R.string.nonSeTop, Toast.LENGTH_LONG).show();

                    } else {
                        int totalAmos = avis.size();
                        if (totalAmos % 5 != 0) {
                            numViews = (totalAmos / 5) + 1;

                        } else {
                            numViews = (totalAmos / 5);
                        }

                        totAv.setText(getResources().getString(R.string.cero) + totalAmos);
                        if (ata > totalAmos) {
                            cargarFilas(dende, totalAmos);
                        } else {
                            cargarFilas(dende, ata);
                        }


                    }


                }
            }
        });

        anterior = findViewById(R.id.previa);
        seguinte = findViewById(R.id.seguinte);
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dende - 5 >= 0) {
                    taboa.removeAllViews();
                    dende -= 5;
                    ata -= 5;
                    cargarFilas(dende, ata);

                }

            }
        });
        seguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalAmos = avis.size();

                if (dende < totalAmos) {
                    ata += 5;
                    dende += 5;
                    if (ata > totalAmos) {
                        cargarFilas(dende, totalAmos);
                    } else {
                        cargarFilas(dende, ata);
                    }
                }


            }
        });


    }


    public void cargarFilas(int dende, int ata) {
        try {
            taboa.removeAllViews();
            for (int i = dende; i < ata; i++) {
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

                }catch (StringIndexOutOfBoundsException e){
                    tv.setText(R.string.desc);

                }
                //amosamos nome

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                        if (dirAudio!=null){
                            FragmentManager fm = getSupportFragmentManager();
                            ReproducirAudio ra = new ReproducirAudio();
                            ra.setElementoReproducir(dirAudio);
                            ra.setCancelable(false);

                            ra.show(fm, "Reproducindo");
                        }else{

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
                            if (dirFoto!=null){
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
}