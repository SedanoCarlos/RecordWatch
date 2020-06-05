package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.recordwatch.recordwatch.adaptadores.AdaptadorPeliculas;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Pelicula;
import java.util.ArrayList;

import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;

/**
 * En esta activity muestra un listado de películas cuyo titulo tiene relación con el que el usuario introduzca
 */
public class BuscarPelicula extends AppCompatActivity {
    EditText tituloPelicula;
    TextView nombrePelicula;
    TextView valoracionPelicula;
    static ImageView fotoPelicula;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pelicula);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tituloPelicula = findViewById(R.id.etTituloPelicula);
        nombrePelicula = findViewById(R.id.idTituloEpisodio);
        valoracionPelicula = findViewById(R.id.idValoracionSerie);
        fotoPelicula = findViewById(R.id.idFotoEpisodio);

    }

    /**
     * Método que muestra una lista de elementos de tipo de película dentro dentro de una lista
     *
     * @param view de tipo View usada para la representación en pantalla de los elementos pertenecientes a la activity
     */
    public void buscarPelicula(View view) {
        ArrayList<Pelicula> aux  = new ArrayList<>();
        final ArrayList<Pelicula> miLista = new ArrayList<Pelicula>();
        final RecyclerView miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaPeliculas);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        final AdaptadorPeliculas elAdaptador = new AdaptadorPeliculas(this,miLista);
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

        String nPelicula = tituloPelicula.getText().toString();
        if (nPelicula.isEmpty()) {
            tituloPelicula.setError("Introduzca nombre de pelicula a mostrar");
            return;
        }
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            aux = cad.buscarPelicula(nPelicula);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for(int i=0;i<aux.size();i++){
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }

    }

    /**
     * Método que hace que se muestre la pantalla de Pelicula Detallada
     */
    private void mostrarPelicula() {
        Intent i = new Intent(this,PeliculaDetallada.class);
        startActivity(i);
    }



}
