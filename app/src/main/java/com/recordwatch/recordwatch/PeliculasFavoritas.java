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

import java.util.ArrayList;

import com.recordwatch.recordwatch.adaptadores.AdaptadorPeliculas;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Pelicula;

import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;

/**
 * Activity que muestra el listado de las películas favoritas por el usuario
 */
public class PeliculasFavoritas extends AppCompatActivity {
    ArrayList<Pelicula> miLista;
    RecyclerView miRecycler;
    AdaptadorPeliculas elAdaptador;
    EditText tituloPelicula;
    TextView nombrePelicula;
    TextView valoracionPelicula;
    static ImageView fotoPelicula;
    SwipeRefreshLayout refrescar;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas_favoritas);
        this.setTitle(R.string.peliculasFavoritas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tituloPelicula = findViewById(R.id.etTituloPelicula);
        nombrePelicula = findViewById(R.id.idTituloEpisodio);
        valoracionPelicula = findViewById(R.id.idValoracionSerie);
        fotoPelicula = findViewById(R.id.idFotoEpisodio);
        refrescar = findViewById(R.id.refrescarPeliculasFavoritas);

        miLista = new ArrayList<Pelicula>();
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaPeliculasFavoritas);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorPeliculas(this, miLista);
        //Si se clica en una pelicula,esta se mostrará en la pantalla de pelicula detallada
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence texto = "Pulsado: " + miLista.get(miRecycler.getChildAdapterPosition(v)).getTitulo();
                codigoPeliculaElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getPeliculaId();
                Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
                toast.show();
                mostrarPelicula();
                elAdaptador.notifyItemChanged(miRecycler.getChildAdapterPosition(v));
            }
        });
        miRecycler.setAdapter(elAdaptador);
        //Se volverá a cargar la información de la activity si el usuario desliza el dedo de arriba a abajo de la pantalla
        refrescar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refrescar.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        ArrayList<Pelicula> aux = new ArrayList<>();
        try {
            //Muestra la información de las peliculas de la base de datos con estado favorita
            ComponenteCAD cad = new ComponenteCAD(this);
            aux = cad.leerPeliculas("F");
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for (int i = 0; i < aux.size(); i++) {
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }
    }

    /**
     * Método que muestra la pantallla de mostrar pelicula en detalle
     */
    private void mostrarPelicula() {
        Intent i = new Intent(this, PeliculaDetallada.class);
        startActivity(i);
    }

}
