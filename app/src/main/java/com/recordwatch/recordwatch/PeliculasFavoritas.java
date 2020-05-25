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

import com.recordwatch.recordwatch.componentes.ComponenteCAD;

import java.util.ArrayList;
import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;



public class PeliculasFavoritas extends AppCompatActivity {
    ArrayList<Pelicula> miLista;
    RecyclerView miRecycler;
    AdaptadorPeliculas elAdaptador;
    EditText tituloPelicula;
    TextView nombrePelicula;
    TextView valoracionPelicula;
    static ImageView fotoPelicula;
    SwipeRefreshLayout refrescar;


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


        miLista = new ArrayList<Pelicula>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaPeliculasFavoritas);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorPeliculas(this, miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos el nombre del elemento seleccionado
                CharSequence texto = "Pulsado: " + miLista.get(miRecycler.getChildAdapterPosition(v)).getTitulo();
                codigoPeliculaElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getPeliculaId();
                Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
                toast.show();
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
                },3000);
            }
        });

        ArrayList<Pelicula> aux  = new ArrayList<>();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            aux = cad.leerPeliculas("F");
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for(int i=0;i<aux.size();i++){
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }


    }

    private void mostrarPelicula() {
        Intent i = new Intent(this,PeliculaDetallada.class);
        startActivity(i);
    }



}
