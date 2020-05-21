package com.recordwatch.recordwatch;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.componentes.ComponenteWS;

import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;
import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.numeroTemporadaElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.primeraTemporada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class TemporadaDetallada extends AppCompatActivity {

    ArrayList<Episodio> miLista;
    RecyclerView miRecycler;
    AdaptadorEpisodios elAdaptador;
    TextView sinopsis;
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporada_detallada);
        this.setTitle("Temporada "+numeroTemporadaElegida);

        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);
        miLista = new ArrayList<Episodio>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecylerVistaEpisodios);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorEpisodios(this,miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos el nombre del elemento seleccionado
                CharSequence texto = "Pulsado: " + miLista.get(miRecycler.getChildAdapterPosition(v)).getTituloEpisodio();
                numeroTemporadaElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getNumeroTemporada();
                Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
                toast.show();
                elAdaptador.notifyItemChanged(miRecycler.getChildAdapterPosition(v));
            }
        });
        miRecycler.setAdapter(elAdaptador);


        ComponenteWS ws = null;
        Temporada temporada = new Temporada();
        try {
            ws = new ComponenteWS();
            temporada = ws.leerTemporada(codigoSerieElegida,numeroTemporadaElegida);
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
            ws = new ComponenteWS();
            aux = ws.leerEpisodios(codigoSerieElegida,numeroTemporadaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for(int i=0;i<aux.size();i++){
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }

    }

}
