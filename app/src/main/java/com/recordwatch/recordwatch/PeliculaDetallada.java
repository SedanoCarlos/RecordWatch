package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Pelicula;
import com.recordwatch.recordwatch.utilidades.TTSManager;

import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;

/**
 * Activity que nos muestra la información de una película en detalle
 */
public class PeliculaDetallada extends AppCompatActivity {

    TextView titulo;
    TextView valoracion;
    TextView sinopsis;
    ImageView foto;
    Button peliculaRegistrada;
    Button leerTexto;
    private TTSManager lecturaVoz = null;
    private int hablando = 0;

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula_detallada);
        this.setTitle(R.string.peliculaDetallada);

        titulo = findViewById(R.id.tvTitulo);
        valoracion = findViewById(R.id.tvValoracion);
        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);
        peliculaRegistrada = findViewById(R.id.buttonPeliculaRegistrada);
        leerTexto = findViewById(R.id.buttonLeer);

        leerTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerSinopis();
            }
        });
        peliculaRegistrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(PeliculaDetallada.this, peliculaRegistrada);
                popup.getMenuInflater().inflate(R.menu.popup_peliculas, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cambiarEstado(item);
                        Toast.makeText(PeliculaDetallada.this, "Has pulsado : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();
            }
        });
        //Comprueba si la pelicula existe en la base de datos
        Pelicula pelicula = new Pelicula();
        try {
            ComponenteCAD cad = new ComponenteCAD(this);
            pelicula = cad.leerPeliculaBD(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        //Depende de si existe o no,muestra un estado diferente
        if (pelicula != null) {
            comprobarEstado();
        } else {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
        }


        pelicula = new Pelicula();
        try {
            ComponenteCAD cad2 = new ComponenteCAD(this);
            //Busca la información de la pelicula en la api de TheMovieDB y muestra su información en los campos
            pelicula = cad2.leerPeliculaWS(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        titulo.setText(pelicula.getTitulo());
        valoracion.setText(pelicula.getValoracion());
        sinopsis.setText(pelicula.getSinopsis());
        String poster = pelicula.getRutaPoster();
        if (poster.equals("null")) {
            Glide.with(PeliculaDetallada.this).load(poster).placeholder(R.drawable.pelicula).into(foto);
        } else {
            Glide.with(PeliculaDetallada.this).load(poster).placeholder(R.drawable.pelicula).into(foto);
        }

        comprobarSinopsis();
    }

    /**
     * Método que cambia el estado de la película según que opción del menu se marque
     * @param item elemento del menú seleccionado
     */
    public void cambiarEstado(MenuItem item) {
        Pelicula aux = new Pelicula();
        ComponenteCAD cad = null;
        try {
            //Comprueba si la pelicula existe en la base de datos
            cad = new ComponenteCAD(this);
            aux = cad.leerPeliculaBD(codigoPeliculaElegida);
            Pelicula pelicula = new Pelicula();
            if (aux == null) {
                /**Si no existe,inserta la película en la base de datos con el estado correspondiente al que se
                haya seleccionado en el menú y cambia el icono que se muestra de la película**/
                pelicula.setPeliculaId(codigoPeliculaElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    pelicula.setEstado("P");
                    cad.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como favorita")) {
                    pelicula.setEstado("F");
                    cad.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    pelicula.setEstado("V");
                    cad.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Sin Clasificar")) {
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            } else {
                /**Si existe,modifica la película en la base de datos con el estado correspondiente al que se
                 haya seleccionado en el menú y cambia el icono que se muestra de la película**/
                pelicula.setPeliculaId(codigoPeliculaElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    pelicula.setEstado("P");
                    cad.modificarPelicula(codigoPeliculaElegida, pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como favorita")) {
                    pelicula.setEstado("F");
                    cad.modificarPelicula(codigoPeliculaElegida, pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    pelicula.setEstado("V");
                    cad.modificarPelicula(codigoPeliculaElegida, pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Sin Clasificar")) {
                    cad.eliminarPelicula(codigoPeliculaElegida);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
    }

    /**
     * Método que comprueba el estado de la películas,para saber que icono mostrar
     */
    public void comprobarEstado() {
        Pelicula pelicula = new Pelicula();
        try {
            //Comprueba si la pelicula existe en la base de datos
            ComponenteCAD cad = new ComponenteCAD(this);
            pelicula = cad.leerPeliculaBD(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }

        //Si la pelicula existe en la base de datos,muestra su estado en función del que tenga almacenado en la base de datos
        if (pelicula.getEstado().equals("P")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
        } else if (pelicula.getEstado().equals("F")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else if (pelicula.getEstado().equals("V")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
        }
    }

    /**
     * Método que comprueba si hay sinopsis de la pelicula
     */
    private void comprobarSinopsis() {
        //Si existe sinopsis para la pelicula, el botón de lectura en voz se mostrará,de lo contrario se ocultará
        if (!sinopsis.getText().toString().isEmpty()) {
            lecturaVoz = new TTSManager();
            lecturaVoz.init(this);
            leerTexto.setVisibility(View.VISIBLE);
        } else {
            leerTexto.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Método que comprueba si la lectura en voz todavía se esta reproduciendo
     */
    public void leerSinopis() {
        switch (hablando) {
            case 0:
                hablando = 1;
                lecturaVoz.initQueue(sinopsis.getText().toString());
                break;
            case 1:
                hablando = 0;
                lecturaVoz.stop();
                break;
        }
    }

    /**
     * Método que corta la reproducción de la lectura en voz en caso de que se salga de la actividad
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lecturaVoz != null) {
            lecturaVoz.shutDown();
        }
    }
}
