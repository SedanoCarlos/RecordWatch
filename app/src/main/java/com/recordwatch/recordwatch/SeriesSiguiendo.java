package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.recordwatch.recordwatch.adaptadores.AdaptadorSeries;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Serie;

import java.util.ArrayList;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;


public class SeriesSiguiendo extends AppCompatActivity {
    ArrayList<Serie> miLista;
    RecyclerView miRecycler;
    AdaptadorSeries elAdaptador;
    EditText tituloSerie;
    TextView nombreSerie;
    TextView valoracionSerie;
    static ImageView fotoSerie;
    SwipeRefreshLayout refrescar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_siguiendo);
        this.setTitle(R.string.seriesSiguiendo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tituloSerie = findViewById(R.id.etTituloPelicula);
        nombreSerie = findViewById(R.id.idTituloEpisodio);
        valoracionSerie = findViewById(R.id.idValoracionSerie);
        fotoSerie = findViewById(R.id.idFotoEpisodio);
        refrescar = findViewById(R.id.refrescarSeriesSiguiendo);


        miLista = new ArrayList<Serie>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaSeriesSiguiendo);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorSeries(this, miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos el nombre del elemento seleccionado
                CharSequence texto = "Pulsado: " + miLista.get(miRecycler.getChildAdapterPosition(v)).getTitulo();
                codigoSerieElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getSerieId();
                Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
                toast.show();
                mostrarSerie();
                elAdaptador.notifyItemChanged(miRecycler.getChildAdapterPosition(v));
            }
        });
        miRecycler.setAdapter(elAdaptador);

        refrescar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refrescar.setRefreshing(false);
                    }
                },3000);
            }
        });

        ArrayList<Serie> aux = new ArrayList<>();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            aux = cad.leerSeries("S");
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for (int i = 0; i < aux.size(); i++) {
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }
    }


    private void mostrarSerie() {
        Intent i = new Intent(this, SerieDetallada.class);
        startActivity(i);
    }


}
