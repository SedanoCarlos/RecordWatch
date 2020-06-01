/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recordwatch.recordwatch.componentes;


import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.JSONTask;
import com.recordwatch.recordwatch.pojos.Pelicula;
import com.recordwatch.recordwatch.pojos.Serie;
import com.recordwatch.recordwatch.pojos.Temporada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.recordwatch.recordwatch.TemporadasActivity.numeroTemporadaElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.primeraTemporada;


/**
 * @author Carlos
 */
public class ComponenteWS {

    public static String url;

    public ComponenteWS() throws ExcepcionRecordWatch {

    }


    public Pelicula leerPelicula(Integer peliculaId) throws ExcepcionRecordWatch {
        url = "https://api.themoviedb.org/3/movie/" + peliculaId + "?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        String response = null;
        Pelicula pelicula = new Pelicula();
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            int codigo = jsonObject.getInt("id");
            pelicula.setPeliculaId(codigo);
            String nombre = jsonObject.getString("title");
            pelicula.setTitulo(nombre);
            String valoracion = jsonObject.getString("vote_average");
            pelicula.setValoracion(valoracion);
            String sinopsis = jsonObject.getString("overview");
            pelicula.setSinopsis(sinopsis);
            String foto = jsonObject.getString("poster_path");
            if (foto.equals("null")) {
                pelicula.setRutaPoster("");
            } else {
                pelicula.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pelicula;
    }


    public ArrayList<Pelicula> buscarPelicula(String tituloPelicula) throws ExcepcionRecordWatch {
        ArrayList<Pelicula> miLista = new ArrayList<>();
        url = "https://api.themoviedb.org/3/search/movie?api_key=2f6c71bda35c7c12888918e27e405df2&query=" + tituloPelicula + "&page=1&include_adult=false&language=es-ES";
        String response = null;
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Pelicula pelicula = new Pelicula();
                JSONArray array = (JSONArray) jsonObject.get("results"); // it should be any array name

                JSONObject datos = (JSONObject) array.get(i);
                int codigo = datos.getInt("id");
                pelicula.setPeliculaId(codigo);
                String nombre = datos.getString("title");
                pelicula.setTitulo(nombre);
                String valoracion = datos.getString("vote_average");
                pelicula.setValoracion(valoracion);
                String foto = datos.getString("poster_path");
                if (foto.equals("null")) {
                    pelicula.setRutaPoster("");
                } else {
                    pelicula.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
                }
                miLista.add(pelicula);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return miLista;
    }

    public ArrayList<Pelicula> leerPeliculasPopulares() throws ExcepcionRecordWatch {
        ArrayList<Pelicula> miLista = new ArrayList<>();
        url = "https://api.themoviedb.org/3/movie/popular?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        String response = null;
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 20; i++) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Pelicula pelicula = new Pelicula();
                JSONArray array = (JSONArray) jsonObject.get("results"); // it should be any array name

                JSONObject datos = (JSONObject) array.get(i);
                int codigo = datos.getInt("id");
                pelicula.setPeliculaId(codigo);
                String nombre = datos.getString("title");
                pelicula.setTitulo(nombre);
                String valoracion = datos.getString("vote_average");
                pelicula.setValoracion(valoracion);
                String foto = datos.getString("poster_path");
                if (foto.equals("null")) {
                    pelicula.setRutaPoster("");
                } else {
                    pelicula.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
                }
                miLista.add(pelicula);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return miLista;
    }


