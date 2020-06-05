package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import com.recordwatch.recordwatch.adaptadores.AdaptadorPeliculas;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Pelicula;

/**
 * Activity que muestra un listado de peliculas populares además de darnos la opción de buscar una película
 * nosotros mismos o ver un listado de nuestras películas dependiendo de su estado
 */
public class PeliculasActivity extends AppCompatActivity {

    ArrayList<Pelicula> miLista;
    RecyclerView miRecycler;
    AdaptadorPeliculas elAdaptador;
    TextView nombrePelicula;
    TextView valoracionPelicula;
    static ImageView fotoPelicula;
    static public int codigoPeliculaElegida;
    SwipeRefreshLayout refrescar;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);
        this.setTitle(R.string.peliculasPopulares);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombrePelicula = findViewById(R.id.idTituloEpisodio);
        valoracionPelicula = findViewById(R.id.idValoracionSerie);
        fotoPelicula = findViewById(R.id.idFotoEpisodio);
        refrescar = findViewById(R.id.refrescarPeliculasPopulares);

        miLista = new ArrayList<Pelicula>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaPeliculasPopulares);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorPeliculas(this, miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos el nombre del elemento seleccionado
                codigoPeliculaElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getPeliculaId();
                mostrarPelicula();
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
                }, 3000);
            }
        });
        ArrayList<Pelicula> aux = new ArrayList<>();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            aux = cad.leerPeliculasPopulares();
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for (int i = 0; i < aux.size(); i++) {
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }
    }

    /**
     * Método que muestra el menú desplegable en el action bar de la activity
     * @param menu menu desplegable con opciones
     * @return objeto de tipo boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_peliculas, menu);
        return true;
    }

    /**
     * Método encargado de mostrar una activity en función de la opción del menu elegido
     * @param item elemento del menú indicado por el usuario
     * @return objeto de tipo boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Depende de la opcion elegida nos lleva a una activity diferente
        if (id == R.id.opcionPeliculaFavorita) {
            Intent i = new Intent(this, PeliculasFavoritas.class);
            startActivity(i);
        } else if (id == R.id.opcionPeliculaPendiente) {
            Intent i = new Intent(this, PeliculasPendientes.class);
            startActivity(i);
        } else if (id == R.id.opcionPeliculaVista) {
            Intent i = new Intent(this, PeliculasVistas.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método que muestra la pantallla de buscar peliculas
     * @param view de tipo View usada para la representación en pantalla de los elementos pertenecientes a la activity
     */
    public void menuBuscarPelicula(View view) {
        Intent i = new Intent(this, BuscarPelicula.class);
        startActivity(i);
    }

    /**
     * Método que muestra la pantallla de mostrar pelicula en detalle
     */
    private void mostrarPelicula() {
        Intent i = new Intent(this, PeliculaDetallada.class);
        startActivity(i);
    }

}
