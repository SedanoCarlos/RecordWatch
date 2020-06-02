/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recordwatch.recordwatch.componentes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recordwatch.recordwatch.utilidades.AdminSQLite;
import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.pojos.Pelicula;
import com.recordwatch.recordwatch.pojos.Serie;
import com.recordwatch.recordwatch.pojos.Temporada;
import com.recordwatch.recordwatch.pojos.Usuario;


import java.util.ArrayList;

/**
 * En esta clase se gestionan todas las operaciones de inserción, borrado,
 * eliminación, leer un registro y leer todos los registros de todas las tablas
 * de la base de datos RecordWatch.
 *
 * @author Carlos
 */
public class ComponenteBD {
    AdminSQLite admin;
    SQLiteDatabase rw;

    /**
     * Método constructor.
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * @param context Activity desde la cual se llama a este método
     */
    public ComponenteBD(Context context) throws ExcepcionRecordWatch {
        admin = new AdminSQLite(context, "RecordWatch", null, 1);
    }

    /**
     * Método que nos permitirá establecer la conexión una base de datos.
     *
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema con el driver
     */
    private void conectarBD() throws ExcepcionRecordWatch {
        rw = admin.getWritableDatabase();
    }

    /**
     * Método que realizará la desconexión de la base de datos.
     *
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema con el driver
     */
    private void desconectarBD() throws ExcepcionRecordWatch {
        rw.close();
    }

