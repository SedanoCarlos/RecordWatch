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
import com.recordwatch.recordwatch.componentes.ComponenteWS;
import java.util.ArrayList;
import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;



public class BuscarPelicula extends AppCompatActivity {
    EditText tituloPelicula;
    TextView nombrePelicula;
    TextView valoracionPelicula;
    static ImageView fotoPelicula;


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

    public void buscarPelicula(View view) {
        ArrayList<Pelicula> aux  = new ArrayList<>();
        final ArrayList<Pelicula> miLista = new ArrayList<Pelicula>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        final RecyclerView miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaPeliculas);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        final AdaptadorPeliculas elAdaptador = new AdaptadorPeliculas(this,miLista);
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

        String nPelicula = tituloPelicula.getText().toString();
        if (nPelicula.isEmpty()) {
            tituloPelicula.setError("Introduzca nombre de pelicula a mostrar");
            return;
        }
        try {
            ComponenteWS ws = new ComponenteWS();
            aux = ws.buscarPelicula(nPelicula);
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
