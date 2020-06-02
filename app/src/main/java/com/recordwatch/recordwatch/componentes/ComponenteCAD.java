package com.recordwatch.recordwatch.componentes;

import android.content.Context;
import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.pojos.Pelicula;
import com.recordwatch.recordwatch.pojos.Serie;
import com.recordwatch.recordwatch.pojos.Temporada;
import com.recordwatch.recordwatch.pojos.Usuario;

import java.util.ArrayList;

/**
 * En esta clase se gestionan todas las operaciones relacionadas con las llamadas a la base de datos o
 * a la api de TheMovieDB mediante llamadas a sus componenetes individuales
 * @author Carlos
 */
public class ComponenteCAD {
    Context context;

    /**
     * Método constructor.
     * @throws ExcepcionRecordWatch se lanzará cuando se produzca
     * @param con Activity desde la cual se llama a este método
     */
    public ComponenteCAD(Context con) throws ExcepcionRecordWatch {
        context=con;
    }

    /**
     * Método que utiliza el ComponenteBD para insertar un usuario en la base de datos
     * @param u objeto de tipo usuario que el ComponenteBD insertará en la tabla Usuarios
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long insertarUsuario(Usuario u) throws ExcepcionRecordWatch{
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.insertarUsuario(u);
        return registrosAfectados;
    }

    /**
     * Método que utiliza el ComponenteBD para modificar un usuario en la base de datos
     * @param usuarioViejo objeto de tipo usuario que el ComponenteBD modificará en la tabla Usuarios
     * @param usuarioNuevo objeto de tipo usuario que el ComponenteBD insertará en la tabla Usuarios
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long modificarUsuario(Usuario usuarioViejo, Usuario usuarioNuevo) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosModificados = bd.modificarUsuario(usuarioViejo,usuarioNuevo);
        return registrosModificados;
    }

    /**
     * Método que utiliza el ComponenteBD para eliminar un usuario en la base de datos
     * @param contraseña cadena de texto que indicará al ComponenteBD  que registro borrar en la tabla Usuarios
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public Integer eliminarUsuario(String contraseña) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Integer registrosEliminados = bd.eliminarUsuario(contraseña);
        return registrosEliminados;
    }

    /**
     * Método que utiliza el ComponenteBD para leer un usuario en la base de datos
     * @param contraseña cadena de texto que indicará al ComponenteBD  que registro leer en la tabla Usuarios
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public Usuario leerUsuario(String contraseña) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Usuario usuario = bd.leerUsuario(contraseña);
        return usuario;

    }

    /**
     * Método que utiliza el ComponenteBD para leer todos los usuarios en la base de datos
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public Usuario leerUsuarios() throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Usuario usuario = bd.leerUsuarios();
        return usuario;
    }

    /**
     * Método que utiliza el ComponenteBD para insertar una pelicula en la base de datos
     * @param p objeto de tipo pelicula que el ComponenteBD insertará en la tabla Peliculas
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long insertarPelicula(Pelicula p) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAñadidos = bd.insertarPelicula(p);
        return registrosAñadidos;
    }

    /**
     * Método que utiliza el ComponenteBD para modificar una pelicula en la base de datos
     * @param peliculaId entero que indica al ComponenteBD que registro modificar en la tabla Peliculas
     * @param p objeto de tipo pelicula que el ComponenteBD insertará en la tabla Pelicula
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long modificarPelicula(Integer peliculaId, Pelicula p) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosModificados = bd.modificarPelicula(peliculaId,p);
        return registrosModificados;
    }

    /**
     * Método que utiliza el ComponenteBD para eliminar una pelicula en la base de datos
     * @param peliculaId entero que indicará al ComponenteBD  que registro borrar en la tabla Peliculas
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long eliminarPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosEliminados = bd.eliminarPelicula(peliculaId);
        return registrosEliminados;
    }

    /**
     * Método que utiliza el ComponenteBD para leer una pelicula en la base de datos
     * @param peliculaId entero que indicará al ComponenteBD  que registro leer en la tabla Peliculas
     * @return objeto de tipo pelicula
     * @throws ExcepcionRecordWatch
     */
    public Pelicula leerPeliculaBD(Integer peliculaId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Pelicula pelicula = bd.leerPelicula(peliculaId);
        return pelicula;
    }

