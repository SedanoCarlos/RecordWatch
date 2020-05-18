package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.componentes.ComponenteBD;
import com.recordwatch.recordwatch.componentes.ComponenteWS;

import org.json.JSONException;
import org.json.JSONObject;

import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;


public class PeliculaDetallada extends AppCompatActivity {

    TextView titulo;
    TextView valoracion;
    TextView sinopsis;
    ImageView foto;
    Button peliculaRegistrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula_detallada);
        titulo = findViewById(R.id.tvTitulo);
        valoracion = findViewById(R.id.tvValoracion);
        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);
        peliculaRegistrada = findViewById(R.id.buttonPeliculaRegistrada);

        peliculaRegistrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(PeliculaDetallada.this, peliculaRegistrada);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_peliculas, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cambiarEstado(item);
                        Toast.makeText(PeliculaDetallada.this, "Has pulsado : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        Pelicula pelicula = new Pelicula();
        try {
            ComponenteBD bd = new ComponenteBD(this);
            pelicula = bd.leerPelicula(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if (pelicula != null) {
            comprobarEstado();
        } else {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
        }

        ComponenteWS ws = null;
        pelicula = new Pelicula();
        try {
            ws = new ComponenteWS();
            pelicula = ws.leerPelicula(codigoPeliculaElegida);
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
    }


    public void cambiarEstado(MenuItem item) {
        Pelicula aux = new Pelicula();
        ComponenteBD bd = null;
        try {
            bd = new ComponenteBD(this);
            aux = bd.leerPelicula(codigoPeliculaElegida);
            Pelicula pelicula = new Pelicula();
            if (aux == null) {
                pelicula.setPeliculaId(codigoPeliculaElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    pelicula.setEstado("P");
                    bd.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como favorita")) {
                    pelicula.setEstado("F");
                    bd.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    pelicula.setEstado("V");
                    bd.insertarPelicula(pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Nada")) {
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }else{
                pelicula.setPeliculaId(codigoPeliculaElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    pelicula.setEstado("P");
                    bd.modificarPelicula(codigoPeliculaElegida,pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como favorita")) {
                    pelicula.setEstado("F");
                    bd.modificarPelicula(codigoPeliculaElegida,pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    pelicula.setEstado("V");
                    bd.modificarPelicula(codigoPeliculaElegida,pelicula);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Nada")) {
                    bd.eliminarPelicula(codigoPeliculaElegida);
                    peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }

    }

    public void comprobarEstado() {
        Pelicula pelicula = new Pelicula();
        try {
            ComponenteBD bd = new ComponenteBD(this);
            pelicula = bd.leerPelicula(codigoPeliculaElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if (pelicula.getEstado().equals("P")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
        } else if (pelicula.getEstado().equals("F")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else if (pelicula.getEstado().equals("V")) {
            peliculaRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
        }


    }
}
