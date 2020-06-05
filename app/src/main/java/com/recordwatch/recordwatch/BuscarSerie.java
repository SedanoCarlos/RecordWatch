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

import com.recordwatch.recordwatch.adaptadores.AdaptadorSeries;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Serie;

import java.util.ArrayList;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;

/**
 * En esta activity muestra un listado de series cuyo titulo tiene relación con el que el usuario introduzca
 */
public class BuscarSerie extends AppCompatActivity {
    EditText tituloSerie;
    TextView nombreSerie;
    TextView valoracionSerie;
    static ImageView fotoSerie;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
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

    /**
     * Método que muestra una lista de elementos de tipo de serie dentro dentro de una lista
     *
     * @param view de tipo View usada para la representación en pantalla de los elementos pertenecientes a la activity
     */
    public void buscarSerie(View view) {
        ArrayList<Serie> aux = new ArrayList<>();
        final ArrayList<Serie> miLista = new ArrayList<Serie>();
        final RecyclerView miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVistaSeries);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        final AdaptadorSeries elAdaptador = new AdaptadorSeries(this, miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoSerieElegida = miLista.get(miRecycler.getChildAdapterPosition(v)).getSerieId();
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
        for (int i = 0; i < aux.size(); i++) {
            miLista.add(aux.get(i));
            elAdaptador.notifyItemChanged(i);
        }
    }

    /**
     * Método que hace que se muestre la pantalla de Serie Detallada
     */
    private void mostrarSerie() {
        Intent i = new Intent(this, SerieDetallada.class);
        startActivity(i);
    }


}
