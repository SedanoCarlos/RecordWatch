package com.recordwatch.recordwatch.pojos;

/**
 * En esta clase se gestiona la creación de objetos de tipo Serie
 * y la inserción de sus datos.
 *
 * @author Carlos
 */
public class Serie {

    private Integer serieId;
    private String titulo;
    private String valoracion;
    private String sinopsis;
    private String rutaPoster;
    private String estado;

    /**
     * Este método permite crear un objeto Serie sin datos.
     *
     */
    public Serie() {
    }

    /**
     * Este método crear un objeto Serie indicando todos sus datos.
     *
     * @param serieId Entero que indica el id de la serie a insertar
     * @param titulo String que indica el titulo de la serie a insertar
     * @param valoracion String que indica la valoracion de la serie a insertar
     * @param sinopsis String que indica la sinopsis de la serie a insertar
     * @param rutaPoster Entero que indica la ruta del poster de la serie a insertar
     * @param estado String que indica el estado de la serie a insertar
     */
    public Serie(Integer serieId, String titulo, String valoracion, String sinopsis, String rutaPoster, String estado) {
        this.serieId = serieId;
        this.titulo = titulo;
        this.valoracion = valoracion;
        this.sinopsis = sinopsis;
        this.rutaPoster = rutaPoster;
        this.estado = estado;
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
     * Este método devuelve el titulo de una serie.
     *
     * @return titulo de la serie
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Este método permite establecer el titulo de una serie.
     *
     * @param titulo String que indica el titulo de la serie que se establecerá
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Este método devuelve la valoracion de una serie.
     *
     * @return valoracion de la serie
     */
    public String getValoracion() {
        return valoracion;
    }

    /**
     * Este método permite establecer la valoracion de una serie.
     *
     * @param valoracion Entero que indica el titulo de la serie que se establecerá
     */
    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * Este método devuelve la sinopsis de una serie.
     *
     * @return sinopsis de la serie
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Este método permite establecer la sinopsis de una serie.
     *
     * @param sinopsis String que indica la sinopsis de la serie que se establecerá
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Este método devuelve la ruta del poster de una serie.
     *
     * @return ruta del poster de la serie
     */
    public String getRutaPoster() {
        return rutaPoster;
    }

    /**
     * Este método permite establecer la ruta del poster de una serie.
     *
     * @param rutaPoster String que indica el titulo de la serie que se establecerá
     */
    public void setRutaPoster(String rutaPoster) {
        this.rutaPoster = rutaPoster;
    }

    /**
     * Este método devuelve el estado de una serie.
     *
     * @return estado de la serie
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Este método permite establecer el estado de una serie.
     *
     * @param estado String que indica el estado de la serie que se establecerá
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Este método permite mostrar todos los datos de
     * un objeto de tipo Serie.
     *
     * @return Cadena de texto con los todos y cada uno de los datos de un
     * objeto de tipo Serie

     */
    @Override
    public String toString() {
        return "Serie{" +
                "serieId=" + serieId +
                ", titulo='" + titulo + '\'' +
                ", valoracion='" + valoracion + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", rutaPoster='" + rutaPoster + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
