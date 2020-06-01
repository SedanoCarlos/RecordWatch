package com.recordwatch.recordwatch.pojos;

public class Pelicula {

    private Integer peliculaId;
    private String titulo;
    private String valoracion;
    private String sinopsis;
    private String rutaPoster;
    private String estado;

    public Pelicula() {
    }

    public Pelicula(Integer peliculaId, String titulo, String valoracion, String sinopsis, String rutaPoster, String estado) {
        this.peliculaId = peliculaId;
        this.titulo = titulo;
        this.valoracion = valoracion;
        this.sinopsis = sinopsis;
        this.rutaPoster = rutaPoster;
        this.estado = estado;
    }

    public Integer getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getRutaPoster() {
        return rutaPoster;
    }

    public void setRutaPoster(String rutaPoster) {
        this.rutaPoster = rutaPoster;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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