    /**
     * Método que utiliza el ComponenteWS para leer una pelicula de la api de TheMovieDB
     * @param peliculaId entero que indicará al ComponenteWS  que registro de la api de TheMovieDB
     * @return objeto de tipo pelicula
     * @throws ExcepcionRecordWatch
     */
    public Pelicula leerPeliculaWS(Integer peliculaId) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        Pelicula pelicula = ws.leerPelicula(peliculaId);
        return pelicula;
    }

    /**
     * Método que utiliza los ComponentesBD  junto al ComponenteWS para leer varias películas de la base de datos según el estado
     * obteniendo la informaciíon de la api de TheMovieDB
     * @param estado String que indicará al ComponenteBD las películas que se quiere mostrar según ese estado
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
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

    /**
     * Método que utiliza el ComponenteWS para leer varias peliculas de la lista de peliculas populares de la api de TheMovieDB
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
    public ArrayList<Pelicula> leerPeliculasPopulares() throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Pelicula> miLista = ws.leerPeliculasPopulares();
        return miLista;
    }

    /**
     * Método que utiliza el ComponenteWS para buscar una pelicula de la api de TheMovieDB
     * @param tituloPelicula String que indicará al ComponenteWS que peliculas de la api de TheMovieDB mostrar
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
    public ArrayList<Pelicula> buscarPelicula(String tituloPelicula) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Pelicula> miLista;
        miLista = ws.buscarPelicula(tituloPelicula);
        return miLista;
    }

    /**
     * Método que utiliza el ComponenteBD para insertar una serie en la base de datos
     * @param s objeto de tipo serie que el ComponenteBD insertará en la tabla Series
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long insertarSerie(Serie s) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.insertarSerie(s);
        return registrosAfectados;
    }

    /**
     * Método que utiliza el ComponenteBD para modificar una serie en la base de datos
     * @param serieId entero que indica al ComponenteBD que registro modificar en la tabla Series
     * @param s objeto de tipo pelicula que el ComponenteBD insertará en la tabla Series
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long modificarSerie(Integer serieId, Serie s) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.modificarSerie(serieId,s);
        return registrosAfectados;
    }

    /**
     * Método que utiliza el ComponenteBD para eliminar una serie en la base de datos
     * @param serieId entero que indicará al ComponenteBD  que registro borrar en la tabla Series
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long eliminarSerie(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.eliminarSerie(serieId);
        return registrosAfectados;
    }

    /**
     * Método que utiliza el ComponenteBD para leer una serie en la base de datos
     * @param serieId entero que indicará al ComponenteBD  que registro leer en la tabla Series
     * @return objeto de tipo serie
     * @throws ExcepcionRecordWatch
     */
    public Serie leerSerieBD(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Serie serie = bd.leerSerie(serieId);
        return serie;
    }

    /**
     * Método que utiliza el ComponenteWS para leer una serie de la api de TheMovieDB
     * @param serieId entero que indicará al ComponenteWS  que registro de la api de TheMovieDB
     * @return objeto de tipo serie
     * @throws ExcepcionRecordWatch
     */
    public Serie leerSerieWS(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        Serie serie = ws.leerSerie(serieId);
        return serie;
    }

    /**
     * Método que utiliza los ComponentesBD  junto al ComponenteWS para leer varias series de la base de datos según el estado
     * obteniendo la informaciíon de la api de TheMovieDB
     * @param estado String que indicará al ComponenteBD las series que se quiere mostrar según ese estado
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
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

    /**
     * Método que utiliza el ComponenteWS para leer varias series de la lista de series populares de la api de TheMovieDB
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
    public ArrayList<Serie> leerSeriesPopulares() throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Serie> miLista = ws.leerSeriesPopulares();
        return miLista;
    }

    /**
     * Método que utiliza el ComponenteWS para buscar una serie de la api de TheMovieDB
     * @param tituloSerie String que indicará al ComponenteWS que series de la api de TheMovieDB mostrar
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
    public ArrayList<Serie> buscarSerie(String tituloSerie) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Serie> miLista = new ArrayList<>();
        miLista = ws.buscarSerie(tituloSerie);
        return miLista;
    }

    /**
     * Método que utiliza el ComponenteBD para insertar una temporada en la base de datos
     * @param t objeto de tipo serie que el ComponenteBD insertará en la tabla Temporadas
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
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

    /**
     * Método que utiliza el ComponenteWS para leer una temporada de la api de TheMovieDB
     * @param serieId entero que indicará al ComponenteWS  que serie buscar en  la api de TheMovieDB
     * @param numeroTemporada entero que indicará al ComponenteWS  que numero de temporada buscar en la api de TheMovieDB
     * @return objeto de tipo temporada
     * @throws ExcepcionRecordWatch
     */
    public Temporada leerTemporada(Integer serieId,int numeroTemporada) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        Temporada temporada = ws.leerTemporada(serieId,numeroTemporada);
        return temporada;
    }

    /**
     * Método que utiliza el ComponenteBD para leer varias temporadas de la base de datos
     * @param serieId entero que indicará al ComponenteBD temporadas mostrar de la base de datos
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
    public ArrayList<Temporada> leerTemporadasBD(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        ArrayList<Temporada> miLista = bd.leerTemporadas(serieId);
        return miLista;
    }

    /**
     * Método que utiliza el ComponenteWS para leer varias temporadas de la api de TheMovieDB
     * @param serieId entero que indicará al ComponenteBD temporadas mostrar de la api de TheMovieDB
     * @return objeto de tipo lista
     * @throws ExcepcionRecordWatch
     */
    public ArrayList<Temporada> leerTemporadasWS(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Temporada> miLista = ws.leerTemporadas(serieId);
        return miLista;
    }

    /**
     * Método que utiliza el ComponenteBD para eliminar varias temporadas de la base de datos
     * @param serieId entero que indicará al ComponenteBD que temporadas eliminar de la base de datos
     * @return numero de registros eliminados
     * @throws ExcepcionRecordWatch
     */
    public Integer eliminarTemporadas(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        int registrosEliminados = bd.eliminarTemporadas(serieId);
        return registrosEliminados;
    }

    /**
     * Método que utiliza el ComponenteBD para insertar un episodio en la base de datos
     * @param e objeto de tipo episodio que el ComponenteBD insertará en la tabla Episodios
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long insertarEpisodio(Episodio e) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.insertarEpisodio(e);
        return registrosAfectados;
    }

    /**
     * Método que utiliza el ComponenteBD para eliminar un episodio en la base de datos
     * @param serieId entero que indicará al ComponenteBD  que registro borrar en la tabla Episodios
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public long eliminarEpisodio(Integer serieId,int numeroTemporada,int numeroEpisodio) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        long registrosAfectados = bd.eliminarEpisodio(serieId,numeroTemporada,numeroEpisodio);
        return registrosAfectados;
    }

    /**
     * Método que utiliza el ComponenteBD para eliminar varios episodios en la base de datos
     * @param serieId entero que indicará al ComponenteBD  que registros borrar en la tabla Episodios
     * @return numero de registros afectados
     * @throws ExcepcionRecordWatch
     */
    public Integer eliminarEpisodios(Integer serieId) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        int registrosEliminados = bd.eliminarEpisodios(serieId);
        return registrosEliminados;
    }

