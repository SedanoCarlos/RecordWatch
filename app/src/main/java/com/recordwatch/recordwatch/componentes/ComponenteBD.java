/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recordwatch.recordwatch.componentes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.recordwatch.recordwatch.AdminSQLite;
import com.recordwatch.recordwatch.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.IniciarSesion;
import com.recordwatch.recordwatch.MainActivity;
import com.recordwatch.recordwatch.Pelicula;
import com.recordwatch.recordwatch.R;
import com.recordwatch.recordwatch.Serie;
import com.recordwatch.recordwatch.Temporada;
import com.recordwatch.recordwatch.Usuario;


import java.util.ArrayList;

import static com.recordwatch.recordwatch.IniciarSesion.saveValuePreference;
import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;

/**
 * @author Carlos
 */
public class ComponenteBD {
    AdminSQLite admin;
    SQLiteDatabase rw;

    public ComponenteBD(Context context) throws ExcepcionRecordWatch {
        admin = new AdminSQLite(context, "RecordWatch", null, 1);
    }

    private void conectarBD() throws ExcepcionRecordWatch {
        rw = admin.getWritableDatabase();
    }

    private void desconectarBD() throws ExcepcionRecordWatch {
        rw.close();
    }

    public long insertarUsuario(Usuario u) throws ExcepcionRecordWatch{
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("contrasena", u.getContrasena());
        long registrosAfectados = rw.insert("usuario", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    public Integer eliminarUsuario(Integer contrasena) throws ExcepcionRecordWatch {
        return 0;
    }

    public long modificarUsuario(Usuario usuarioViejo, Usuario usuarioNuevo) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("contrasena", usuarioNuevo.getContrasena());
        long registrosModificados = rw.update("usuario", registro, "contrasena=" + "'" + usuarioViejo.getContrasena() + "'" , null);
        desconectarBD();
        return registrosModificados;
    }

    public Usuario leerUsuario(String contraseña) throws ExcepcionRecordWatch {
        conectarBD();
        Cursor buscar = rw.rawQuery("select contrasena from usuario where contrasena=" + "'" + contraseña + "'", null);
        buscar.moveToFirst();
        if (buscar.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setContrasena(buscar.getString(0));
            desconectarBD();
            return usuario;
        }
        return null;
    }

    public long insertarPelicula(Pelicula p) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("pelicula_id", p.getPeliculaId());
        registro.put("estado", p.getEstado());
        long registrosAfectados = rw.insert("pelicula_registrada", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    public Integer eliminarPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("pelicula_registrada", "pelicula_id=" + peliculaId , null);
        return registrosEliminados;
    }

    public long modificarPelicula(Integer peliculaId, Pelicula p) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("pelicula_id", p.getPeliculaId());
        registro.put("estado", p.getEstado());
        long registrosModificados = rw.update("pelicula_registrada", registro, "pelicula_id=" + peliculaId , null);
        desconectarBD();
        return registrosModificados;
    }

