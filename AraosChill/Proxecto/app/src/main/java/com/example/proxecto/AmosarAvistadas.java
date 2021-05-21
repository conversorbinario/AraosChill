package com.example.proxecto;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

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
                int totalAmos = avis.size();
                totalAmos = MainActivity.bb_dd.getNumeroAvesAvistadasFB();
                if (avis.size() == 0) {

                    Toast.makeText(getApplicationContext(), R.string.nonSeTop, Toast.LENGTH_LONG).show();

                } else {
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
                    dende += 4;
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
                         /*   FragmentManager fm = getSupportFragmentManager();
                            ReproducirAudio ra = new ReproducirAudio();
                            ra.setElementoReproducir(dirAudio);
                            ra.setCancelable(false);

                            ra.show(fm, "Reproducindo"); */
                            descargarAudioFireBase();
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
                                descargarFotoFirebase();

                            }

                      /*  FragmentManager fm = getSupportFragmentManager();
                        FotoFrag ff = new FotoFrag();
                        ff.setPathAmosar(finalLocalFile.getAbsolutePath());
                        ff.setCancelable(false);
                        ff.show(fm, "Imaxe"); */
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void descargarAudioFireBase(){


        StorageReference islandRef = storageRef.child("audios/-Ma4Xx8YvodQUWL_J9s8_-Ma4Xx9-X7QLy0ZL8GQv.mp3");

        File localFile = null;
        try {
            localFile = File.createTempFile("audio", "mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File finalLocalFile = localFile;
        File finalLocalFile1 = localFile;
        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                FragmentManager fm = getSupportFragmentManager();
                ReproducirAudio ra = new ReproducirAudio();
                ra.setElementoReproducir(finalLocalFile1.getAbsolutePath());
                ra.setCancelable(false);

                ra.show(fm, "Reproducindo");}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void descargarFotoFirebase(){


        StorageReference islandRef = storageRef.child("imagenes/-Ma4X_45TwgfeOJ3tPwv_-Ma4X_4amY0p-TOFx9wF");

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File finalLocalFile = localFile;
        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                FragmentManager fm = getSupportFragmentManager();
                FotoFrag ff = new FotoFrag();
                ff.setPathAmosar(finalLocalFile.getAbsolutePath());
                ff.setCancelable(false);
                ff.show(fm, "Imaxe");            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}