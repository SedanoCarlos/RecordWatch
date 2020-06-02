package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Activity que nos permite elegir entre la opción manejo de películas o series
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Método que muestra el menú desplegable en el action bar de la activity
     * @param menu menu desplegable con opciones
     * @return objeto de tipo boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    /**
     * Método encargado de mostrar una activity en función de la opción del menu elegido
     * @param item elemento del menú indicado por el usuario
     * @return objeto de tipo boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Depende de la opcion elegida nos lleva a una activity diferente
        int id = item.getItemId();
        if (id == R.id.itemCopiaSeguridad) {
            Intent i = new Intent(this, CopiaSeguridad.class);
            startActivity(i);
        } else if (id == R.id.itemCambiarContraseña) {
            Intent i = new Intent(this, CambiarUsuario.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método que muestra la pantalla de peliculas populares
     * @param view de tipo View usada para la representación en pantalla de los elementos pertenecientes a la activity
     */
    public void botonPeliculas(View view) {
        Intent i = new Intent(this, PeliculasActivity.class);
        startActivity(i);
    }

    /**
     * Método que muestra la pantalla de series populares
     * @param view de tipo View usada para la representación en pantalla de los elementos pertenecientes a la activity
     */
    public void botonSeries(View view) {
        Intent i = new Intent(this, SeriesActivity.class);
        startActivity(i);

    }


}
