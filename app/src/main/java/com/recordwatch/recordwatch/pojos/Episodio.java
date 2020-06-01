package com.recordwatch.recordwatch.pojos;

public class Episodio {

    private Integer serieId;
    private int numeroTemporada;
    private int numeroEpisodio;
    private String tituloEpisodio;
    private String rutaPoster;
    private String visto;

    public Episodio() {
    }

    public Episodio(Integer serieId, int numeroTemporada, int numeroEpisodio, String tituloEpisodio, String rutaPoster, String visto) {
        this.serieId = serieId;
        this.numeroTemporada = numeroTemporada;
        this.numeroEpisodio = numeroEpisodio;
        this.tituloEpisodio = tituloEpisodio;
        this.rutaPoster = rutaPoster;
        this.visto = visto;
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

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public String getTituloEpisodio() {
        return tituloEpisodio;
    }

    public void setTituloEpisodio(String tituloEpisodio) {
        this.tituloEpisodio = tituloEpisodio;
    }

    public String getRutaPoster() {
        return rutaPoster;
    }

    public void setRutaPoster(String rutaPoster) {
        this.rutaPoster = rutaPoster;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

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
