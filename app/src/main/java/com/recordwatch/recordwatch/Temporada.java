package com.recordwatch.recordwatch;

public class Temporada {

    private Integer serieId;
    private int numeroTemporada;
    private String tituloTemporada;
    private int numeroCapitulos;
    private String sinopsis;
    private String rutaPoster;

    public Temporada() {
    }

    public Temporada(Integer serieId, int numeroTemporada, String tituloTemporada, int numeroCapitulos, String sinopsis, String rutaPoster) {
        this.serieId = serieId;
        this.numeroTemporada = numeroTemporada;
        this.tituloTemporada = tituloTemporada;
        this.numeroCapitulos = numeroCapitulos;
        this.sinopsis = sinopsis;
        this.rutaPoster = rutaPoster;
    }

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public int getNumeroTemporada() {
        return numeroTemporada;
    }

    public void setNumeroTemporada(int numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }

    public String getTituloTemporada() {
        return tituloTemporada;
    }

    public void setTituloTemporada(String tituloTemporada) {
        this.tituloTemporada = tituloTemporada;
    }

    public int getNumeroCapitulos() {
        return numeroCapitulos;
    }

    public void setNumeroCapitulos(int numeroCapitulos) {
        this.numeroCapitulos = numeroCapitulos;
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
