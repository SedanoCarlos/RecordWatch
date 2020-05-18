package com.recordwatch.recordwatch.componentes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.recordwatch.recordwatch.AdminSQLite;
import com.recordwatch.recordwatch.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.JSONTask;
import com.recordwatch.recordwatch.Pelicula;
import com.recordwatch.recordwatch.Serie;
import com.recordwatch.recordwatch.Temporada;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ComponenteCAD {
    Context context;

    public ComponenteCAD(Context con) throws ExcepcionRecordWatch {
        context=con;
    }

    public Integer insertarPelicula(Pelicula p) throws ExcepcionRecordWatch {
        return null;
    }

    public Integer eliminarPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        return null;
    }

    public int modificarPelicula(Integer peliculaId, Pelicula p) throws ExcepcionRecordWatch {
        return 0;
    }

    public Pelicula leerPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        return null;
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
        return null;
    }

    public ArrayList<Pelicula> leerPeliculasPopulares() throws ExcepcionRecordWatch {
        return null;
    }

    public ArrayList<Serie> buscarSerie(String tituloSerie) throws ExcepcionRecordWatch {
        return null;
    }

    public Integer insertarSerie(Serie s) throws ExcepcionRecordWatch {
        return null;
    }

    public Integer eliminarSerie(Integer serieId) throws ExcepcionRecordWatch {
        return null;
    }

    public int modificarSerie(Integer serieId, Serie s) throws ExcepcionRecordWatch {
        return 0;
    }

    public Serie leerSerie(Integer serieId) throws ExcepcionRecordWatch {
        return null;
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
        return null;
    }

    public Integer insertarTemporada(Temporada t) throws ExcepcionRecordWatch {
        return null;
    }

    public Integer eliminarTemporada(Integer serieId,Integer numeroTemporada) throws ExcepcionRecordWatch {
        return null;
    }

    public int modificarTemporada(Integer serieId, int numeroTemporada,Temporada t) throws ExcepcionRecordWatch {
        return 0;
    }

    public Temporada leerTemporada(Integer serieId,int numeroTemporada) throws ExcepcionRecordWatch {
        return null;
    }

    public ArrayList<Temporada> leerTemporadas(Integer serieId) throws ExcepcionRecordWatch {
        return null;
    }

    public Integer insertarEpisodio(Episodio e) throws ExcepcionRecordWatch {
        return null;
    }

    public Integer eliminarEpisodio(Integer serieId,int numeroTemporada,int numeroEpisodio) throws ExcepcionRecordWatch {
        return null;
    }

    public int modificarEpisodio(Integer serieId, int numeroTemporada,int numeroEpisodio,Episodio e) throws ExcepcionRecordWatch {
        return 0;
    }

    public Episodio leerEpisodio(Integer serieId,int numeroTemporada,int numeroEpisodio) throws ExcepcionRecordWatch {
        return null;
    }

    public ArrayList<Episodio> leerEpisodios(Integer serieId,int numeroTemporada) throws ExcepcionRecordWatch {
        return null;
    }
}
