package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.recordwatch.recordwatch.componentes.ComponenteWS;

import java.util.ArrayList;

public class PeliculasActivity extends AppCompatActivity {

    ArrayList<Pelicula> miLista;
    RecyclerView miRecycler;
    AdaptadorPeliculas elAdaptador;
    TextView nombrePelicula;
    TextView valoracionPelicula;
    static ImageView fotoPelicula;
    static public  int codigoPeliculaElegida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);
        nombrePelicula = findViewById(R.id.idTituloEpisodio);
        valoracionPelicula = findViewById(R.id.idValoracionSerie);
        fotoPelicula = findViewById(R.id.idFotoEpisodio);

        miLista = new ArrayList<Pelicula>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaPeliculasPopulares);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorPeliculas(this,miLista);
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

        ArrayList<Pelicula> aux  = new ArrayList<>();
        try {
            ComponenteWS ws = new ComponenteWS();
            aux = ws.leerPeliculasPopulares();
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
        getMenuInflater().inflate(R.menu.menu_peliculas, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.opcionPeliculaFavorita) {
            Intent i = new Intent(this,PeliculasFavoritas.class);
            startActivity(i);
        }
        else if(id == R.id.opcionPeliculaPendiente){
            Intent i = new Intent(this,PeliculasPendientes.class);
            startActivity(i);
        }
        else if(id == R.id.opcionPeliculaVista){
            Intent i = new Intent(this,PeliculasVistas.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuBuscarPelicula(View view){
        Intent i = new Intent(this,BuscarPelicula.class);
        startActivity(i);
    }

    private void mostrarPelicula() {
        Intent i = new Intent(this,PeliculaDetallada.class);
        startActivity(i);
    }

}
