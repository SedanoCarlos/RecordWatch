package com.recordwatch.recordwatch;

import android.content.Intent;
import android.os.Bundle;
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
import com.recordwatch.recordwatch.componentes.ComponenteWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;


public class TemporadasActivity extends AppCompatActivity {

    ArrayList<Temporada> miLista;
    RecyclerView miRecycler;
    AdaptadorTemporadas elAdaptador;
    TextView nombreTemporada;
    TextView numeroCapitulos;
    static ImageView fotoTemporada;
    static public int numeroTemporadaElegida;
    static public int primeraTemporada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporadas);
        this.setTitle(R.string.listaTemporada);

        nombreTemporada = findViewById(R.id.idNombre);
        numeroCapitulos = findViewById(R.id.idNumeroCapitulos);
        fotoTemporada = findViewById(R.id.idFotoEpisodio);

        miLista = new ArrayList<Temporada>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecylerVistaTemporadas);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorTemporadas(this, miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos el nombre del elemento seleccionado
                CharSequence texto = "Pulsado: " + miLista.get(miRecycler.getChildAdapterPosition(v)).getTituloTemporada();
                numeroTemporadaElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getNumeroTemporada();
                Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
                toast.show();
                mostrarTemporada();
                elAdaptador.notifyItemChanged(miRecycler.getChildAdapterPosition(v));
            }
        });
        miRecycler.setAdapter(elAdaptador);


        String url="https://api.themoviedb.org/3/tv/"+codigoSerieElegida+"?api_key=2f6c71bda35c7c12888918e27e405df2&language=es-ES";
        RequestQueue queue = Volley.newRequestQueue(this);
        primeraTemporada = 0;


        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray array = (JSONArray) jsonObject.get("seasons"); // it should be any array name
                                JSONObject datos = (JSONObject) array.get(0);
                                primeraTemporada = datos.getInt("season_number");
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("PRIMERA TEMPORADA", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);


        ArrayList<Temporada> aux = new ArrayList<>();
        try {
            ComponenteWS ws = new ComponenteWS();
            aux = ws.leerTemporadas(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for (int i = 0; i < aux.size(); i++) {
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }
    }

    private void mostrarTemporada() {
        Intent i = new Intent(this, TemporadaDetallada.class);
        startActivity(i);
    }
}
