package com.recordwatch.recordwatch;

import android.content.ContentValues;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.componentes.ComponenteBD;
import com.recordwatch.recordwatch.componentes.ComponenteWS;

import static com.recordwatch.recordwatch.PeliculasActivity.codigoPeliculaElegida;
import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SerieDetallada extends AppCompatActivity {

    TextView titulo;
    TextView valoracion;
    TextView sinopsis;
    ImageView foto;
    Button serieRegistrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detallada);
        titulo = findViewById(R.id.tvTitulo);
        valoracion = findViewById(R.id.tvValoracion);
        sinopsis = findViewById(R.id.tvSinopsis);
        sinopsis.setMovementMethod(new ScrollingMovementMethod());
        foto = findViewById(R.id.ivPoster);

        serieRegistrada = findViewById(R.id.buttonSerieRegistrada);

        serieRegistrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(SerieDetallada.this, serieRegistrada);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_series, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cambiarEstado(item);
                        Toast.makeText(SerieDetallada.this,"Has pulsado : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        Serie serie = new Serie();
        try {
            ComponenteBD bd = new ComponenteBD(this);
            serie = bd.leerSerie(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if (serie != null) {
            comprobarEstado();
        } else {
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
        }

        ComponenteWS ws = null;
        serie = new Serie();
        try {
            ws = new ComponenteWS();
            serie = ws.leerSerie(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }

        titulo.setText(serie.getTitulo());
        valoracion.setText(serie.getValoracion());
        sinopsis.setText(serie.getSinopsis());
        String poster = serie.getRutaPoster();
        if (poster.equals("null")) {
            Glide.with(SerieDetallada.this).load(poster).placeholder(R.drawable.series).into(foto);
        } else {
            Glide.with(SerieDetallada.this).load(poster).placeholder(R.drawable.series).into(foto);
        }
    }

    public void cambiarEstado(MenuItem item){
        Serie aux = new Serie();
        ComponenteBD bd = null;
        try {
            bd = new ComponenteBD(this);
            aux = bd.leerSerie(codigoSerieElegida);
            Serie serie = new Serie();
            if (aux == null) {
                serie.setSerieId(codigoSerieElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    serie.setEstado("P");
                    bd.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como siguiendo")) {
                    serie.setEstado("S");
                    bd.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    serie.setEstado("V");
                    bd.insertarSerie(serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Nada")) {
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }else{
                serie.setSerieId(codigoSerieElegida);
                if (item.getTitle().equals("Marcar como pendiente")) {
                    serie.setEstado("P");
                    bd.modificarSerie(codigoSerieElegida,serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
                } else if (item.getTitle().equals("Marcar como siguiendo")) {
                    serie.setEstado("S");
                    bd.modificarSerie(codigoSerieElegida,serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
                } else if (item.getTitle().equals("Marcar como vista")) {
                    serie.setEstado("V");
                    bd.modificarSerie(codigoSerieElegida,serie);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
                } else if (item.getTitle().equals("Nada")) {
                    bd.eliminarSerie(codigoSerieElegida);
                    serieRegistrada.setBackground(getResources().getDrawable(R.drawable.nada));
                }
            }
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }

    }

    public void comprobarEstado(){
        Serie serie = new Serie();
        try {
            ComponenteBD bd = new ComponenteBD(this);
            serie = bd.leerSerie(codigoSerieElegida);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {

        }
        if(serie.getEstado().equals("P")){
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.pendiente));
        }else if(serie.getEstado().equals("S")){
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.ojo));
        }else if(serie.getEstado().equals("V")){
            serieRegistrada.setBackground(getResources().getDrawable(R.drawable.vista));
        }
    }

    public void mostrarTemporadas(View view) {
        Intent i = new Intent(this,TemporadasActivity.class);
        startActivity(i);
    }
}