    public Pelicula leerPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        conectarBD();
        Cursor buscar = rw.rawQuery("select pelicula_id,estado from pelicula_registrada where pelicula_id=" + peliculaId + "", null);
        buscar.moveToFirst();
        if (buscar.moveToFirst()) {
            Pelicula pelicula = new Pelicula();
            pelicula.setPeliculaId(buscar.getInt(0));
            pelicula.setEstado(buscar.getString(1));
            desconectarBD();
            return pelicula;
        }
        return null;
    }

    public ArrayList<Pelicula> leerPeliculas(String estado) throws ExcepcionRecordWatch {
        ArrayList<Pelicula> miLista = new ArrayList<>();
        conectarBD();
        Cursor cursor = rw.rawQuery("select pelicula_id,estado from pelicula_registrada where estado = '"+estado+"'", null);
        if(cursor.moveToFirst()) {
            do {
                Pelicula pelicula = new Pelicula();
                pelicula.setPeliculaId(cursor.getInt(0));
                pelicula.setEstado(cursor.getString(1));
                miLista.add(pelicula);
            }while(cursor.moveToNext());
        }
        return miLista;
    }

    public long insertarSerie(Serie s) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("serie_id", s.getSerieId());
        registro.put("estado", s.getEstado());
        long registrosAfectados = rw.insert("serie_registrada", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    public Integer eliminarSerie(Integer serieId) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("serie_registrada", "serie_id=" + serieId , null);
        return registrosEliminados;
    }

    public long modificarSerie(Integer serieId, Serie s) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("serie_id", s.getSerieId());
        registro.put("estado", s.getEstado());
        long registrosModificados = rw.update("serie_registrada", registro, "serie_id=" + serieId , null);        desconectarBD();
        return registrosModificados;
    }

    public Serie leerSerie(Integer serieId) throws ExcepcionRecordWatch {
        conectarBD();
        Cursor buscar = rw.rawQuery("select serie_id,estado from serie_registrada where serie_id=" + serieId + "", null);
        buscar.moveToFirst();
        if (buscar.moveToFirst()) {
            Serie serie = new Serie();
            serie.setSerieId(buscar.getInt(0));
            serie.setEstado(buscar.getString(1));
            desconectarBD();
            return serie;
        }
        return null;
    }

    public ArrayList<Serie> leerSeries(String estado) throws ExcepcionRecordWatch {
        ArrayList<Serie> miLista = new ArrayList<>();
        conectarBD();
        Cursor cursor = rw.rawQuery("select serie_id,estado from serie_registrada where estado = '"+estado+"'", null);
        if(cursor.moveToFirst()) {
            do {
                Serie serie = new Serie();
                serie.setSerieId(cursor.getInt(0));
                serie.setEstado(cursor.getString(1));
                miLista.add(serie);
            }while(cursor.moveToNext());
        }
        return miLista;
    }

    public Integer insertarTemporada(Temporada t) throws ExcepcionRecordWatch {
        return null;
    }

    public Integer eliminarTemporada(Integer serieId, Integer numeroTemporada) throws ExcepcionRecordWatch {
        return null;
    }

    public int modificarTemporada(Integer serieId, int numeroTemporada, Temporada t) throws ExcepcionRecordWatch {
        return 0;
    }

    public Temporada leerTemporada(Integer serieId, int numeroTemporada) throws ExcepcionRecordWatch {
        return null;
    }

    public ArrayList<Temporada> leerTemporadas(Integer serieId) throws ExcepcionRecordWatch {
        return null;
    }

    public long insertarEpisodio(Episodio e) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("serie_id", e.getSerieId());
        registro.put("numero_temporada", e.getNumeroTemporada());
        registro.put("numero_episodio", e.getNumeroEpisodio());
        long registrosAfectados = rw.insert("episodio", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    public Integer eliminarEpisodio(Integer serieId, int numeroTemporada, int numeroEpisodio) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("episodio", "(serie_id=" + serieId +") and (numero_temporada=" + numeroTemporada +") and (numero_episodio=" + numeroEpisodio +")" , null);
        return registrosEliminados;
    }

    public int modificarEpisodio(Integer serieId, int numeroTemporada, int numeroEpisodio, Episodio e) throws ExcepcionRecordWatch {
        return 0;
    }

    public Episodio leerEpisodio(Integer serieId, int numeroTemporada, int numeroEpisodio) throws ExcepcionRecordWatch {
        conectarBD();
        Cursor buscar = rw.rawQuery("select serie_id,numero_temporada,numero_episodio from episodio where (serie_id=" + serieId +") and (numero_temporada=" + numeroTemporada +") and (numero_episodio=" + numeroEpisodio +")", null);
        buscar.moveToFirst();
        if (buscar.moveToFirst()) {
            Episodio episodio = new Episodio();
            episodio.setSerieId(buscar.getInt(0));
            episodio.setNumeroTemporada(buscar.getInt(1));
            episodio.setNumeroEpisodio(buscar.getInt(2));
            desconectarBD();
            return episodio;
        }
        return null;
    }

    public ArrayList<Episodio> leerEpisodios(Integer serieId, int numeroTemporada) throws ExcepcionRecordWatch {
        return null;
    }


}
