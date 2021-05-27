package com.example.proxecto;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private final int PERMISOINTERNET=5;
    private final int PERMISOD = 10;
    public static Db bb_dd;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usarSD();
        crearBD2();
        SharedPreferences sharedpref = getPreferences(MODE_PRIVATE);
        Boolean procesado = sharedpref.getBoolean("BBDD", false);
        if (!procesado) {
            Log.i("procesado", "SI");
           // copiarXml();
            // procesarXML2();
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

        try {
           // MainActivity.bb_dd.existeXeneroFB("Corvus");
        }catch(Exception e){
            e.printStackTrace();
        }
        getAvis_indivCTodFB();
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


    public void crearBD2(){
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + "BaseDatosPaxaros.db";
        File file = new File(bddestino);
        if (file.exists()) {
            bb_dd = new Db(getApplicationContext());
            bb_dd.getReadableDatabase();
            return; // XA EXISTE A BASE DE DATOS
        }

        String pathbd = "/data/data/" + getPackageName()
                + "/databases/";
        File filepathdb = new File(pathbd);
        filepathdb.mkdirs();

        InputStream inputstream;
        try {
            inputstream = getAssets().open("BaseDatosPaxaros.db");
            OutputStream outputstream = new FileOutputStream(bddestino);

            int tamread;
            byte[] buffer = new byte[2048];

            while ((tamread = inputstream.read(buffer)) > 0) {
                outputstream.write(buffer, 0, tamread);
            }

            inputstream.close();
            outputstream.flush();
            outputstream.close();
            Toast.makeText(getApplicationContext(), "BASE DE DATOS COPIADA", Toast.LENGTH_LONG).show();
            bb_dd = new Db(getApplicationContext());
            bb_dd.getReadableDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public void crearBD(){
       // bb_dd=new Db(getApplicationContext());

               // bb_dd=new Db(getApplicationContext(), "BaseDatosPaxaros.db", null, 1);

        bb_dd.db = bb_dd.getWritableDatabase();
        Log.i("BD creada", "BD creada manualmente");
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


                          //  String xeneroFB = bb_dd.add_xen_taxonFB(new Xenero_taxonFB(xenero));

                            NodeList especies = genus.getElementsByTagName("species");
                            for (int l =0; l<especies.getLength();l++){
                                Element specie = (Element) especies.item(l);
                                especie = specie.getElementsByTagName("latin_name").item(0).getTextContent();
                                try {
                                    bb_dd.addTipo_ave(new Tipo_ave(especie), id_xenero);
                                //    String espFB = bb_dd.addTipo_aveFB(new Tipo_AveFB(xeneroFB, especie));
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


    public ArrayList<Avis_Esp> getAvis_indivCTodFB() {

        ArrayList<Avis_Esp> avis_esp = new ArrayList<Avis_Esp>();
        int id_xenero = -1;
        //  Cursor cursor = db.rawQuery("select  AV.CONCELLO, AV.NOME_SITIO, AV.DATA, I.ID_INDIVIDUO, AI.FOTO, AI.AUDIO from AVISTAMENTO_INDIVIDUOS as AI inner join INDIVIDUOS as I on AI.INDIVIDUO=I.ID_INDIVIDUO inner join AVISTAMENTOS as AV on AV.ID_AVISTAMENTO=AI.AVISTAMENTO", null);

        DatabaseReference myRef = database.getReference("Individuo");
        DatabaseReference myRefIndivAv = database.getReference("Individuos_Avistamentos");
        final DatabaseReference[] lugarNodo = new DatabaseReference[1];

        Task<DataSnapshot> individuos = myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                for (DataSnapshot indiv: dataSnapshot.getChildren()){
                    String pkFBIndiv = indiv.getKey();
                    lugarNodo[0] =  myRefIndivAv.child(pkFBIndiv).child("Lugar");
                      lugarNodo[0].get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            final String[] lugarValue = new String[1];

                            //lugarValue sale a null, debugea y veras
                            lugarValue[0]= (String) dataSnapshot.getValue();
                            String v = indiv.child("especie").getValue().toString();
                            int numero_especie = Integer.parseInt(indiv.child("especie").getValue().toString());
                            Xenero_Especie xe = bb_dd.getXeneroEspecieFB(numero_especie);
                            if (xe!=null)
                                Log.i("RESULTADO", lugarValue[0] + "|||" + xe.getXenero() + xe.getEspecie());
                            else
                                Log.w("ResultadoAnomalo", indiv.toString());
                        }
                    });


                }

            }
        });


        /*        int numero_especie = (int) indiv.child("especie").getValue();
                    String xenero_esp = bb_dd.getXeneroEspecie(numero_especie);
                    Log.i("RESULTADO", lugarValue[0] + " " + xenero_esp);
*/

      /*  for (DataSnapshot indiv : individuos.getChildren()){
            String pkFBIndiv = indiv.getKey();
            lugarNodo=  myRefIndivAv.child(pkFBIndiv).child("Lugar");
            lugarValue= (String) lugarNodo.get().getResult().getValue();

            int numero_especie = (int) indiv.child("especie").getValue();
            String xenero_esp = getXeneroEspecie(numero_especie);
            Log.i("RESULTADO", lugarValue + " " + xenero_esp);
        }
 */
        /*if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            while (!cursor.isAfterLast()) {
                String conce = cursor.getString(0);
                String nomeSitio = cursor.getString(1);
                String data = cursor.getString(2);
                int idINdiv = cursor.getInt(3);
                String xen_esp = getXeneroEspecie(idINdiv);
                String dir_foto = cursor.getString(4);
                String dir_audio = cursor.getString(5);
                avis_esp.add(new Avis_Esp(conce, nomeSitio, data, xen_esp, dir_foto, dir_audio));
                cursor.moveToNext();
            }

        }
        cursor.close();*/
        return avis_esp;
    }

}