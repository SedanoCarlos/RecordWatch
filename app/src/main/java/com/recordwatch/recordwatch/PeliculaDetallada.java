package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Pelicula;


import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;


public class PeliculaDetallada extends AppCompatActivity{

    TextView titulo;
    TextView valoracion;
    TextView sinopsis;
    ImageView foto;
    Button peliculaRegistrada;
    Button leerTexto;
    private TtsManager ttsManager = null;
    private int stopTtsManager = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula_detallada);
        this.setTitle(R.string.peliculaDetallada);


        titulo = findViewById(R.id.tvTitulo);
        valoracion = findViewById(R.id.tvValoracion);
        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);
        peliculaRegistrada = findViewById(R.id.buttonPeliculaRegistrada);
        leerTexto = findViewById(R.id.buttonLeer);

        leerTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readingDescription();
            }
        });

        peliculaRegistrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(PeliculaDetallada.this, peliculaRegistrada);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_peliculas, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cambiarEstado(item);
                        Toast.makeText(PeliculaDetallada.this, "Has pulsado : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });


        Pelicula pelicula = new Pelicula();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            pelicula = cad.leerPeliculaBD(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if (pelicula != null) {
            comprobarEstado();
        } else {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
        }

        pelicula = new Pelicula();
        try {
            ComponenteCAD cad2 = new ComponenteCAD(this);
            pelicula = cad2.leerPeliculaWS(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }

        titulo.setText(pelicula.getTitulo());
        valoracion.setText(pelicula.getValoracion());
        sinopsis.setText(pelicula.getSinopsis());
        String poster = pelicula.getRutaPoster();
        if (poster.equals("null")) {
            Glide.with(PeliculaDetallada.this).load(poster).placeholder(R.drawable.pelicula).into(foto);
        } else {
            Glide.with(PeliculaDetallada.this).load(poster).placeholder(R.drawable.pelicula).into(foto);
        }

        checkDescription();
    }


    public void cambiarEstado(MenuItem item) {
        Pelicula aux = new Pelicula();
        ComponenteCAD cad = null;
        try {
            cad = new ComponenteCAD(this);
            aux = cad.leerPeliculaBD(codigoPeliculaElegida);
            Pelicula pelicula = new Pelicula();
            if (aux == null) {
                pelicula.setPeliculaId(codigoPeliculaElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    pelicula.setEstado("P");
                    cad.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como favorita")) {
                    pelicula.setEstado("F");
                    cad.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    pelicula.setEstado("V");
                    cad.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Sin Clasificar")) {
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }else{
                pelicula.setPeliculaId(codigoPeliculaElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    pelicula.setEstado("P");
                    cad.modificarPelicula(codigoPeliculaElegida,pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como favorita")) {
                    pelicula.setEstado("F");
                    cad.modificarPelicula(codigoPeliculaElegida,pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    pelicula.setEstado("V");
                    cad.modificarPelicula(codigoPeliculaElegida,pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Sin Clasificar")) {
                    cad.eliminarPelicula(codigoPeliculaElegida);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }

    }

    public void comprobarEstado() {
        Pelicula pelicula = new Pelicula();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            pelicula = cad.leerPeliculaBD(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if (pelicula.getEstado().equals("P")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
        } else if (pelicula.getEstado().equals("F")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else if (pelicula.getEstado().equals("V")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
        }


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
