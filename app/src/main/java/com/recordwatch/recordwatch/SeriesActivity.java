package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.recordwatch.recordwatch.adaptadores.AdaptadorSeries;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Serie;

/**
 * Activity que muestra un listado de series populares además de darnos la opción de buscar una serie
 * nosotros mismos o ver un listado de nuestras series dependiendo de su estado
 */
public class SeriesActivity extends AppCompatActivity {

    ArrayList<Serie> miLista;
    RecyclerView miRecycler;
    AdaptadorSeries elAdaptador;
    TextView nombreSerie;
    TextView valoracionSerie;
    static ImageView fotoSerie;
    public static  int codigoSerieElegida;
    SwipeRefreshLayout refrescar;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        this.setTitle(R.string.seriesPopulares);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombreSerie = findViewById(R.id.idTituloEpisodio);
        valoracionSerie = findViewById(R.id.idValoracionSerie);
        fotoSerie = findViewById(R.id.idFotoEpisodio);
        refrescar = findViewById(R.id.refrescarSeriesPopulares);

        miLista = new ArrayList<Serie>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaSeriesPopulares);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorSeries(this,miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos el nombre del elemento seleccionado
                CharSequence texto = "Pulsado: " + miLista.get(miRecycler.getChildAdapterPosition(v)).getTitulo();
                codigoSerieElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getSerieId();
                Log.d("TAG","Codigo de serie elegida : "+codigoSerieElegida);
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

        ArrayList<Serie> aux  = new ArrayList<>();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            aux = cad.leerSeriesPopulares();
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for(int i=0;i<aux.size();i++){
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
        getMenuInflater().inflate(R.menu.menu_series, menu);
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
        if (id == R.id.opcionSerieSiguiendo) {
            Intent i = new Intent(this,SeriesSiguiendo.class);
            startActivity(i);
        }
        else if(id == R.id.opcionSeriePendiente){
            Intent i = new Intent(this,SeriesPendientes.class);
            startActivity(i);
        }
        else if(id == R.id.opcionSerieVista){
            Intent i = new Intent(this,SeriesVistas.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método que muestra la pantallla de buscar series
     * @param view de tipo View usada para la representación en pantalla de los elementos pertenecientes a la activity
     */
    public void menuBuscarSerie(View view){
        Intent i = new Intent(this,BuscarSerie.class);
        startActivity(i);
    }

    /**
     * Método que muestra la pantallla de mostrar serie en detalle
     */
    private void mostrarSerie() {
        Intent i = new Intent(this,SerieDetallada.class);
        startActivity(i);
    }
}