//    public long modificarEpisodio(Integer serieId, int numeroTemporada,int numeroEpisodio,Episodio e) throws ExcepcionRecordWatch {
//        return 0;
//    }

    /**
     * Método que utiliza el ComponenteBD para leer un episodio en la base de datos
     * @param serieId entero que indicará al ComponenteBD  que registro leer en la tabla Episodios
     * @return objeto de tipo episodio
     * @throws ExcepcionRecordWatch
     */
    public Episodio leerEpisodio(Integer serieId,int numeroTemporada,int numeroEpisodio) throws ExcepcionRecordWatch {
        ComponenteBD bd = new ComponenteBD(context);
        Episodio episodio = bd.leerEpisodio(serieId,numeroTemporada,numeroEpisodio);
        return episodio;
    }

    /**
     * Método que utiliza el ComponenteWS para leer varios episodios de la api de TheMovieDB
     * @param serieId entero que indicará al ComponenteWS que registro de la api de TheMovieDB mostrar
     * @param numeroTemporada entero que indicará al ComponenteWS que numero de temporada buscar en la api de TheMovieDB
     * @return objeto de tipo serie
     * @throws ExcepcionRecordWatch
     */
    public ArrayList<Episodio> leerEpisodios(Integer serieId,int numeroTemporada) throws ExcepcionRecordWatch {
        ComponenteWS ws = new ComponenteWS();
        ArrayList<Episodio> miLista = ws.leerEpisodios(serieId,numeroTemporada);
        return miLista;
    }


}
