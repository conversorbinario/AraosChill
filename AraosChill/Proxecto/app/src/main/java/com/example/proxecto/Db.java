package com.example.proxecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Db extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public Context ctxt;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    //En foto e audio, van as rutas onde están os ficheiros
    private final String AVISTAMENTO_INDIVIDUOS = "CREATE TABLE AVISTAMENTO_INDIVIDUOS(" +
            "AVISTAMENTO INTEGER NOT NULL," +
            "INDIVIDUO INTEGER NOT NULL," +
            "FOTO	VARCHAR(120)," +
            "AUDIO	VARCHAR(120)," +
            "PLUMAXE	VARCHAR(30)," +
            "PESO_GRAMOS INTEGER," +
            "FOREIGN KEY(AVISTAMENTO) REFERENCES AVISTAMENTOS(ID_AVISTAMENTO) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY(INDIVIDUO) REFERENCES INDIVIDUOS(ID_INDIVIDUO) ON DELETE CASCADE ON UPDATE CASCADE," +
            "PRIMARY KEY (AVISTAMENTO, INDIVIDUO))";

    private final String AVISTAMENTOS = "CREATE TABLE AVISTAMENTOS (" +
            "ID_AVISTAMENTO INTEGER PRIMARY KEY," +
            "CONCELLO	TEXT NOT NULL," +
            "NOME_SITIO	TEXT NOT NULL," +
            "HORA	TEXT NOT NULL," +
            "DATA TEXT NOT NULL)";

    private final String INDIVIDUOS = "CREATE TABLE INDIVIDUOS (" +
            "ID_INDIVIDUO	INTEGER PRIMARY KEY," +
            "ESPECIE INT," +
            "SEXO VACRHAR(20)," +
            "FOREIGN KEY(ESPECIE) REFERENCES TIPO_AVE(ID_AVE) ON DELETE SET NULL ON UPDATE CASCADE)";

    //avistada hai que cambialo, xa o sabemos se hai individuos rexistrados ou non
    private final String TIPO_AVE = "CREATE TABLE TIPO_AVE ( " +
            "ID_AVE INTEGER PRIMARY KEY NOT NULL UNIQUE," +

            "ESPECIE VARCHAR(120) NOT NULL," +
            "XENERO INT NOT NULL, " +
            //Xenero taxonomico,
            "FOREIGN KEY(XENERO) REFERENCES XENERO_TAXON(ID_XENERO) ON DELETE SET NULL ON UPDATE CASCADE)";

    private final String XENERO_TAXON = "CREATE TABLE XENERO_TAXON (" +
            "ID_XENERO INTEGER PRIMARY KEY, " +
            "XENERO VARCHAR(120) UNIQUE NOT NULL)";

    public Db(Context ct) {
      super(ct, "BaseDatosPaxaros.db", null, 1);
        this.ctxt = ct;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /* try {
            db.execSQL(AVISTAMENTO_INDIVIDUOS);
            db.execSQL(AVISTAMENTOS);
            db.execSQL(INDIVIDUOS);
            db.execSQL(TIPO_AVE);
            db.execSQL(XENERO_TAXON);

        } catch (Exception e) {
            Log.w("Erros creacion DB", e.getMessage());
        } */

        db = getReadableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public SQLiteDatabase getWritableDatabase() {

        return super.getWritableDatabase();
    }

    public String addTipo_aveFB(Tipo_AveFB esp) throws Exception {

        DatabaseReference myRef = database.getReference("Tipo_Ave");

        DatabaseReference myRef2= myRef.push();
        String pk = myRef.getKey();
        myRef2.setValue(esp);
        return pk;

    }

   /* FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("usuarios");
                    myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot sp : snapshot.getChildren() ){
                if (sp.getKey().equals(nomeUsuario) && sp.getValue().equals(passw)){
                    Intent activityPrincipal = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(activityPrincipal);
                    return;
                }

            }
            Toast.makeText(getApplicationContext(), R.string.usuarioContra, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }); */









    public long addTipo_ave(Tipo_ave esp, long xenero) {
        ContentValues valores = new ContentValues();
        //pares nome_campo - valor_campo

        valores.put("ESPECIE", esp.getEspecie());
        valores.put("XENERO", xenero);


        //para que lance excepcións de ser preciso (p ex, against PK)
        long id = db.insertOrThrow("TIPO_AVE", null, valores);
        valores.clear();
        return id;

    }

    public String add_xen_taxonFB(Xenero_taxonFB xen){

        DatabaseReference myRef = database.getReference("Xenero_Taxon");

        DatabaseReference myRef2= myRef.push();

        myRef2.setValue(xen);
        String pk = myRef2.getKey();

        return pk;


    }

    public long add_xen_taxon(Xenero_taxon xenTax) {
        ContentValues valores = new ContentValues();
        valores.put("XENERO", xenTax.getXenero());
        long id = db.insertOrThrow("XENERO_TAXON", null, valores);
        valores.clear();
        return id;
    }

    public void existeXeneroFB(String xenero) throws Exception {

        DatabaseReference myRef = database.getReference("usuarios");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sp : snapshot.getChildren() ){
                    if (sp.getKey().equals("manuel") && sp.getValue().equals("passw")){
                        return;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean existeXenero(String xenero) throws Exception {
        xenero = xenero.toLowerCase();
        xenero = xenero.substring(0, 1).toUpperCase() + xenero.substring(1);
        int id_xenero = -1;
        Cursor cursor = db.rawQuery("select ID_XENERO from XENERO_TAXON WHERE XENERO=?", new String[]{xenero});

        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            try {
                cursor.close();
            } catch (Exception e) {

            }
            return true;
        }
        cursor.close();
        return false;
    }


    public boolean existeEspecie(String especie, int xen) throws Exception {

        int id_xenero = -1;
        Cursor cursor = db.rawQuery("select ID_AVE from TIPO_AVE WHERE ESPECIE=? and XENERO=?", new String[]{especie, String.valueOf(xen)});

        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            try {
                cursor.close();
            } catch (Exception e) {

            }
            return true;
        }
        cursor.close();
        return false;

    }

    public int getIdEspecie(String especie) {
        especie = especie.toLowerCase();
        int id_ave_especie = -1;
        Cursor cursor = db.rawQuery("select ID_AVE from TIPO_AVE WHERE ESPECIE=?", new String[]{especie});

        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            id_ave_especie = cursor.getInt(0);

        }
        cursor.close();
        return id_ave_especie;
    }

    public int getIdTaxon(String xenero) {
        xenero = xenero.toLowerCase();
        xenero = xenero.substring(0, 1).toUpperCase() + xenero.substring(1);
        int id_xenero = -1;
        Cursor cursor = db.rawQuery("select ID_XENERO from XENERO_TAXON WHERE XENERO=?", new String[]{xenero});

        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            id_xenero = cursor.getInt(0);

        }
        cursor.close();
        return id_xenero;
    }

    //a CONSULTA ESTÁ ben; estou metendo mal o xenero-especie

    ////select X.XENERO,TA.ESPECIE from TIPO_AVE as TA join  XENERO_TAXON as X on TA.XENERO= x.ID_XENERO  WHERE TA.ID_AVE IN (SELECT ESPECIE FROM INDIVIDUOS WHERE ID_INDIVIDUO IN (SELECT INDIVIDUO FROM AVISTAMENTO_INDIVIDUOS))
    ////select X.XENERO,TA.ESPECIE from TIPO_AVE as TA join  XENERO_TAXON as X on TA.XENERO= x.ID_XENERO where TA.ID_AVE in (SELCT ESPECIE FROM INDIVIDUO WHERE ID_INDIVIDUO in (SELECT))



    public ArrayList<Avistamento> getTodosAvistamentos() {

        ArrayList<Avistamento> todosAvi = new ArrayList<Avistamento>();

        Cursor cursor = db.rawQuery("select * from AVISTAMENTOS", null);


        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            while (!cursor.isAfterLast()) {
                int pkAvis = cursor.getInt(0);
                String conce = cursor.getString(1);
                String nome_sitio = cursor.getString(2);
                String hora = cursor.getString(3);
                String data = cursor.getString(4);
                todosAvi.add(new Avistamento(pkAvis, conce, nome_sitio, data, hora));
                cursor.moveToNext();
            }

        }

        cursor.close();
        return todosAvi;
    }



    ////select XT.XENERO, TA.ESPECIE from TIPO_AVE AS TA INNER JOIN XENERO_TAXON AS XT ON TA.XENERO=XT.ID_XENERO WHERE TA.ESPECIE="alba"  AND XT.XENERO="Motacilla"

    public int getIdEspecie(String xenero, String esp) {
        int id_xen = 0;
        xenero = xenero.substring(0, 1).toUpperCase() + xenero.substring(1);
        esp = esp.toLowerCase();
        //select TA.ESPECIE, XA.XENERO from tipo_ave AS TA INNER JOIN INDIVIDUOS as I on I.ESPECIE=TA.ID_AVE INNER JOIN XENERO_TAXON AS XA ON TA.XENERO=XA.ID_XENERO
        Cursor cursor = db.rawQuery("select TA.ID_AVE from TIPO_AVE AS TA INNER JOIN XENERO_TAXON AS XT ON TA.XENERO=XT.ID_XENERO WHERE XT.XENERO=? and TA.ESPECIE=?", new String[]{xenero, esp});

        if (cursor.moveToFirst()) {
            id_xen = cursor.getInt(0);
        }
        cursor.close();
        return id_xen;
    }

    public ArrayList<IndoIndiv> getInfoIndiv(){
        ArrayList<IndoIndiv> infoINdivs = new ArrayList<IndoIndiv>();
        String query = "select I.ID_INDIVIDUO, A.CONCELLO, A.NOME_SITIO, AI.FOTO, A.DATA from AVISTAMENTO_INDIVIDUOS AS AI INNER JOIN AVISTAMENTOS AS A ON AI.AVISTAMENTO=A.ID_AVISTAMENTO INNER JOIN INDIVIDUOS AS I ON I.ID_INDIVIDUO=AI.INDIVIDUO";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            while (!cursor.isAfterLast()) {
               int pkIndi = cursor.getInt(0);
               String conc = cursor.getString(1);
               String nomeSit = cursor.getString(2);
               String foto = cursor.getString(3);
               String data = cursor.getString(4);
               infoINdivs.add(new IndoIndiv(pkIndi, conc, nomeSit, foto, data));
               cursor.moveToNext();
            }
        }

        cursor.close();
        return infoINdivs;
    }

    //dada unha especie, saca o seu xenero



        public String getXeneroEspecie(long idIndiv) {

        String xenero_esp = "";
        //select TA.ESPECIE, XA.XENERO from tipo_ave AS TA INNER JOIN INDIVIDUOS as I on I.ESPECIE=TA.ID_AVE INNER JOIN XENERO_TAXON AS XA ON TA.XENERO=XA.ID_XENERO
        Cursor cursor = db.rawQuery("select  XA.XENERO, TA.ESPECIE " +
                "from tipo_ave AS TA INNER JOIN INDIVIDUOS as I on I.ESPECIE=TA.ID_AVE " +
                "INNER JOIN XENERO_TAXON AS XA ON TA.XENERO=XA.ID_XENERO " +
                " where I.ID_INDIVIDUO=?", new String[]{String.valueOf(idIndiv)});
        if (cursor.moveToFirst()) {
            xenero_esp = cursor.getString(0) + " " + cursor.getString(1);
        }
        cursor.close();
        return xenero_esp;

    }

    public ArrayList<Avis_Esp> getAvis_indivCon(String concello) {
        //select  AV.CONCELLO, AV.NOME_SITIO, AV.DATA, I.ID_INDIVIDUO, AI.FOTO, AI.AUDIO from AVISTAMENTO_INDIVIDUOS as AI inner join INDIVIDUOS as I on AI.INDIVIDUO=I.ID_INDIVIDUO inner join AVISTAMENTOS as AV on AV.ID_AVISTAMENTO=AI.AVISTAMENTO where AV.NOME_SITIO="cangas"
        //select  AV.CONCELLO, AV.NOME_SITIO, AV.DATA, I.ID_INDIVIDUO, AI.FOTO, AI.AUDIO from AVISTAMENTO_INDIVIDUOS as AI inner join INDIVIDUOS as I on AI.INDIVIDUO=I.ID_INDIVIDUO inner join AVISTAMENTOS as AV on AV.ID_AVISTAMENTO=AI.AVISTAMENTO where AV.NOME_SITIO="cangas"
        concello = concello.toLowerCase();
        ArrayList<Avis_Esp> avis_esp = new ArrayList<Avis_Esp>();
        int id_xenero = -1;
        Cursor cursor = db.rawQuery("select  AV.CONCELLO, AV.NOME_SITIO, AV.DATA, I.ID_INDIVIDUO, AI.FOTO, AI.AUDIO from AVISTAMENTO_INDIVIDUOS as AI inner join INDIVIDUOS as I on AI.INDIVIDUO=I.ID_INDIVIDUO inner join AVISTAMENTOS as AV on AV.ID_AVISTAMENTO=AI.AVISTAMENTO where AV.CONCELLO=?", new String[]{concello});


        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
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
        cursor.close();
        return avis_esp;
    }



    public ArrayList<Avis_Esp> getAvis_indivCTod() {

        ArrayList<Avis_Esp> avis_esp = new ArrayList<Avis_Esp>();
        int id_xenero = -1;
        Cursor cursor = db.rawQuery("select  AV.CONCELLO, AV.NOME_SITIO, AV.DATA, I.ID_INDIVIDUO, AI.FOTO, AI.AUDIO from AVISTAMENTO_INDIVIDUOS as AI inner join INDIVIDUOS as I on AI.INDIVIDUO=I.ID_INDIVIDUO inner join AVISTAMENTOS as AV on AV.ID_AVISTAMENTO=AI.AVISTAMENTO", null);


        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
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
        cursor.close();
        return avis_esp;
    }

    public int setEspecieIndiv(int especie, int idIndi){
        ContentValues cv = new ContentValues();
        cv.put("ESPECIE",especie);
        int val=db.update("INDIVIDUOS", cv, "ID_INDIVIDUO=?", new String[]{String.valueOf(idIndi)});
        return val;
    }

    public ArrayList<Avis_Esp> getAvis_indivCTodID() {

        ArrayList<Avis_Esp> avis_esp = new ArrayList<Avis_Esp>();
        int id_xenero = -1;
        Cursor cursor = db.rawQuery("select  AV.CONCELLO, AV.NOME_SITIO, AV.DATA, I.ID_INDIVIDUO, AI.FOTO, AI.AUDIO from AVISTAMENTO_INDIVIDUOS as AI inner join INDIVIDUOS as I on AI.INDIVIDUO=I.ID_INDIVIDUO inner join AVISTAMENTOS as AV on AV.ID_AVISTAMENTO=AI.AVISTAMENTO", null);


        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            while (!cursor.isAfterLast()) {
                String conce = cursor.getString(0);
                String nomeSitio = cursor.getString(1);
                String data = cursor.getString(2);
                int idINdiv = cursor.getInt(3);
                String xen_esp = getXeneroEspecie(idINdiv);
                String dir_foto = cursor.getString(4);
                String dir_audio = cursor.getString(5);
                avis_esp.add(new Avis_Esp(conce, nomeSitio, data, xen_esp, dir_foto, dir_audio, idINdiv));
                cursor.moveToNext();
            }

        }
        cursor.close();
        return avis_esp;
    }


    public long addTipo_aveFB(Tipo_ave esp, long xenero) throws Exception {
        ContentValues valores = new ContentValues();
        //pares nome_campo - valor_campo

        valores.put("ESPECIE", esp.getEspecie());
        valores.put("XENERO", xenero);


        //para que lance excepcións de ser preciso (p ex, against PK)
        long id = db.insertOrThrow("TIPO_AVE", null, valores);
        valores.clear();
        return id;

    }




    public long addAvistamento(Avistamento av) throws Exception {
        ContentValues valores = new ContentValues();
        valores.put("DATA", av.getData());
        valores.put("NOME_SITIO", av.getNome_sitio().toLowerCase());
        valores.put("CONCELLO", av.getConcello().toLowerCase());
        valores.put("HORA", av.getHora());
        long id = db.insertOrThrow("AVISTAMENTOS", null, valores);
        valores.clear();
        return id;
    }

    public String addAvistamentoFB(AvistamentoFB av) throws Exception {

        DatabaseReference myRef = database.getReference("Avistamento");

        DatabaseReference myRef2= myRef.push();
        String pk = myRef2.getKey();
        myRef2.setValue(av);
        return pk;
    }







    /*public String addTipo_aveFB(Tipo_AveFB esp) throws Exception {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Tipo_Ave");

        DatabaseReference myRef2= myRef.push();
        String pk = myRef.getKey();
        myRef2.setValue(esp);
        return pk;

    } */



    public String addAvisIndividuoFB(String pkIndividuo, String pkAvistamento) {

        DatabaseReference myRef = database.getReference("Individuos_Avistamentos/"+pkIndividuo+"/Lugar");
        myRef.setValue(pkAvistamento);

        return "";
    }



    public long addAvisIndividuio(long pk_avis, long pk_indiv, String rutaAudio, String rutfoto, int peso, String plumaxe) {
        ContentValues valores = new ContentValues();
        valores.put("AVISTAMENTO", pk_avis);
        valores.put("INDIVIDUO", pk_indiv);
        valores.put("FOTO", rutfoto);
        valores.put("AUDIO", rutaAudio);
        valores.put("PESO_GRAMOS", peso);
        valores.put("plumaxe", plumaxe);
        long id = db.insertOrThrow("AVISTAMENTO_INDIVIDUOS", null, valores);
        valores.clear();
        return id;

    }


    public long addIndividuo(Individuo pax, int idEspecie) throws Exception {
        ContentValues valores = new ContentValues();
        //pares nome_campo - valor_campo

        valores.put("ESPECIE", idEspecie);
        valores.put("SEXO", pax.getSexo());

        //para que lance excepcións de ser preciso (p ex, against PK)
        long id = db.insertOrThrow("INDIVIDUOS", null, valores);
        valores.clear();
        return id;

    }



    //se a especie e -1, é que NON se coñece
    public String addIndividuoFB(IndividuoFB pax, String pkAvis) throws Exception {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Individuo");

        DatabaseReference myRef2= myRef.push();
        String pk = myRef2.getKey();
        pax.setRutaFoto(pkAvis+"_"+pk);
        myRef2.setValue(pax);
        return pk;
    }


    public long addIndividuo(Individuo pax) throws Exception {
        ContentValues valores = new ContentValues();
        //pares nome_campo - valor_campo
        valores.put("SEXO", pax.getSexo());
        valores.putNull("ESPECIE");

        //para que lance excepcións de ser preciso (p ex, against PK)
        long id = db.insertOrThrow("INDIVIDUOS", null, valores);
        valores.clear();
        return id;

    }

    public ArrayList<String> getXeneroAvistados() {
        ArrayList<String> especies = new ArrayList<String>();

        String xenero = "";
        Cursor cursor = db.rawQuery("select XENERO from XENERO_TAXON where ID_XENERO in (select XENERO from TIPO_AVE)ORDER BY XENERO DESC", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                xenero = cursor.getString(0);
                especies.add(xenero);
            }
        }
        cursor.close();
        return especies;

    }

    public Xenero_Especie getXeneroEspecieFB(long claveIndiv){

        Cursor cursor = db.rawQuery("select xt.xenero, ta.ESPECIE from xenero_taxon as xt left join tipo_ave as ta on xt.ID_XENERO=ta.XENERO where ta.ID_AVE =?",  new String[]{String.valueOf(claveIndiv)});


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
               String xenero = cursor.getString(0);
               String especie = cursor.getString(1);
               Xenero_Especie esp = new Xenero_Especie(xenero, especie);
                return esp;
            }
        }
        cursor.close();
        return  null;

    }


    //select xt.xenero, ta.ESPECIE from xenero_taxon as xt left join tipo_ave as ta on xt.ID_XENERO=ta.XENERO where ta.ID_AVE =1;
    public String getXeneropk(int pk) {
        String xenero = "";
        Cursor cursor = db.rawQuery("select XENERO from XENERO_TAXON where ID_XENERO=? ORDER BY XENERO DESC", new String[]{String.valueOf(pk)});
        if (cursor.moveToFirst()) {
            xenero = cursor.getString(0);
        }
        cursor.close();
        return xenero;
    }



    public ArrayList<Tipo_ave> getTodasEspecies() {
        ArrayList<Tipo_ave> especies = new ArrayList<Tipo_ave>();
        Cursor cursor = db.rawQuery("select * from TIPO_AVE", null);

        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            while (!cursor.isAfterLast()) {     // Quédase no bucle ata que remata de percorrer o cursor.
                int id_ave = cursor.getInt(0);
                String especie = cursor.getString(1);
                int xenero = cursor.getInt(2);
                Tipo_ave esp = new Tipo_ave(id_ave, xenero, especie);
                especies.add(esp);
                cursor.moveToNext();
            }
        }
        cursor.close();


        return especies;
    }

    public ArrayList<Xenero_Especie> getTodoXeneroEspecie(){
        ArrayList<Xenero_Especie> especies = new ArrayList<Xenero_Especie>();
        Cursor cursor = db.rawQuery("select  xa.xenero, tipo_ave.ESPECIE from xenero_taxon as xa  inner join tipo_ave on xa.ID_XENERO=tipo_ave.XENERO order by xa.xenero, tipo_ave.ESPECIE", null);

        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            while (!cursor.isAfterLast()) {     // Quédase no bucle ata que remata de percorrer o cursor.
                String xenero = cursor.getString(0);
                String especie = cursor.getString(1);
                Xenero_Especie esp = new Xenero_Especie(xenero, especie);
                especies.add(esp);
                cursor.moveToNext();
            }
        }
        cursor.close();


        return especies;

    }

    public ArrayList<Tipo_ave> getTodasEspeciesAlf() {
        ArrayList<Tipo_ave> especies = new ArrayList<Tipo_ave>();
        Cursor cursor = db.rawQuery("select * from TIPO_AVE", null);

        if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
            while (!cursor.isAfterLast()) {     // Quédase no bucle ata que remata de percorrer o cursor.
                int id_ave = cursor.getInt(0);
                String especie = cursor.getString(1);
                int xenero = cursor.getInt(2);
                Tipo_ave esp = new Tipo_ave(id_ave, xenero, especie);
                especies.add(esp);
                cursor.moveToNext();
            }
        }
        cursor.close();


        return especies;
    }


}