    /**
     * Este método permite insertar un registro en la tabla Usuario.
     *
     * @param u Objeto de la clase Usuario que contiene los datos del usuario
     * a insertar.
     * @return Cantidad de registros insertados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long insertarUsuario(Usuario u) throws ExcepcionRecordWatch{
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("contrasena", u.getContrasena());
        long registrosAfectados = rw.insert("usuario", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla Usuario.
     *
     * @param usuarioViejo Objeto de la clase usuario que contiene los datos del usuario
     * a modificar
     * @param usuarioNuevo Objeto de la clase empleado que contiene los datos del usuario a
     * modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long modificarUsuario(Usuario usuarioViejo, Usuario usuarioNuevo) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("contrasena", usuarioNuevo.getContrasena());
        long registrosModificados = rw.update("usuario", registro, "contrasena=" + "'" + usuarioViejo.getContrasena() + "'" , null);
        desconectarBD();
        return registrosModificados;
    }

    /**
     * Este método permite eliminar un registro en la tabla Usuario.
     *
     * @param contraseña String que indica la contraseña del usuario a borrar
     * @return Cantidad de registros borrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public Integer eliminarUsuario(String contraseña) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("usuario", "contrasena=" + "'" + contraseña + "'" , null);
        desconectarBD();
        return registrosEliminados;
    }

    /**
     * Este método permite leer un registro de la tabla Usuario.
     *
     * @param contraseña String que indica el la contraseña del usuario del cual se
     * van a leer los datos
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
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
        desconectarBD();
        return null;

    }

    /**
     * Este método permite leer todos los registros de la tabla Usuario.
     *
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public Usuario leerUsuarios() throws ExcepcionRecordWatch {
        conectarBD();
        Cursor buscar = rw.rawQuery("select contrasena from usuario", null);
        buscar.moveToFirst();
        if (buscar.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setContrasena(buscar.getString(0));
            desconectarBD();
            return usuario;
        }
        desconectarBD();
        return null;

    }

    /**
     * Este método permite insertar un registro en la tabla Pelicula.
     *
     * @param p Objeto de la clase Pelicula que contiene los datos de la pelicula
     * a insertar.
     * @return Cantidad de registros insertados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long insertarPelicula(Pelicula p) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("pelicula_id", p.getPeliculaId());
        registro.put("estado", p.getEstado());
        long registrosAfectados = rw.insert("pelicula_registrada", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla Pelicula.
     *
     * @param peliculaId id que indica el id de la pelicula
     * a modificar
     * @param p Objeto de la clase pelicula que contiene los datos de la pelicula a
     * modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long modificarPelicula(Integer peliculaId, Pelicula p) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("pelicula_id", p.getPeliculaId());
        registro.put("estado", p.getEstado());
        long registrosModificados = rw.update("pelicula_registrada", registro, "pelicula_id=" + peliculaId , null);
        desconectarBD();
        return registrosModificados;
    }

    /**
     * Este método permite eliminar un registro en la tabla Pelicula.
     *
     * @param peliculaId id que indica el id de la pelicula a borrar
     * @return Cantidad de registros borrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public Integer eliminarPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("pelicula_registrada", "pelicula_id=" + peliculaId , null);
        desconectarBD();
        return registrosEliminados;
    }

    /**
     * Este método permite leer un registro de la tabla Pelicula.
     *
     * @param peliculaId id de la pelicula del cual se
     * van a leer los datos
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
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
        desconectarBD();
        return null;
    }

    /**
     * Este método permite leer todos los registros de la tabla Pelicula.
     *
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
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
        desconectarBD();
        return miLista;
    }

    /**
     * Este método permite insertar un registro en la tabla Serie.
     *
     * @param s Objeto de la clase Serie que contiene los datos de la serie
     * a insertar.
     * @return Cantidad de registros insertados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long insertarSerie(Serie s) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("serie_id", s.getSerieId());
        registro.put("estado", s.getEstado());
        long registrosAfectados = rw.insert("serie_registrada", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla Serie.
     *
     * @param serieId id que indica el id de la serie
     * a modificar
     * @param s Objeto de la clase serie que contiene los datos de la serie a
     * modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long modificarSerie(Integer serieId, Serie s) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("serie_id", s.getSerieId());
        registro.put("estado", s.getEstado());
        long registrosModificados = rw.update("serie_registrada", registro, "serie_id=" + serieId , null);        desconectarBD();
        desconectarBD();
        return registrosModificados;
    }

    /**
     * Este método permite eliminar un registro en la tabla Serie.
     *
     * @param serieId id que indica el id de la serie a borrar
     * @return Cantidad de registros borrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public Integer eliminarSerie(Integer serieId) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("serie_registrada", "serie_id=" + serieId , null);
        desconectarBD();
        return registrosEliminados;
    }

    /**
     * Este método permite leer un registro de la tabla Serie.
     *
     * @param serieId id de la serie del cual se
     * van a leer los datos
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
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
        desconectarBD();
        return null;
    }

    /**
     * Este método permite leer varios de los registros de la tabla Serie.
     *
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
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
        desconectarBD();
        return miLista;
    }

    /**
     * Este método permite insertar un registro en la tabla Temporada.
     *
     * @param t Objeto de la clase Temporada que contiene los datos de la temporada
     * a insertar.
     * @return Cantidad de registros insertados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long insertarTemporada(Temporada t) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("serie_id", t.getSerieId());
        registro.put("numero_temporada", t.getNumeroTemporada());
        long registrosAfectados = rw.insert("temporada", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

//    public Integer eliminarTemporada(Integer serieId, Integer numeroTemporada) throws ExcepcionRecordWatch {
//        return null;
//    }

    /**
     * Este método permite eliminar varios registros en la tabla Temporada.
     *
     * @param serieId id que indica el id de la serie de las temporadas a borrar
     * @return Cantidad de registros borrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public Integer eliminarTemporadas(Integer serieId) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("temporada", "(serie_id=" + serieId +")" , null);
        desconectarBD();
        return registrosEliminados;
    }

//    public int modificarTemporada(Integer serieId, int numeroTemporada, Temporada t) throws ExcepcionRecordWatch {
//        return 0;
//    }

//    public Temporada leerTemporada(Integer serieId, int numeroTemporada) throws ExcepcionRecordWatch {
//        return null;
//    }

    /**
     * Este método permite leer varios de los registros de la tabla Temporada.
     *
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public ArrayList<Temporada> leerTemporadas(Integer serieId) throws ExcepcionRecordWatch {
        ArrayList<Temporada> miLista = new ArrayList<>();
        conectarBD();
        Cursor cursor = rw.rawQuery("select serie_id,numero_temporada from temporada where serie_id = "+serieId+"", null);
        if(cursor.moveToFirst()) {
            do {
                Temporada temporada = new Temporada();
                temporada.setSerieId(cursor.getInt(0));
                temporada.setNumeroTemporada(cursor.getInt(1));
                miLista.add(temporada);
            }while(cursor.moveToNext());
        }
        desconectarBD();
        return miLista;
    }

    /**
     * Este método permite insertar un registro en la tabla Episodio.
     *
     * @param e Objeto de la clase Temporada que contiene los datos del episodio
     * a insertar.
     * @return Cantidad de registros insertados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public long insertarEpisodio(Episodio e) throws ExcepcionRecordWatch {
        conectarBD();
        ContentValues registro = new ContentValues();
        registro.put("serie_id", e.getSerieId());
        registro.put("numero_temporada", e.getNumeroTemporada());
        registro.put("numero_episodio", e.getNumeroEpisodio());
        registro.put("visto",e.getVisto());
        long registrosAfectados = rw.insert("episodio", null, registro);
        desconectarBD();
        return registrosAfectados;
    }

    /**
     * Este método permite eliminar un registro en la tabla Episodio.
     *
     * @param serieId id que indica la serie del episodio a borrar
     * @param numeroTemporada numero que indica la temporada de los episodios a borrar
     * @param numeroEpisodio numero que indica el numero del episodio a borrar
     * @return Cantidad de registros borrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public Integer eliminarEpisodio(Integer serieId, int numeroTemporada, int numeroEpisodio) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("episodio", "(serie_id=" + serieId +") and (numero_temporada=" + numeroTemporada +") and (numero_episodio=" + numeroEpisodio +")" , null);
        desconectarBD();
        return registrosEliminados;
    }

    /**
     * Este método permite eliminar varios registros en la tabla Episodios.
     *
     * @param serieId id que indica la serie de los episodios a borrar
     * @return Cantidad de registros borrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
    public Integer eliminarEpisodios(Integer serieId) throws ExcepcionRecordWatch {
        conectarBD();
        int registrosEliminados = rw.delete("episodio", "(serie_id=" + serieId +")" , null);
        desconectarBD();
        return registrosEliminados;
    }

//    public int modificarEpisodio(Integer serieId, int numeroTemporada, int numeroEpisodio, Episodio e) throws ExcepcionRecordWatch {
//        return 0;
//    }

    /**
     * Este método permite leer varios registros de la tabla Episodios.
     *
     * @param serieId id que indica la serie del episodio del cual se van a leer los datos
     * @param numeroTemporada numero que indica la temporada de los episodios del cual se van a leer los datos
     * @param numeroEpisodio numero que indica el numero del episodio del cual se van a leer los datos
     * @return Cantidad de registros mostrados
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * algun problema al operar con la base de datos
     */
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
        desconectarBD();
        return null;
    }

//    public ArrayList<Episodio> leerEpisodios(Integer serieId, int numeroTemporada) throws ExcepcionRecordWatch {
//        ArrayList<Episodio> miLista = new ArrayList<>();
//        conectarBD();
//        Cursor cursor = rw.rawQuery("select serie_id,numero_temporada,numero_episodio from episodio where (serie_id=" + serieId +") and (numero_temporada=" + numeroTemporada +")", null);
//        if(cursor.moveToFirst()) {
//            do {
//                Episodio episodio = new Episodio();
//                episodio.setSerieId(cursor.getInt(0));
//                episodio.setNumeroTemporada(cursor.getInt(1));
//                episodio.setNumeroEpisodio(cursor.getInt(2));
//                miLista.add(episodio);
//            }while(cursor.moveToNext());
//        }
//        desconectarBD();
//        return miLista;
//    }


}
