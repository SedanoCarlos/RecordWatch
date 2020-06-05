package com.recordwatch.recordwatch;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Serie;
import com.recordwatch.recordwatch.pojos.Temporada;
import com.recordwatch.recordwatch.utilidades.TTSManager;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;

/**
 * Activity que nos muestra la información de una serie en detalle
 */
public class SerieDetallada extends AppCompatActivity {

    static TextView titulo;
    TextView valoracion;
    TextView sinopsis;
    ImageView foto;
    Button serieRegistrada;
    Button leerTexto;
    private TTSManager lecturaVoz = null;
    private int hablando = 0;
    public static String sinopsisSerie;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detallada);
        this.setTitle(R.string.serieDetallada);

        titulo = findViewById(R.id.tvTitulo);
        valoracion = findViewById(R.id.tvValoracion);
        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);
        serieRegistrada = findViewById(R.id.buttonSerieRegistrada);
        leerTexto = findViewById(R.id.buttonLeer);

        leerTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerSinopsis();
            }
        });
        serieRegistrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(SerieDetallada.this, serieRegistrada);
                popup.getMenuInflater().inflate(R.menu.popup_series, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cambiarEstado(item);
                        Toast.makeText(SerieDetallada.this, "Has pulsado : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });
        Serie serie = new Serie();
        try {
            //Comprueba si la pelicula existe en la base de datos
            ComponenteCAD cad = new ComponenteCAD(this);
            serie = cad.leerSerieBD(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
        }
        //Depende de si existe o no,muestra un estado diferente
        if (serie != null) {
            comprobarEstado();
        } else {
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
        }
        ComponenteCAD cad2 = null;
        serie = new Serie();
        try {
            cad2 = new ComponenteCAD(this);
            //Busca la información de la pelicula en la api de TheMovieDB y muestra su información en los campos
            serie = cad2.leerSerieWS(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        titulo.setText(serie.getTitulo());
        valoracion.setText(serie.getValoracion());
        sinopsisSerie = serie.getSinopsis();
        sinopsis.setText(sinopsisSerie);
        String poster = serie.getRutaPoster();
        if (poster == null) {
            Glide.with(SerieDetallada.this).load(poster).placeholder(R.drawable.series).into(foto);
        } else {
            Glide.with(SerieDetallada.this).load(poster).placeholder(R.drawable.series).into(foto);
        }
        comprobarSinopis();
    }

    /**
     * Método que cambia el estado de la serie según que opción del menu se marque
     * @param item elemento del menú seleccionado
     */
    public void cambiarEstado(MenuItem item) {
        Serie aux = new Serie();
        ComponenteCAD cad = null;
        try {
            cad = new ComponenteCAD(this);
            aux = cad.leerSerieBD(codigoSerieElegida);
            Serie serie = new Serie();
            if (aux == null) {
                serie.setSerieId(codigoSerieElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    serie.setEstado("P");
                    cad.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                    ArrayList<Temporada> listaTemporadas = new ArrayList<>();
                    listaTemporadas = cad.leerTemporadasBD(codigoSerieElegida);
                    if (listaTemporadas == null) {
                        listaTemporadas = cad.leerTemporadasWS(codigoSerieElegida);
                        for (int i = 0; i < listaTemporadas.size(); i++) {
                            Temporada temporada = new Temporada();
                            temporada.setSerieId(codigoSerieElegida);
                            temporada.setNumeroTemporada(i);
                            cad.insertarTemporada(temporada);
                        }
                    }
                } else if (item.getTitle().equals("Marcar como siguiendo")) {
                    serie.setEstado("S");
                    cad.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
                    ArrayList<Temporada> listaTemporadas = new ArrayList<>();
                    listaTemporadas = cad.leerTemporadasBD(codigoSerieElegida);
                    if (listaTemporadas == null) {
                        listaTemporadas = cad.leerTemporadasWS(codigoSerieElegida);
                        for (int i = 0; i < listaTemporadas.size(); i++) {
                            Temporada temporada = new Temporada();
                            temporada.setSerieId(codigoSerieElegida);
                            temporada.setNumeroTemporada(i);
                            cad.insertarTemporada(temporada);
                        }
                    }
                } else if (item.getTitle().equals("Marcar como vista")) {
                    serie.setEstado("V");
                    cad.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                    ArrayList<Temporada> listaTemporadas = new ArrayList<>();
                    listaTemporadas = cad.leerTemporadasBD(codigoSerieElegida);
                    if (listaTemporadas == null) {
                        listaTemporadas = cad.leerTemporadasWS(codigoSerieElegida);
                        for (int i = 0; i < listaTemporadas.size(); i++) {
                            Temporada temporada = new Temporada();
                            temporada.setSerieId(codigoSerieElegida);
                            temporada.setNumeroTemporada(i);
                            cad.insertarTemporada(temporada);
                        }
                    }
                } else if (item.getTitle().equals("Sin Clasificar")) {
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            } else {
                serie.setSerieId(codigoSerieElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    serie.setEstado("P");
                    cad.modificarSerie(codigoSerieElegida, serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como siguiendo")) {
                    serie.setEstado("S");
                    cad.modificarSerie(codigoSerieElegida, serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    serie.setEstado("V");
                    cad.modificarSerie(codigoSerieElegida, serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Sin Clasificar")) {
                    cad.eliminarSerie(codigoSerieElegida);
                    cad.eliminarTemporadas(codigoSerieElegida);
                    cad.eliminarEpisodios(codigoSerieElegida);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
    }

    /**
     * Método que comprueba el estado de la series,para saber que icono mostrar
     */
    public void comprobarEstado() {
        Serie serie = new Serie();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            serie = cad.leerSerieBD(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
        }
        if (serie.getEstado().equals("P")) {
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
        } else if (serie.getEstado().equals("S")) {
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
        } else if (serie.getEstado().equals("V")) {
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
        }
    }

    /**
     * Método que muestra la pantalla de temporadas respectivas para la serie elegida
     * @param view de tipo View usada para la representación en pantalla de los elementos pertenecientes a la activity
     */
    public void mostrarTemporadas(View view) {
        Intent i = new Intent(this, TemporadasActivity.class);
        startActivity(i);
    }

    /**
     * Método que comprueba si hay sinopsis de la serie
     */
    private void comprobarSinopis() {
        //Si existe sinopsis para la serie, el botón de lectura en voz se mostrará,de lo contrario se ocultará
        if (!sinopsis.getText().toString().isEmpty()) {
            lecturaVoz = new TTSManager();
            lecturaVoz.init(this);
            leerTexto.setVisibility(View.VISIBLE);
        } else {
            leerTexto.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Método que comprueba si la lectura en voz todavía se esta reproduciendo
     */
    public void leerSinopsis() {
        switch (hablando) {
            case 0:
                hablando = 1;
                lecturaVoz.initQueue(sinopsis.getText().toString());
                break;
            case 1:
                hablando = 0;
                lecturaVoz.stop();
                break;
        }
    }

    /**
     * Método que corta la reproducción de la lectura en voz en caso de que se salga de la actividad
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lecturaVoz != null) {
            lecturaVoz.shutDown();
        }
    }
}
