package com.recordwatch.recordwatch;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Serie;
import com.recordwatch.recordwatch.pojos.Temporada;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;

import java.util.ArrayList;


public class SerieDetallada extends AppCompatActivity {

    static TextView titulo;
    TextView valoracion;
    TextView sinopsis;
    ImageView foto;
    Button serieRegistrada;
    Button leerTexto;
    private TtsManager ttsManager = null;
    private int stopTtsManager = 0;

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
                readingDescription();
            }
        });

        serieRegistrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(SerieDetallada.this, serieRegistrada);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_series, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cambiarEstado(item);
                        Toast.makeText(SerieDetallada.this,"Has pulsado : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        Serie serie = new Serie();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            serie = cad.leerSerieBD(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if (serie != null) {
            comprobarEstado();
        } else {
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
        }

        ComponenteCAD cad2 = null;
        serie = new Serie();
        try {
            cad2 = new ComponenteCAD(this);
            serie = cad2.leerSerieWS(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }

        titulo.setText(serie.getTitulo());
        valoracion.setText(serie.getValoracion());
        sinopsis.setText(serie.getSinopsis());
        String poster = serie.getRutaPoster();
        if (poster.equals("null")) {
            Glide.with(SerieDetallada.this).load(poster).placeholder(R.drawable.series).into(foto);
        } else {
            Glide.with(SerieDetallada.this).load(poster).placeholder(R.drawable.series).into(foto);
        }

        checkDescription();
    }

    public void cambiarEstado(MenuItem item){
        Serie aux = new Serie();
        ComponenteCAD cad = null;
        try {
            cad = new ComponenteCAD(this);
            aux = cad.leerSerieBD(codigoSerieElegida);
            Serie serie = new Serie();
            if (aux == null) {
                ComponenteCAD cad2 = new ComponenteCAD(this);
                ComponenteCAD cad3 = new ComponenteCAD(this);
                ArrayList<Temporada> listaTemporadas = new ArrayList<>();
                listaTemporadas = cad.leerTemporadasBD(codigoSerieElegida);
                if(listaTemporadas == null){
                    listaTemporadas = cad3.leerTemporadasWS(codigoSerieElegida);
                    for (int i = 0; i < listaTemporadas.size(); i++) {
                        Temporada temporada = new Temporada();
                        temporada.setSerieId(codigoSerieElegida);
                        temporada.setNumeroTemporada(i);
                        cad2.insertarTemporada(temporada);
                    }
                }
                Log.d("Prueba",""+cad2.leerTemporadasBD(codigoSerieElegida));


                serie.setSerieId(codigoSerieElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    serie.setEstado("P");
                    cad.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como siguiendo")) {
                    serie.setEstado("S");
                    cad.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    serie.setEstado("V");
                    cad.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Sin Clasificar")) {
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }else{
                serie.setSerieId(codigoSerieElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    serie.setEstado("P");
                    cad.modificarSerie(codigoSerieElegida,serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como siguiendo")) {
                    serie.setEstado("S");
                    cad.modificarSerie(codigoSerieElegida,serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    serie.setEstado("V");
                    cad.modificarSerie(codigoSerieElegida,serie);
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

    public void comprobarEstado(){
        Serie serie = new Serie();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            serie = cad.leerSerieBD(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if(serie.getEstado().equals("P")){
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
        }else if(serie.getEstado().equals("S")){
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
        }else if(serie.getEstado().equals("V")){
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
        }
    }

    public void mostrarTemporadas(View view) {
        Intent i = new Intent(this,TemporadasActivity.class);
        startActivity(i);
    }

    private void checkDescription() {
        if (!sinopsis.getText().toString().isEmpty()) {
            ttsManager = new TtsManager();
            ttsManager.init(this);
            leerTexto.setVisibility(View.VISIBLE);
        } else {
            leerTexto.setVisibility(View.INVISIBLE);
        }
    }

    public void readingDescription() {
        switch (stopTtsManager) {
            case 0:
                stopTtsManager = 1;
                ttsManager.initQueue(sinopsis.getText().toString());
                break;
            case 1:
                stopTtsManager = 0;
                ttsManager.stop();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttsManager != null) {
            ttsManager.shutDown();
        }
    }
}
