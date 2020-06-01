package com.recordwatch.recordwatch.componentes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.recordwatch.recordwatch.JSONTask;
import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.pojos.Pelicula;
import com.recordwatch.recordwatch.pojos.Serie;
import com.recordwatch.recordwatch.pojos.Temporada;
import com.recordwatch.recordwatch.pojos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ComponenteCAD {
    Context context;

    public ComponenteCAD(Context con) throws ExcepcionRecordWatch {
        context=con;
    }

    public long insertarUsuario(Usuario u) throws ExcepcionRecordWatch{
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.insertarUsuario(u);
        return registrosAfectados;
    }

    public Integer eliminarUsuario(String contraseña) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Integer registrosEliminados = bd.eliminarUsuario(contraseña);
        return registrosEliminados;
    }

    public long modificarUsuario(Usuario usuarioViejo, Usuario usuarioNuevo) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosModificados = bd.modificarUsuario(usuarioViejo,usuarioNuevo);
        return registrosModificados;
    }


    public Usuario leerUsuario(String contraseña) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Usuario usuario = bd.leerUsuario(contraseña);
        return usuario;

    }

    public Usuario leerUsuarios() throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Usuario usuario = bd.leerUsuarios();
        return usuario;
    }


    public long insertarPelicula(Pelicula p) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAñadidos = bd.insertarPelicula(p);
        return registrosAñadidos;
    }

    public long eliminarPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosEliminados = bd.eliminarPelicula(peliculaId);
        return registrosEliminados;
    }

    public long modificarPelicula(Integer peliculaId, Pelicula p) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosModificados = bd.modificarPelicula(peliculaId,p);
        return registrosModificados;
    }

    public Pelicula leerPeliculaBD(Integer peliculaId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Pelicula pelicula = bd.leerPelicula(peliculaId);
        return pelicula;
    }

    public Pelicula leerPeliculaWS(Integer peliculaId) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        Pelicula pelicula = ws.leerPelicula(peliculaId);
        return pelicula;
    }


    public ArrayList<Pelicula> leerPeliculas(String estado) throws ExcepcionRecordWatch {
        ArrayList<Pelicula> aux = new ArrayList<>();
        ArrayList<Pelicula> miLista = new ArrayList<>();
        ComponenteBD bd = new ComponenteBD(context);
        aux = bd.leerPeliculas(estado);
        for(int i=0;i<aux.size();i++){
            Pelicula pelicula = new Pelicula();
            ComponenteWS ws = new ComponenteWS();
            pelicula = ws.leerPelicula(aux.get(i).getPeliculaId());
            miLista.add(pelicula);
        }
        return miLista;
    }

    public ArrayList<Pelicula> buscarPelicula(String tituloPelicula) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Pelicula> miLista;
        miLista = ws.buscarPelicula(tituloPelicula);
        return miLista;
    }

    public ArrayList<Pelicula> leerPeliculasPopulares() throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Pelicula> miLista = ws.leerPeliculasPopulares();
        return miLista;
    }


    public long insertarSerie(Serie s) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.insertarSerie(s);
        return registrosAfectados;
    }

    public long eliminarSerie(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.eliminarSerie(serieId);
        return registrosAfectados;
    }

    public long modificarSerie(Integer serieId, Serie s) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.modificarSerie(serieId,s);
        return registrosAfectados;
    }

    public Serie leerSerieBD(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Serie serie = bd.leerSerie(serieId);
        return serie;
    }

    public Serie leerSerieWS(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        Serie serie = ws.leerSerie(serieId);
        return serie;
    }

    public ArrayList<Serie> leerSeries(String estado) throws ExcepcionRecordWatch {
        ArrayList<Serie> aux = new ArrayList<>();
        ArrayList<Serie> miLista = new ArrayList<>();
        ComponenteBD bd = new ComponenteBD(context);
        aux = bd.leerSeries(estado);
        for(int i=0;i<aux.size();i++){
            Serie serie = new Serie();
            ComponenteWS ws = new ComponenteWS();
            serie = ws.leerSerie(aux.get(i).getSerieId());
            miLista.add(serie);
        }
        return miLista;
    }

    public ArrayList<Serie> leerSeriesPopulares() throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Serie> miLista = ws.leerSeriesPopulares();
        return miLista;
    }

    public ArrayList<Serie> buscarSerie(String tituloSerie) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Serie> miLista = new ArrayList<>();
        miLista = ws.buscarSerie(tituloSerie);
        return miLista;
    }


    public long insertarTemporada(Temporada t) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.insertarTemporada(t);
        return registrosAfectados;
    }

//    public long eliminarTemporada(Integer serieId,Integer numeroTemporada) throws ExcepcionRecordWatch {
//        ComponenteBD bd = new ComponenteBD(context);
//        long registrosAfectados = bd.eliminarTemporada(serieId,numeroTemporada);
//        return registrosAfectados;
//    }

//    public long modificarTemporada(Integer serieId, int numeroTemporada,Temporada t) throws ExcepcionRecordWatch {
//        ComponenteBD bd = new ComponenteBD(context);
//        long registrosAfectados = bd.modificarTemporada(serieId,numeroTemporada,t);
//        return registrosAfectados;
//    }

    public Temporada leerTemporada(Integer serieId,int numeroTemporada) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        Temporada temporada = ws.leerTemporada(serieId,numeroTemporada);
        return temporada;
    }

    public ArrayList<Temporada> leerTemporadasBD(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        ArrayList<Temporada> miLista = bd.leerTemporadas(serieId);
        return miLista;
    }

    public ArrayList<Temporada> leerTemporadasWS(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Temporada> miLista = ws.leerTemporadas(serieId);
        return miLista;
    }

    public Integer eliminarTemporadas(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        int registrosEliminados = bd.eliminarTemporadas(serieId);
        return registrosEliminados;
    }


    public long insertarEpisodio(Episodio e) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.insertarEpisodio(e);
        return registrosAfectados;
    }

    public long eliminarEpisodio(Integer serieId,int numeroTemporada,int numeroEpisodio) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.eliminarEpisodio(serieId,numeroTemporada,numeroEpisodio);
        return registrosAfectados;
    }

//    public long modificarEpisodio(Integer serieId, int numeroTemporada,int numeroEpisodio,Episodio e) throws ExcepcionRecordWatch {
//        return 0;
//    }

    public Episodio leerEpisodio(Integer serieId,int numeroTemporada,int numeroEpisodio) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Episodio episodio = bd.leerEpisodio(serieId,numeroTemporada,numeroEpisodio);
        return episodio;
    }

    public ArrayList<Episodio> leerEpisodios(Integer serieId,int numeroTemporada) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Episodio> miLista = ws.leerEpisodios(serieId,numeroTemporada);
        return miLista;
    }

    public Integer eliminarEpisodios(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        int registrosEliminados = bd.eliminarEpisodios(serieId);
        return registrosEliminados;
    }

}