    public Serie leerSerie(Integer serieId) throws ExcepcionRecordWatch {
        url = "https://api.themoviedb.org/3/tv/" + serieId + "?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        String response = null;
        Serie serie = new Serie();
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            int codigo = jsonObject.getInt("id");
            serie.setSerieId(codigo);
            String nombre = jsonObject.getString("name");
            serie.setTitulo(nombre);
            String valoracion = jsonObject.getString("vote_average");
            serie.setValoracion(valoracion);
            String sinopsis = jsonObject.getString("overview");
            serie.setSinopsis(sinopsis);
            String foto = jsonObject.getString("poster_path");
            if (foto.equals("null")) {
                serie.setRutaPoster("");
            } else {
                serie.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serie;
    }

    public ArrayList<Serie> leerSeries() throws ExcepcionRecordWatch {
        return null;
    }

    public ArrayList<Serie> buscarSerie(String tituloSerie) throws ExcepcionRecordWatch {
        ArrayList<Serie> miLista = new ArrayList<>();
        url = "https://api.themoviedb.org/3/search/tv?api_key=2f6c71bda35c7c12888918e27e405df2&query=" + tituloSerie + "&page=1&include_adult=false&language=es-ES";
        String response = null;
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Serie serie = new Serie();
                JSONArray array = (JSONArray) jsonObject.get("results"); // it should be any array name

                JSONObject datos = (JSONObject) array.get(i);
                int codigo = datos.getInt("id");
                serie.setSerieId(codigo);
                String nombre = datos.getString("name");
                serie.setTitulo(nombre);
                String valoracion = datos.getString("vote_average");
                serie.setValoracion(valoracion);
                String foto = datos.getString("poster_path");
                if (foto.equals("null")) {
                    serie.setRutaPoster("");
                } else {
                    serie.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
                }
                miLista.add(serie);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return miLista;
    }

    public ArrayList<Serie> leerSeriesPopulares() throws ExcepcionRecordWatch {
        ArrayList<Serie> miLista = new ArrayList<>();
        url = "https://api.themoviedb.org/3/tv/popular?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        String response = null;
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 20; i++) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Serie serie = new Serie();
                JSONArray array = (JSONArray) jsonObject.get("results"); // it should be any array name

                JSONObject datos = (JSONObject) array.get(i);
                int codigo = datos.getInt("id");
                serie.setSerieId(codigo);
                String nombre = datos.getString("name");
                serie.setTitulo(nombre);
                String valoracion = datos.getString("vote_average");
                serie.setValoracion(valoracion);
                String foto = datos.getString("poster_path");
                if (foto.equals("null")) {
                    serie.setRutaPoster("");
                } else {
                    serie.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
                }
                miLista.add(serie);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return miLista;
    }


    public Temporada leerTemporada(Integer serieId, int numeroTemporada) throws ExcepcionRecordWatch {
        url = "https://api.themoviedb.org/3/tv/" + serieId + "?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        String response = null;
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject datos = null;
        try {
            datos = new JSONObject(response);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        Temporada temporada = new Temporada();
        try {
            String sinopsisDefecto = datos.getString("overview");
            temporada.setSinopsis(sinopsisDefecto);
            String poster = "https://image.tmdb.org/t/p/w500" + datos.getString("poster_path");
            temporada.setRutaPoster(poster);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return temporada;
    }


    public ArrayList<Temporada> leerTemporadas(Integer serieId) throws ExcepcionRecordWatch {
        ArrayList<Temporada> miLista = new ArrayList<>();
        url = "https://api.themoviedb.org/3/tv/" + serieId + "?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        String response = null;
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 40; i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                try {
                    Temporada temporada = new Temporada();
                    JSONArray array = (JSONArray) jsonObject.get("seasons"); // it should be any array name

                    JSONObject datos = (JSONObject) array.get(i);
                    int codigo = datos.getInt("id");
                    temporada.setSerieId(codigo);
                    numeroTemporadaElegida = i;
                    temporada.setNumeroTemporada(numeroTemporadaElegida);
                    String nombre = datos.getString("name");
                    temporada.setTituloTemporada(nombre);
                    int numeroCapitulos = datos.getInt("episode_count");
                    temporada.setNumeroCapitulos(numeroCapitulos);
                    String foto = datos.getString("poster_path");
                    if (foto.equals("null")) {
                        temporada.setRutaPoster("");
                    } else {
                        temporada.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
                    }
                    miLista.add(temporada);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return miLista;
    }


    public Episodio leerEpisodio(Integer serieId, int numeroTemporada, int numeroEpisodio) throws ExcepcionRecordWatch {
        return null;
    }

    public ArrayList<Episodio> leerEpisodios(Integer serieId, int numeroTemporada) throws ExcepcionRecordWatch {
        ArrayList<Episodio> miLista = new ArrayList<>();
        if (primeraTemporada == 0) {
            url = "https://api.themoviedb.org/3/tv/" + serieId + "/season/" + numeroTemporada + "?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        } else {
            url = "https://api.themoviedb.org/3/tv/" + serieId + "/season/" + (numeroTemporada + 1) + "?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        }
        String response = null;
        try {
            response = new JSONTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 40; i++) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Episodio episodio = new Episodio();
                JSONArray array = (JSONArray) jsonObject.get("episodes"); // it should be any array name
                JSONObject datos = (JSONObject) array.get(i);
                episodio.setNumeroTemporada(numeroTemporada);
                episodio.setNumeroEpisodio(i + 1);
                String nombre = datos.getString("name");
                episodio.setTituloEpisodio(nombre);
                String foto = datos.getString("still_path");
                if (foto.equals("null")) {
                    episodio.setRutaPoster("");
                } else {
                    episodio.setRutaPoster("https://image.tmdb.org/t/p/w500" + foto);
                }
                miLista.add(episodio);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

        }
        return miLista;
    }
}
