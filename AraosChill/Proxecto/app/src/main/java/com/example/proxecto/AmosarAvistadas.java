package com.example.proxecto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.proxecto.MainActivity.bb_dd;

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
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    ArrayList<IndividuoFB> avistadasFB;
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
                avis = bb_dd.getAvis_indivCTod();
                avistadasFB = new ArrayList<>();
                DatabaseReference myRef = database.getReference("Individuo");
                myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot individuo : dataSnapshot.getChildren()) {
                            //public IndividuoFB(int especie, String sexo, String rutaFoto, String rutaAudio, String plumaxe, int peso) {
                            int esp = Integer.parseInt(individuo.child("especie").getValue().toString());
                            String sexo = individuo.child("sexo").getValue().toString();
                            String plumaxe = individuo.child("plumaxe").getValue().toString();
                            String rutaFoto = individuo.child("rutaFoto").getValue().toString();
                            String rutaAudio = individuo.child("rutaAudio").getValue().toString();
                            int peso = Integer.parseInt(individuo.child("peso").getValue().toString());
                            //falta meter todo en una rray
                            avistadasFB.add(new IndividuoFB(esp, sexo, rutaFoto, rutaAudio, plumaxe, peso));
                        }
                        long totalAmos = dataSnapshot.getChildrenCount();
                        if (totalAmos == 0) {
                            Toast.makeText(getApplicationContext(), R.string.nonSeTop, Toast.LENGTH_LONG).show();

                        } else {

                            if (totalAmos % 5 != 0) {
                                numViews = ((int) totalAmos / 5) + 1;

                            } else {
                                numViews = ((int) totalAmos / 5);
                            }

                            totAv.setText(getResources().getString(R.string.cero) + totalAmos);
                            if (ata > totalAmos) {
                                cargarFilas(dende, (int) totalAmos);
                            } else {
                                cargarFilas(dende, ata);
                            }

                        }
                    }
                });

            }
        });
        cocnello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String concS = String.valueOf(conc.getText());

                if (concS.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), R.string.campobaleiro, Toast.LENGTH_LONG).show();

                } else {
                    avis = bb_dd.getAvis_indivCon(concS);
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
                int totalAmos = avistadasFB.size();

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
                    int numEsp = avistadasFB.get(i).getEspecie();
                    ;
                    Xenero_Especie xe = bb_dd.getXeneroEspecieFB(numEsp);
                    if (xe!=null)
                    tv.setText(xe.getXenero() + " " + xe.getEspecie());
                    else{
                        tv.setText(R.string.ufo);
                    }
                } catch (StringIndexOutOfBoundsException e) {
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
                String localizacionAudio = avistadasFB.get(i).getRutaAudio();
                canto.setTag(avistadasFB.get(i));

                fila.addView(canto);
                canto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IndividuoFB indi = (IndividuoFB)canto.getTag();
                        String dirAudio = indi.getRutaAudio();
                Xenero_Especie xep = bb_dd.getXeneroEspecieFB(indi.getEspecie());
                        if (!dirAudio.equalsIgnoreCase("")) {
                            if (xep!=null){
                                descargarAudioFireBase(dirAudio, xep.getXenero(), xep.getEspecie());


                            }
                                else {
                                descargarAudioFireBase(dirAudio, "", "");
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), R.string.audioNonTopado, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                fila.addView(foto);
                String direFoto = avistadasFB.get(i).getRutaFoto();
                //foto.setTag(direFoto);
                foto.setTag(avistadasFB.get(i));
                foto.setText(R.string.fot);
                foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // String dirFoto = (String) foto.getTag();
                        IndividuoFB dirFoto = (IndividuoFB) foto.getTag();
                        if (dirFoto != null) {
                            descargarFotoFirebase(dirFoto);

                        }

                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void descargarAudioFireBase(String ruta, String xenero, String especie) {


        StorageReference islandRef = storageRef.child("audios/"+ruta+".mp3");

        File localFile = null;
        try {
            localFile = File.createTempFile("audio", "mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File finalLocalFile1 = localFile;
        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                FragmentManager fm = getSupportFragmentManager();
                ReproducirAudio ra = new ReproducirAudio();
                ra.setElementoReproducir(finalLocalFile1.getAbsolutePath());
                ra.setXenero(xenero);
                ra.setEspecie(especie);
                ra.setCancelable(false);

                ra.show(fm, "Reproducindo");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

   // public void descargarFotoFirebase(String ruta) {
     public void descargarFotoFirebase(IndividuoFB individuo) {


       // StorageReference islandRef = storageRef.child("imagenes/"+ruta);
         StorageReference islandRef = storageRef.child("imagenes/"+individuo.getRutaFoto());

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
                ff.setIndividuo(individuo);
                ff.setPathAmosar(finalLocalFile.getAbsolutePath());
                ff.setCancelable(false);
                ff.show(fm, "Imaxe");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


}