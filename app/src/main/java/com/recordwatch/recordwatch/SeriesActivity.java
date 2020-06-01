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
import android.widget.Toast;

import com.recordwatch.recordwatch.adaptadores.AdaptadorSeries;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Serie;

import java.util.ArrayList;

public class SeriesActivity extends AppCompatActivity {

    ArrayList<Serie> miLista;
    RecyclerView miRecycler;
    AdaptadorSeries elAdaptador;
    TextView nombreSerie;
    TextView valoracionSerie;
    static ImageView fotoSerie;
    public static  int codigoSerieElegida;
    SwipeRefreshLayout refrescar;



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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_series, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    public void menuBuscarSerie(View view){
        Intent i = new Intent(this,BuscarSerie.class);
        startActivity(i);
    }


    private void mostrarSerie() {
        Intent i = new Intent(this,SerieDetallada.class);
        startActivity(i);
    }
}
