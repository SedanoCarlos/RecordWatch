package com.recordwatch.recordwatch.pojos;

/**
 * En esta clase se gestiona la creación de objetos de tipo Episodio
 * y la inserción de sus datos.
 *
 * @author Carlos
 */
public class Episodio {

    private Integer serieId;
    private int numeroTemporada;
    private int numeroEpisodio;
    private String tituloEpisodio;
    private String rutaPoster;
    private String visto;

    /**
     * Este método permite crear un objeto Episodio sin datos.
     *
     */
    public Episodio() {
    }

    /**
     * Este método devuelve el id de una serie.
            *
            * @return id de la serie
     */
    public Integer getSerieId() {
        return serieId;
    }

    /**
     * Este método permite establecer el id de una serie.
     *
     * @param serieId Entero que indica el id de la serie que se establecerá
     */
    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    /**
     * Este método devuelve el numero de una temporada.
     *
     * @return numero de la temporada
     */
    public int getNumeroTemporada() {
        return numeroTemporada;
    }

    /**
     * Este método permite establecer el numero de una temporada.
     *
     * @param numeroTemporada Entero que indica el numero de la temporada que se establecerá
     */
    public void setNumeroTemporada(int numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }

    /**
     * Este método devuelve el numero de un episodio.
     *
     * @return numero del episodio
     */
    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    /**
     * Este método permite establecer el numero de un episodio.
     *
     * @param numeroEpisodio Entero que indica el numero del episodio que se establecerá
     */
    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    /**
     * Este método devuelve el titulo de un episodio.
     *
     * @return titulo del episodio
     */
    public String getTituloEpisodio() {
        return tituloEpisodio;
    }

    /**
     * Este método permite establecer el titulo de un episodio.
     *
     * @param tituloEpisodio String que indica el titulo del episodio que se establecerá
     */
    public void setTituloEpisodio(String tituloEpisodio) {
        this.tituloEpisodio = tituloEpisodio;
    }

    /**
     * Este método devuelve la ruta del poster de un episodio.
     *
     * @return ruta del poster del episodio
     */
    public String getRutaPoster() {
        return rutaPoster;
    }

    /**
     * Este método permite establecer la ruta del poster de un episodio.
     *
     * @param rutaPoster String que indica el titulo del episodio que se establecerá
     */
    public void setRutaPoster(String rutaPoster) {
        this.rutaPoster = rutaPoster;
    }

    /**
     * Este método devuelve el estado de un episodio.
     *
     * @return estado del episodio
     */
    public String getVisto() {
        return visto;
    }


    /**
     * Este método permite mostrar todos los datos de
     * un objeto de tipo Episodio.
     *
     * @return Cadena de texto con los todos y cada uno de los datos de un
     * objeto de tipo Episodio
     */
    @Override
    public String toString() {
        return "Episodio{" +
                "serieId=" + serieId +
                ", numeroTemporada=" + numeroTemporada +
                ", numeroEpisodio=" + numeroEpisodio +
                ", tituloEpisodio='" + tituloEpisodio + '\'' +
                ", rutaPoster='" + rutaPoster + '\'' +
                ", visto='" + visto + '\'' +
                '}';
    }
}
