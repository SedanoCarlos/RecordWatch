package com.recordwatch.recordwatch;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.recordwatch.recordwatch.componentes.ComponenteBD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AdminSQLite extends SQLiteOpenHelper {
    
    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase bdRecordWatch) {
        bdRecordWatch.execSQL("create table usuario(contrasena String primary key)");
        bdRecordWatch.execSQL("create table pelicula_registrada(pelicula_id int primary key,estado String,check (estado in ('F','P','V')))");
        bdRecordWatch.execSQL("create table serie_registrada(serie_id int primary key,estado String,check (estado in ('S','P','V')))");
        bdRecordWatch.execSQL("create table temporada(serie_id int,numero_temporada int," +
                "primary key (serie_id, numero_temporada)," +
                "foreign key(serie_id) references serie_registrada(serie_id))");
        bdRecordWatch.execSQL("create table episodio(serie_id int,numero_temporada int,numero_episodio int,visto String," +
                "primary key (serie_id,numero_temporada,numero_episodio)," +
                "foreign key(serie_id,numero_temporada) references temporada(serie_id, numero_temporada)," +
                "check (visto in ('S','N')))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static boolean copiaBD(String from, String to) {
        boolean result = false;
        try{
            File dir = new File(to.substring(0, to.lastIndexOf('/')));
            dir.mkdirs();
            File tof = new File(dir, to.substring(to.lastIndexOf('/') + 1));
            int byteread;
            File oldfile = new File(from);
            if(oldfile.exists()){
                InputStream inStream = new FileInputStream(from);
                FileOutputStream fs = new FileOutputStream(tof);
                byte[] buffer = new byte[1024];
                while((byteread = inStream.read(buffer)) != -1){
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
            result = true;
        }catch (Exception e){
            Log.e("copyFile", "Error copiando archivo: " + e.getMessage());
        }
        return result;
    }

    public static boolean restaurarBB(String from,String yo){
        return true;
    }
}
