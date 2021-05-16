package com.example.proxecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Rexistro extends AppCompatActivity {

    boolean fotoTomada = false;
    final int CAMARA = 1;
    final int AUDIO = 2;
    final int ITNERNET = 3;
    String rutaAudio = "";
    static long id_Avist = -2;
    String rutaFoto = "";
    static long id_individuo;


    Button comeAgain;
    Button foto;
    Button audio;
    EditText xenero;
    EditText especie;
    EditText peso;
    EditText tamanho;
    Spinner sp_plumaxe;
    Spinner sexoAv;
    Button reprAudio;
    Button amosarFoto;
    Avistamento avis;
    Boolean avistamentoVello = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        id_Avist = -2;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rexistro);
        foto = findViewById(R.id.foto);
        xenero = findViewById(R.id.xeneroEd);
        tamanho = findViewById(R.id.tamanho);
        especie = findViewById(R.id.especie);
        Button insertar = findViewById(R.id.insertar);
        peso = findViewById(R.id.peso);
        reprAudio = findViewById(R.id.reproAud);
        sp_plumaxe = findViewById(R.id.plumaxe);
        sexoAv = findViewById(R.id.sexo);

        comeAgain = findViewById(R.id.comeAgain);


        comeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_Avist = -2;
                finish();
            }
        });


        amosarFoto = findViewById(R.id.fotoAmos);
        Intent inte = getIntent();
        avis = (Avistamento) inte.getSerializableExtra("idAvis");
        avistamentoVello = inte.getBooleanExtra("existente", false);
        //-3 se o individuo é un novo


        //id_individuo =inte.getIntExtra("idIndiv", -3);
        if (savedInstanceState != null) {
            xenero.setText(savedInstanceState.getString("Xenero"));
            especie.setText(savedInstanceState.getString("Especie"));
            //  avistamentoVello = savedInstanceState.getBoolean("existente");
            // avis = (Avistamento)  savedInstanceState.getSerializable("obxAvis");
            peso.setText(savedInstanceState.getString("peso"));
            tamanho.setText(savedInstanceState.getString("tamanho"));


        }


        amosarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File directorioFoto = new File(Environment.getExternalStorageDirectory() + "/ImaxeTemporal" + "/imaxe");
                if (!directorioFoto.exists()) {
                    Toast.makeText(getApplicationContext(), R.string.sacaFoto, Toast.LENGTH_LONG).show();
                    return;

                }
                FragmentManager fm = getSupportFragmentManager();
                FotoFrag ff = new FotoFrag();
                ff.setPathAmosar(directorioFoto.getAbsolutePath());
                ff.setCancelable(false);
                ff.show(fm, "Foto");
            }
        });
        reprAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                ReproducirAudio ra = new ReproducirAudio();
                File directorioGravacion = new File(Environment.getExternalStorageDirectory() + "/AudioTemporal" + "/audio.mp3");
                if (!directorioGravacion.exists()) {

                    Toast.makeText(getApplicationContext(), R.string.gravaAudio, Toast.LENGTH_LONG).show();
                    return;
                }
                ra.setElementoReproducir(directorioGravacion.getAbsolutePath());
                ra.setXenero(getResources().getString(R.string.aveRece));
                ra.setEspecie(getResources().getString(R.string.aveRex));
                ra.setCancelable(false);
                ra.show(fm, "Audio temporal");
            }
        });


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int permiso = checkSelfPermission(Manifest.permission.CAMERA);
                    if (permiso == PackageManager.PERMISSION_GRANTED) {
                        sacarFoto();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMARA);
                    }
                } else {
                    sacarFoto();
                }
            }
        });

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se non se tomou foto..!
                if (!fotoTomada) {
                    Toast.makeText(getApplicationContext(), R.string.tomarFoto, Toast.LENGTH_LONG).show();
                    return;


                }

                //obvio SO para ver se a cousa esta funcionando
                subirFoto(2, 3);
                String specie = String.valueOf(especie.getText());
                String xen = String.valueOf(xenero.getText());
                // e dicir, se se deixa o campo sen valor, todo ok
                if (specie.equalsIgnoreCase("") && xen.equalsIgnoreCase("")) {

                    String sexAv = (String) sexoAv.getSelectedItem();

                    try {
                        int gramos = 0;
                        try {
                            gramos = Integer.parseInt(String.valueOf(peso.getText()));
                        } catch (NumberFormatException nf) {
                            gramos = 0;
                        }
                        String plumaxe = (String) sp_plumaxe.getSelectedItem();
                        Individuo in = null;
                        // if (id_individuo<0) {
                        if (sexAv.equalsIgnoreCase("")) {
                            in = new Individuo();
                            id_individuo = MainActivity.bb_dd.addIndividuo(in);
                        } else {
                            in = new Individuo(sexAv);
                            id_individuo = MainActivity.bb_dd.addIndividuo(in);
                        }
                        //  }
                        if (id_Avist == -2) {
                            id_Avist = avis.getPkAv();

                            if (avistamentoVello == false) {
                                id_Avist = MainActivity.bb_dd.addAvistamento(avis);
                                avistamentoVello = true;
                            }
                        }
                        String dirAudio = copiarAudio(id_Avist, id_individuo);

                        String dir_foto = copiarFoto(id_Avist, id_individuo);
                        //  try {
                        MainActivity.bb_dd.addAvisIndividuio(id_Avist, id_individuo, dirAudio, dir_foto, gramos, plumaxe);
                       /* }catch(Exception e){

                            Toast.makeText(getApplicationContext(), R.string.indivAvis, Toast.LENGTH_LONG).show();
                            id_individuo=-3;
                            xenero.setText("");
                            especie.setText("");
                            peso.setText("");
                            tamanho.setText("");
                            sp_plumaxe.setSelection(0);
                            sexoAv.setSelection(0);
                            fotoTomada = false;
                            return;

                        } */
                        String xen_esp = MainActivity.bb_dd.getXeneroEspecie(id_individuo);
                        // id_individuo=-3;

                        if (xen_esp.equalsIgnoreCase("")) {
                            xen_esp = getString(R.string.desc);

                        }
                        try {
                            Toast.makeText(getApplicationContext(), avis.getConcello() + " " + avis.getNome_sitio() + " - " + xen_esp, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {


                        }
                        xenero.setText("");
                        especie.setText("");
                        peso.setText("");
                        tamanho.setText("");
                        sp_plumaxe.setSelection(0);
                        sexoAv.setSelection(0);
                        fotoTomada = false;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if ((!(specie.equalsIgnoreCase(""))) && (!(xen.equalsIgnoreCase("")))) {
                    try {
                        //existeXeneroFB("Corvus");
                        if (!MainActivity.bb_dd.existeXenero(xen)) {
                            Toast.makeText(getApplicationContext(), R.string.nonXen, Toast.LENGTH_LONG).show();

                        } else {
                            int pk_xenero = MainActivity.bb_dd.getIdTaxon(xen);
                            if (!MainActivity.bb_dd.existeEspecie(specie, pk_xenero)) {
                                Toast.makeText(getApplicationContext(), R.string.nonSp, Toast.LENGTH_LONG).show();
                            } else {
                                Spinner sspSX = findViewById(R.id.sexo);
                                String sexo = (String) sspSX.getSelectedItem();
                                int especie = MainActivity.bb_dd.getIdEspecie(xen, specie);
                                //if (id_individuo<0) {

                                id_individuo = MainActivity.bb_dd.addIndividuo(new Individuo(sexo), especie);
                                //}
                                if (id_Avist == -2) {
                                    id_Avist = avis.getPkAv();
                                    if (avistamentoVello == false) {

                                        id_Avist = MainActivity.bb_dd.addAvistamento(avis);
                                        avistamentoVello = true;
                                    }
                                }
                                int gramos = 0;
                                try {
                                    gramos = Integer.parseInt(String.valueOf(peso.getText()));
                                } catch (NumberFormatException nf) {
                                    gramos = 0;
                                }
                                String plumaxe = (String) sp_plumaxe.getSelectedItem();
                                String dirAudio = copiarAudio(id_Avist, id_individuo);

                                subirFoto(1, 2);
                                String dir_foto = copiarFoto(id_Avist, id_individuo);
                                //    try {
                                MainActivity.bb_dd.addAvisIndividuio(id_Avist, id_individuo, dirAudio, dir_foto, gramos, plumaxe);
                               /* }catch(Exception e){

                                    Toast.makeText(getApplicationContext(), R.string.indivAvis, Toast.LENGTH_LONG).show();
                                    id_individuo=-3;
                                    xenero.setText("");
                                    Rexistro.this.especie.setText("");
                                    peso.setText("");
                                    tamanho.setText("");

                                    sp_plumaxe.setSelection(0);
                                    sexoAv.setSelection(0);
                                    fotoTomada = false;
                                    return;

                                } */
                                String xen_esp = MainActivity.bb_dd.getXeneroEspecie(id_individuo);
                                //  id_individuo=-3;

                                if (xen_esp.equalsIgnoreCase("")) {
                                    xen_esp = getString(R.string.desc);

                                }
                                try {
                                    Toast.makeText(getApplicationContext(), avis.getConcello() + " " + avis.getNome_sitio() + " - " + xen_esp, Toast.LENGTH_LONG).show();
                                } catch (Exception e) {

                                }
                                xenero.setText("");
                                Rexistro.this.especie.setText("");
                                peso.setText("");
                                tamanho.setText("");

                                sp_plumaxe.setSelection(0);
                                sexoAv.setSelection(0);
                                fotoTomada = false;

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), R.string.xenEsp, Toast.LENGTH_LONG).show();

                }
            }

        });

        audio = findViewById(R.id.audio);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int permisoGravacion = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
                    if (permisoGravacion == PackageManager.PERMISSION_GRANTED) {
                        gravar();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO);
                    }
                }
            }
        });
    }


    //NOMENCLATURA FICHEIRO: avistamentoPK_individuoPK
    //devolve ubicacion na SD
    public String copiarAudio(long avis, long indiv) {
        File fo = null;

        File directorioGravacion = new File(Environment.getExternalStorageDirectory() + "/AudioTemporal" + "/audio.mp3");
        if (!directorioGravacion.exists()) {
            return null;
        }
        try {
            File directorioAvis_Indiv = new File(Environment.getExternalStorageDirectory() + "/ArchivosAraos");
            if (!directorioAvis_Indiv.exists()) {
                directorioAvis_Indiv.mkdirs();
            }

            InputStream in = null;
            fo = new File(directorioAvis_Indiv + "/" + avis + "_" + indiv + "_" + "audio.mp3");
            OutputStream out = null;

            in = new FileInputStream(directorioGravacion.getPath());
            out = new FileOutputStream(fo.getPath());

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            directorioGravacion = new File(Environment.getExternalStorageDirectory() + "/AudioTemporal/audio.mp3");

            directorioGravacion.delete();


        } catch (Exception e) {

            e.printStackTrace();
        }
        return fo.getAbsolutePath();

    }

    public String copiarFoto(long avis, long indiv) {
        File directorioFoto = new File(Environment.getExternalStorageDirectory() + "/ImaxeTemporal" + "/imaxe");
        File fo = null;
        try {
            File directorioAvis_Indiv = new File(Environment.getExternalStorageDirectory() + "/ArchivosAraos");
            if (!directorioAvis_Indiv.exists()) {
                directorioAvis_Indiv.mkdirs();
            }
            InputStream in = null;
            fo = new File(directorioAvis_Indiv + "/" + avis + "_" + indiv + "_" + "imaxe");

            OutputStream out = null;

            in = new FileInputStream(directorioFoto.getPath());
            out = new FileOutputStream(fo.getPath());

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            directorioFoto = new File(Environment.getExternalStorageDirectory() + "/ImaxeTemporal" + "/imaxe");
            directorioFoto.delete();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return fo.getAbsolutePath();
    }


    public void gravar() {
        File directorioGravacion = new File(Environment.getExternalStorageDirectory() + "/AudioTemporal");
        if (!directorioGravacion.exists()) {
            directorioGravacion.mkdirs();
        }

        directorioGravacion = new File(Environment.getExternalStorageDirectory() + "/AudioTemporal" + "/audio.mp3");
        GravarAudio gravacion = new GravarAudio(directorioGravacion);
        FragmentManager fm = getSupportFragmentManager();
        gravacion.show(fm, "Gravacion");

    }

    public void sacarFoto() {
        File directorioFotos = new File(Environment.getExternalStorageDirectory() + "/ImaxeTemporal");
        if (!directorioFotos.exists()) {
            directorioFotos.mkdirs();
        }
        directorioFotos = new File(directorioFotos + "/" + "imaxe");
        Uri photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", directorioFotos);
        Intent inte = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        inte.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(inte, CAMARA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMARA: {
                if (resultCode == Activity.RESULT_OK) {
                    fotoTomada = true;
                }
                return;
            }
        }
    }

    public void subirFoto(int idAv, int Indiv) {

        File rutaFoto = new File(Environment.getExternalStorageDirectory() + "/ImaxeTemporal" + "/imaxe");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(rutaFoto);
        StorageReference riversRef = storageRef.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });


    }

    public void amosar() {


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {

        String xenS = String.valueOf(xenero.getText());

        String espS = String.valueOf(especie.getText());

        String pes = String.valueOf(peso.getText());

        String tama = String.valueOf(tamanho.getText());

        outState.putString("Xenero", xenS);
        outState.putString("Especie", espS);
        //   outState.putBoolean("existente", avistamentoVello);
        //  outState.putSerializable("obxAvis", (Serializable) avis);

        outState.putString("peso", pes);
        outState.putString("tamanho", pes);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMARA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sacarFoto();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.permission), Toast.LENGTH_LONG).show();
                }
                return;
            }
            case AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gravar();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.permission), Toast.LENGTH_LONG).show();
                }
                return;
            }
            case ITNERNET: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, getResources().getString(R.string.permission), Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // id_Avist = -2;
    }


    public void existeXeneroFB(String xenero) {
        xenero = xenero.substring(0, 1).toUpperCase() + xenero.substring(1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Xenero_Taxon");
        //DataSnapshot ds = myRef.child("xenero").
        //  DatabaseReference myRef2 = myRef.child("xenero");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sp : snapshot.getChildren()) {
                    Log.i("Xenero", sp.child("xenero").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    /* @Override
    protected void onStop() {
        super.onPause();
        File directorioFoto = new File(Environment.getExternalStorageDirectory() + "/ImaxeTemporal" + "/imaxe");

        File directorioGravacion = new File(Environment.getExternalStorageDirectory() + "/AudioTemporal/audio.mp3");
        if (directorioGravacion.exists()) {
            directorioGravacion.delete();
        }
        if (directorioFoto.exists()) {
            directorioFoto.delete();
        }

    } */
}