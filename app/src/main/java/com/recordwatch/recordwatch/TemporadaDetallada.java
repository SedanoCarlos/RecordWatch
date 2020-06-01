package com.recordwatch.recordwatch;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.adaptadores.AdaptadorEpisodios;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.componentes.ComponenteWS;
import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.pojos.Temporada;

import static com.recordwatch.recordwatch.CopiaSeguridad.cad;
import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.numeroTemporadaElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.tituloTemporadaElegida;

import java.util.ArrayList;


public class TemporadaDetallada extends AppCompatActivity {

    ArrayList<Episodio> miLista;
    RecyclerView miRecycler;
    AdaptadorEpisodios elAdaptador;
    TextView sinopsis;
    ImageView foto;
    Button leerTexto;
    private TtsManager ttsManager = null;
    private int stopTtsManager = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporada_detallada);
        this.setTitle(""+tituloTemporadaElegida);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);
        miLista = new ArrayList<Episodio>();//Lista de objetos
        leerTexto = findViewById(R.id.buttonLeer);
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecylerVistaEpisodios);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorEpisodios(this,miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos el nombre del elemento seleccionado

                elAdaptador.notifyItemChanged(miRecycler.getChildAdapterPosition(v));
            }
        });
        miRecycler.setAdapter(elAdaptador);

        leerTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readingDescription();
            }
        });



        ComponenteCAD ws = null;
        Temporada temporada = new Temporada();
        try {
            cad = new ComponenteCAD(this);
            temporada = cad.leerTemporada(codigoSerieElegida,numeroTemporadaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        sinopsis.setText(temporada.getSinopsis());
        String poster = temporada.getRutaPoster();
        if (poster.equals("null")) {
            Glide.with(TemporadaDetallada.this).load(poster).placeholder(R.drawable.pelicula).into(foto);
        } else {
            Glide.with(TemporadaDetallada.this).load(poster).placeholder(R.drawable.pelicula).into(foto);
        }


        ArrayList<Episodio> aux  = new ArrayList<>();
        try {
            cad = new ComponenteCAD(this);
            aux = cad.leerEpisodios(codigoSerieElegida,numeroTemporadaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for(int i=0;i<aux.size();i++){
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }

        checkDescription();
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
