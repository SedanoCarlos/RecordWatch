package com.recordwatch.recordwatch;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.pojos.Temporada;
import com.recordwatch.recordwatch.utilidades.TtsManager;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.numeroTemporadaElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.tituloTemporadaElegida;

import java.util.ArrayList;

/**
 * Activity que nos muestra la información de una temporada en detalle
 */
public class TemporadaDetallada extends AppCompatActivity {

    ArrayList<Episodio> miLista;
    RecyclerView miRecycler;
    AdaptadorEpisodios elAdaptador;
    TextView sinopsis;
    ImageView foto;
    Button leerTexto;
    private TtsManager lecturaVoz = null;
    private int hablando = 0;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporada_detallada);
        this.setTitle(""+tituloTemporadaElegida);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);
        miLista = new ArrayList<Episodio>();
        leerTexto = findViewById(R.id.buttonLeer);
        miRecycler = (RecyclerView) findViewById(R.id.miRecylerVistaEpisodios);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorEpisodios(this,miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elAdaptador.notifyItemChanged(miRecycler.getChildAdapterPosition(v));
            }
        });
        miRecycler.setAdapter(elAdaptador);

        leerTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerSinopsis();
            }
        });

        ComponenteCAD cad = null;
        Temporada temporada = new Temporada();
        try {
            //Buscar temporada en la api
            cad = new ComponenteCAD(this);
            Log.d("TAG","A ver : "+codigoSerieElegida+","+numeroTemporadaElegida);
            temporada = cad.leerTemporada(codigoSerieElegida,numeroTemporadaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
        }
        sinopsis.setText(temporada.getSinopsis());
        String poster = temporada.getRutaPoster();
        Glide.with(TemporadaDetallada.this).load(poster).placeholder(R.drawable.series).into(foto);
        ArrayList<Episodio> aux  = new ArrayList<>();
        try {
            //Buscar los episodios en la api y si están an la base de datos mostrar su estado como visto
            cad = new ComponenteCAD(this);
            aux = cad.leerEpisodiosWS(codigoSerieElegida,numeroTemporadaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for(int i=0;i<aux.size();i++){
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }
        comprobarSinopsis();
    }

    /**
     * Método que comprueba si hay sinopsis de la temporada
     */
    private void comprobarSinopsis() {
        if (!sinopsis.getText().toString().isEmpty()) {
            lecturaVoz = new TtsManager();
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
