package com.recordwatch.recordwatch.pojos;

/**
 * En esta clase se gestiona la creación de objetos de tipo Temporada
 * y la inserción de sus datos.
 *
 * @author Carlos
 */
public class Temporada {

    private Integer serieId;
    private int numeroTemporada;
    private String tituloTemporada;
    private int numeroCapitulos;
    private String sinopsis;
    private String rutaPoster;

    /**
     * Este método permite crear un objeto Temporada sin datos.
     *
     */
    public Temporada() {
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
     * Este método devuelve el titulo de una temporada.
     *
     * @return titulo de la temporada
     */
    public String getTituloTemporada() {
        return tituloTemporada;
    }

    /**
     * Este método permite establecer el titulo de una temporada.
     *
     * @param tituloTemporada String que indica el titulo de la temporada que se establecerá
     */
    public void setTituloTemporada(String tituloTemporada) {
        this.tituloTemporada = tituloTemporada;
    }

    /**
     * Este método devuelve el numero de capitulos de una temporada.
     *
     * @return numero de capitulos de la temporada
     */
    public int getNumeroCapitulos() {
        return numeroCapitulos;
    }

    /**
     * Este método permite establecer el numero de capitulos de una temporada.
     *
     * @param numeroCapitulos Entero que indica el numero de capitulos de la temporada que se establecerá
     */
    public void setNumeroCapitulos(int numeroCapitulos) {
        this.numeroCapitulos = numeroCapitulos;
    }

    /**
     * Este método devuelve la sinopsis de una temporada.
     *
     * @return sinopsis de la temporada
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Este método permite establecer la sinopsis de una temporada.
     *
     * @param sinopsis String que indica la sinopsis de la temporada que se establecerá
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Este método devuelve la ruta del poster de una temporada.
     *
     * @return ruta del poster de la temporada
     */
    public String getRutaPoster() {
        return rutaPoster;
    }

    /**
     * Este método permite establecer la ruta del poster de una temporada.
     *
     * @param rutaPoster String que indica el titulo de la temporada que se establecerá
     */
    public void setRutaPoster(String rutaPoster) {
        this.rutaPoster = rutaPoster;
    }

    /**
     * Este método permite mostrar todos los datos de
     * un objeto de tipo Temporada.
     *
     * @return Cadena de texto con los todos y cada uno de los datos de un
     * objeto de tipo Temporada
     */
    @Override
    public String toString() {
        return "Temporada{" +
                "serieId=" + serieId +
                ", numeroTemporada=" + numeroTemporada +
                ", tituloTemporada='" + tituloTemporada + '\'' +
                ", numeroCapitulos=" + numeroCapitulos +
                ", sinopsis='" + sinopsis + '\'' +
                ", rutaPoster='" + rutaPoster + '\'' +
                '}';
    }
}
