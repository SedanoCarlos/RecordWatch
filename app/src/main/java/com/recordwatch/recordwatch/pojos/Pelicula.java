package com.recordwatch.recordwatch.pojos;

/**
 * En esta clase se gestiona la creación de objetos de tipo Pelicula
 * y la inserción de sus datos.
 *
 * @author Carlos
 */
public class Pelicula {

    private Integer peliculaId;
    private String titulo;
    private String valoracion;
    private String sinopsis;
    private String rutaPoster;
    private String estado;

    /**
     * Este método permite crear un objeto Pelicula sin datos.
     *
     */
    public Pelicula() {
    }

    /**
     * Este método crear un objeto Pelicula indicando todos sus datos.
     *
     * @param peliculaId Entero que indica el id de la pelicula a insertar
     * @param titulo String que indica el titulo de la pelicula a insertar
     * @param valoracion String que indica la valoracion de la pelicula a insertar
     * @param sinopsis String que indica la sinopsis de la pelicula a insertar
     * @param rutaPoster Entero que indica la ruta del poster de la pelicula a insertar
     * @param estado String que indica el estado de la pelicula a insertar
     */
    public Pelicula(Integer peliculaId, String titulo, String valoracion, String sinopsis, String rutaPoster, String estado) {
        this.peliculaId = peliculaId;
        this.titulo = titulo;
        this.valoracion = valoracion;
        this.sinopsis = sinopsis;
        this.rutaPoster = rutaPoster;
        this.estado = estado;
    }

    /**
     * Este método devuelve el id de una pelicula.
     *
     * @return id de la pelicula
     */
    public Integer getPeliculaId() {
        return peliculaId;
    }

    /**
     * Este método permite establecer el id de una pelicula.
     *
     * @param peliculaId Entero que indica el id de la pelicula que se establecerá
     */
    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }

    /**
     * Este método devuelve el titulo de una pelicula.
     *
     * @return titulo de la pelicula
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Este método permite establecer el titulo de una pelicula.
     *
     * @param titulo String que indica el titulo de la pelicula que se establecerá
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Este método devuelve la valoracion de una pelicula.
     *
     * @return valoracion de la pelicula
     */
    public String getValoracion() {
        return valoracion;
    }

    /**
     * Este método permite establecer la valoracion de una pelicula.
     *
     * @param valoracion Entero que indica el titulo de la pelicula que se establecerá
     */
    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * Este método devuelve la sinopsis de una pelicula.
     *
     * @return sinopsis de la pelicula
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Este método permite establecer la sinopsis de una pelicula.
     *
     * @param sinopsis String que indica la sinopsis de la pelicula que se establecerá
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Este método devuelve la ruta del poster de una pelicula.
     *
     * @return ruta del poster de la pelicula
     */
    public String getRutaPoster() {
        return rutaPoster;
    }

    /**
     * Este método permite establecer la ruta del poster de una pelicula.
     *
     * @param rutaPoster String que indica el titulo de la pelicula que se establecerá
     */
    public void setRutaPoster(String rutaPoster) {
        this.rutaPoster = rutaPoster;
    }

    /**
     * Este método devuelve el estado de una pelicula.
     *
     * @return estado de la pelicula
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Este método permite establecer el estado de una pelicula.
     *
     * @param estado String que indica el estado de la pelicula que se establecerá
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Este método permite mostrar todos los datos de
     * un objeto de tipo Pelicula.
     *
     * @return Cadena de texto con los todos y cada uno de los datos de un
     * objeto de tipo Pelicula

     */
    @Override
    public String toString() {
        return "Pelicula{" +
                "peliculaId=" + peliculaId +
                ", titulo='" + titulo + '\'' +
                ", valoracion='" + valoracion + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", rutaPoster='" + rutaPoster + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
