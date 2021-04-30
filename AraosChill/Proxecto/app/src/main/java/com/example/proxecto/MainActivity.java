package com.example.proxecto;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private final int PERMISOINTERNET=5;
    private final int PERMISOD = 10;
    public static Db bb_dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usarSD();
        crearBD();
        SharedPreferences sharedpref = getPreferences(MODE_PRIVATE);
        Boolean procesado = sharedpref.getBoolean("BBDD", false);
        if (!procesado) {
            Log.i("procesado", "SI");
            copiarXml();
            procesarXML2();
        }
        else{
            Log.i("procesado", "NON");
        }

        Button rexistro = findViewById(R.id.novoRex);
        rexistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(getApplicationContext(), Avistamentos.class);
                startActivity(inte);

            }
        });

        Button avistadas =findViewById(R.id.todosAvistamentos);

        avistadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(getApplicationContext(), AmosarAvistadas.class);
                startActivity(inte);
            }
        });

        Button amosarTodas = findViewById(R.id.todsAves);
        amosarTodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int permiso = checkSelfPermission(Manifest.permission.INTERNET);
                    if (permiso == PackageManager.PERMISSION_GRANTED) {
                        Intent activityTodas = new Intent(getApplicationContext(), AmosarTodas.class);
                        startActivity(activityTodas);
                    }else{
                        requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISOINTERNET);

                    }
                } else {
                    Intent activityTodas = new Intent(getApplicationContext(), AmosarTodas.class);
                    startActivity(activityTodas);
                }

            }
        });
        Button outrasAccions = findViewById(R.id.outros);
        outrasAccions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityOutras = new Intent(getApplicationContext(), AxudaIdentificacion.class);
                startActivity(activityOutras);
            }
        });

        //probaDBFirebase();
    }

    public void getStorage(){

    }

    public void usarSD() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permiso = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permiso != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISOD);
            }
        } else {

        }
    }

    public void crearBD(){
        bb_dd=new Db(getApplicationContext());
        bb_dd.db = bb_dd.getWritableDatabase();
        Log.i("BD creada", "BD creada manualmente");
    }

    public void probaDBFirebase(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message2");

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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISOD: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Intent activityTodas = new Intent(getApplicationContext(), AmosarTodas.class);
                    //startActivity(activityTodas);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.permission), Toast.LENGTH_LONG).show();
                }
                return;
            }
            case PERMISOINTERNET: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, getResources().getString(R.string.permission), Toast.LENGTH_LONG).show();
                }
                return;
            }


        }
    }

    public void copiarXml(){
        try {
            InputStreamReader is = null;
            BufferedReader br = null;
            is = new InputStreamReader(getAssets().open("Especies.xml"));
            br = new BufferedReader(is);

            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("Especies.xml",MODE_PRIVATE));
            String linea ="";
            while ((linea=br.readLine())!=null){
                osw.write(linea);

            }
            osw.close();
        } catch(Exception e){
            e.printStackTrace();

        }

    }

    public void procesarXML2() {
        InputStream is = null;
        try {
            String xenero="";
            String especie="";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();


            File xmlProcesar = new File("/data/data/com.example.proxecto/files/Especies.xml");
            Document doc = builder.parse(xmlProcesar);

            Element raiz = doc.getDocumentElement();
            NodeList items = raiz.getElementsByTagName("list");
            Element list = (Element )items.item(0);
            NodeList orders = list.getElementsByTagName("order");
            for (int i = 0; i<orders.getLength();i++){
                    Element order = (Element) orders.item(i);
                    NodeList families = order.getElementsByTagName("family");
                    for (int j = 0; j<families.getLength(); j++){
                        Element family = (Element) families.item(j);
                        NodeList genuses = family.getElementsByTagName("genus");
                        for (int t = 0; t<genuses.getLength();t++){
                            Log.i("iteracion", "" +t + i + j);
                            Element genus = (Element)genuses.item(t);
                            xenero = genus.getElementsByTagName("latin_name").item(0).getTextContent();
                            long id_xenero = bb_dd.add_xen_taxon(new Xenero_taxon(xenero));

                            NodeList especies = genus.getElementsByTagName("species");
                            for (int l =0; l<especies.getLength();l++){
                                Element specie = (Element) especies.item(l);
                                especie = specie.getElementsByTagName("latin_name").item(0).getTextContent();
                                try {
                                    bb_dd.addTipo_ave(new Tipo_ave(especie), id_xenero);
                                } catch (Exception e){
                                    Log.w("ERRO", e.getMessage());
                                    Log.i("INSERCION", xenero + " " + especie);

                                }
                                Log.i("ADD", xenero + " " + especie);
                            }

                        }

                    }

            }

           /* String nomeTag = "";
            String especie = "";
            String xenero = "";
            int i = 0;

                Log.i("Bucle primeiro", "PRIMEIRO");

                 bb_dd.add_xen_taxon(new Xenero_taxon(xenero));
                 int id_xenero = bb_dd.getIdTaxon(xenero); */



        } catch (Exception e) {
            Log.w("erro", e.getMessage());
            e.printStackTrace();
        }
        SharedPreferences sharedpref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putBoolean("BBDD", true);
        editor.commit();
    }

}