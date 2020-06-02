package com.recordwatch.recordwatch.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

/**
 * Clase que realiza las sentencias sql a la base de datos
 */
public class AdminSQLite extends SQLiteOpenHelper {

    /**
     * Constructor de uun objeto AdminSQLite
     * @param context indica la actividad desde donde se ha llamado al método
     * @param name nombre de la base de datos que se crea
     * @param factory permite la devolución de subclases de cursor cuando se llama a la consulta.
     * @param version de la base de datos
     */
    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /**
     * Este método permite crear la base una base de datos.En el se ejecutan las sentencias sql
     * que crean las tablas
     * @param bdRecordWatch  objeto de tipo SQLiteDatabase  que llama a los métodos para crear las tablas
     * y ejecutar las sentencias sql
     */
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
                "foreign key (serie_id, numero_temporada)references temporada(serie_id,numero_temporada)," +
                "check (visto in ('S','N')))");
    }

    /**
     *
     * @param db objeto de tipo SQLiteDatabase que llama a los métodos para crear las tablas y ejecutar las sentencias sql
     * @param oldVersion objeto de tipo int que indica el numero de la version vieja de la base de datos
     * @param newVersion objeto de tipo int que indica el numero de la version nueva de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
