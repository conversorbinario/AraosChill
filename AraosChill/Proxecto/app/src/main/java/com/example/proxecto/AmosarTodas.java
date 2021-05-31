package com.example.proxecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AmosarTodas extends AppCompatActivity {

    ImageView iv ;
    TableLayout taboa;
    //ArrayList<Tipo_ave> todasEspecies;
    ArrayList<Xenero_Especie> todasEspecies;
    int dende = 0;
    int ata = 10;
    Button next;
    Button previous;
    int numViews;
    EditText et;
    private boolean fotoSacada=false;

    int numVista=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amosar_todas);
        iv = findViewById(R.id.ir);
        todasEspecies = new ArrayList<Xenero_Especie>();
        todasEspecies=MainActivity.bb_dd.getTodoXeneroEspecie();
      /*  todasEspecies = new ArrayList<Tipo_ave>();
        todasEspecies = MainActivity.bb_dd.getTodasEspecies(); */
        int numEspecies = todasEspecies.size();
        et=findViewById(R.id.numVista);
        if (numEspecies%10!=0){
            numViews=(numEspecies/10)+1;

        }else{
            numViews=(numEspecies/10);
        }
        et.setHint(numVista + "/"+numViews);

        taboa = (TableLayout) findViewById(R.id.taboa_nova);
        int columnas = 3;
        taboa.removeAllViews();
        //nuemFilas = 5;
        cargarFilas(0, 10);

        previous=findViewById(R.id.previa);
        next=findViewById(R.id.seguinte);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dende-10>=0){
                    taboa.removeAllViews();
                    dende-=10;
                    ata-=10;
                    cargarFilas(dende, ata);
                    numVista--;
                    et.setHint(numVista + "/"+numViews);


                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ata+10)<=numViews*10){
                    ata+=10;
                    dende+=10;
                    taboa.removeAllViews();
                    cargarFilas(dende, ata);
                    numVista++;
                    et.setHint(numVista + "/"+numViews);

                }

            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int numero = Integer.parseInt(String.valueOf(et.getText()));
                    if (numero > numViews || numero <= 0) {
                        Toast.makeText(getApplicationContext(), R.string.foraRango, Toast.LENGTH_LONG).show();

                        return;
                    } else {
                        numVista = numero;
                        dende = (numero - 1) * 10;
                        ata = numero * 10;
                        taboa.removeAllViews();
                        cargarFilas(dende, ata);
                        et.setText("");
                        et.setHint(numVista + "/" + numViews);

                    }
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), R.string.introNum, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void cargarFilas(int dende, int ata){
        for (int i = dende; i <= ata; i++) {
            TableRow fila = new TableRow(getApplicationContext());
            taboa.addView(fila);
            String especie = todasEspecies.get(i).getEspecie();
            //int pkXenero =todasEspecies.get(i).getXenero();
            //String xenero = MainActivity.bb_dd.getXeneropk(pkXenero);
            //amosamos nome

            String xenero = todasEspecies.get(i).getXenero();
            TextView tv = new TextView(getApplicationContext());
            tv.setText(xenero + " " + especie);
            tv.setTextColor(getResources().getColor(R.color.darkBlue));
            tv.setPaintFlags(tv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

            Intent inte = getIntent();

            boolean subir_nome_esp=inte.getBooleanExtra("actividade", false);

            if (subir_nome_esp) {

                tv.setBackground(getDrawable(R.drawable.border_text_view));
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Toast.makeText(getApplicationContext(), "LONGO", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = "https://es.wikipedia.org/wiki/"+xenero+"_"+especie;
                    Intent procuraWiki = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(procuraWiki);
                }
            });
            fila.addView(tv);
            Button b = new Button(getApplicationContext());
            b.setTag(xenero + " " + especie);
            b.setText(R.string.escoitar);

            fila.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String texto = (String) b.getTag();
                    String[] arrayNome = texto.split(" ");
                    apiXenoCanto(arrayNome[0], arrayNome[1]);
                   String urlEscoitar =  procesarJsonGetUrl();
                   if (urlEscoitar!=null){
                       FragmentManager fm = getSupportFragmentManager();
                       ReproducirAudio ra = new ReproducirAudio();
                       ra.setXenero(arrayNome[0]);
                       ra.setEspecie(arrayNome[1]);
                       ra.setElementoReproducir(urlEscoitar);
                       ra.setCancelable(false);
                       ra.show(fm, "Proba");

                   }else{


                   }


                }
            });


        }

    }

    public String procesarJsonGetUrl(){
        String urlAudioCompleta=null;
        try {
            FileReader is = new FileReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + File.separator + "obxecto.txt"));
            BufferedReader br = new BufferedReader(is);
            String json;
            json = br.readLine();
            JSONObject jObject = new JSONObject(json);
            JSONArray array = jObject.getJSONArray("recordings");
            JSONObject primeiraGrab = array.getJSONObject(0);

            String urlAudio = primeiraGrab.getString("file");

            urlAudioCompleta = "https:"+urlAudio;
            Log.i("probaJson", urlAudio);


        } catch(Exception e){

            e.printStackTrace();
        }
        return urlAudioCompleta;


    }

    public void apiXenoCanto(String familia, String especie) {

        Thread t = new Thread(new Runnable() {

            public void run() {
                Looper.prepare();
                URL url = null;
                try {
                    String urlStr = "https://www.xeno-canto.org/api/2/recordings?query=" + familia + "+" +especie + "&page=1";
                    // String urlStr = "https://www.xeno-canto.org/api/2/recordings?query="+familia+"+"+especie;
                    url = new URL(urlStr);
                } catch (MalformedURLException e1) {
                    Toast.makeText(getApplicationContext(), R.string.audioNonTopado, Toast.LENGTH_LONG).show();
                    return;
                }
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);    /* milliseconds */
                    conn.setConnectTimeout(15000);  /* milliseconds */
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);            /* Indicamos que a conexión vai recibir datos */
                    // conn.setInstanceFollowRedirects(false);
                    conn.connect();
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), R.string.erroDescargando, Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    OutputStream os;
                    InputStream in;
                    os = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + File.separator + "obxecto.txt"));
                    in = conn.getInputStream();
                    byte data[] = new byte[50000];    // Buffer a utilizar
                    int count;
                    while ((count = in.read(data)) != -1) {
                        os.write(data, 0, count);
                    }
                    os.flush();
                    os.close();
                    in.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.getMessage();
                    Toast.makeText(getApplicationContext(), R.string.erroDatos, Toast.LENGTH_LONG).show();

                }

            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            FragmentManager fm = getSupportFragmentManager();
            ReproducirAudio ra = new ReproducirAudio();
            ra.show(fm, "Proba");

        }


    }
}