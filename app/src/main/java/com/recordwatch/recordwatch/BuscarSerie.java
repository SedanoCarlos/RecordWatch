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

import com.recordwatch.recordwatch.adaptadores.AdaptadorSeries;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.componentes.ComponenteWS;
import com.recordwatch.recordwatch.pojos.Serie;

import java.util.ArrayList;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;


public class BuscarSerie extends AppCompatActivity {
    ArrayList<Serie> miLista;
    RecyclerView miRecycler;
    AdaptadorSeries elAdaptador;
    EditText tituloSerie;
    TextView nombreSerie;
    TextView valoracionSerie;
    static ImageView fotoSerie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tituloSerie = findViewById(R.id.etTituloSerie);
        nombreSerie = findViewById(R.id.idTituloEpisodio);
        valoracionSerie = findViewById(R.id.idValoracionSerie);
        fotoSerie = findViewById(R.id.idFotoEpisodio);

    }

    public void buscarSerie(View view){
        ArrayList<Serie> aux  = new ArrayList<>();
        final ArrayList<Serie> miLista = new ArrayList<Serie>();//Lista de objetos
        //miLista=cargarDatos(miLista);//Cargamos los datos del array
        final RecyclerView miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaSeries);
        //Pasos importantes
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        final AdaptadorSeries elAdaptador = new AdaptadorSeries(this,miLista);
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


        String nSerie = tituloSerie.getText().toString();
        if (nSerie.isEmpty()) {
            tituloSerie.setError("Introduzca nombre de serie a mostrar");
            return;
        }
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            aux = cad.buscarSerie(nSerie);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        for(int i=0;i<aux.size();i++){
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }
    }

    private void mostrarSerie() {
        Intent i = new Intent(this,SerieDetallada.class);
        startActivity(i);
    }



}
