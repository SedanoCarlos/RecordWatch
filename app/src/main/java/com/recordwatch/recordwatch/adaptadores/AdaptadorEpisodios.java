package com.recordwatch.recordwatch.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.R;
import com.recordwatch.recordwatch.pojos.Serie;


import java.util.ArrayList;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.numeroTemporadaElegida;

public class AdaptadorEpisodios extends RecyclerView.Adapter<AdaptadorEpisodios.ViewHolderDatos> implements View.OnClickListener {

    private Context mContext;
    private View.OnClickListener escuchador;//Este es el escuchador del onclick
    private ArrayList<Episodio> miLista;

    public AdaptadorEpisodios(Context mContext, ArrayList<Episodio> miLista) {
        this.mContext = mContext;
        this.miLista = miLista;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episodios, null, false);
        laVista.setOnClickListener(this); //Cada vez que se crea un elemento se invoca al setClickListener
        return new ViewHolderDatos(laVista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(miLista.get(position));
    }

    @Override
    public int getItemCount() {
        if (miLista != null) return miLista.size();
        return 0;
    }

    @Override
    public void onClick(View v) {
        if(escuchador!=null) escuchador.onClick(v);
    }

    //Se asigna el click de la vista a nuestro escuchador
    public void setOnClickListener(View.OnClickListener escucha) {
        this.escuchador = escucha;
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView foto;
        Button visto;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.idTituloEpisodio);
            foto = (ImageView) itemView.findViewById(R.id.idFotoEpisodio);
            visto = (Button) itemView.findViewById(R.id.idVisto);
            visto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(escuchador!=null){
                        int position = getAdapterPosition();
                        cambiarEstado(position);
                    }
                }
            });


        }

        public void asignarDatos(Episodio episodio) {
            //Se asigna los datos del objeto recibido a las variables
            String poster = episodio.getRutaPoster();
            nombre.setText("" + episodio.getNumeroEpisodio() + "." + episodio.getTituloEpisodio());
            Glide.with(mContext).load(poster).into(foto);
            Episodio aux = new Episodio();
            try {
                ComponenteCAD cad = new ComponenteCAD(mContext);
                aux = cad.leerEpisodio(codigoSerieElegida, numeroTemporadaElegida, getAdapterPosition());
            } catch (ExcepcionRecordWatch excepcionRecordWatch) {

            }
            if (aux != null) {
                visto.setBackgroundResource(R.drawable.ojotachado);
            } else {
                visto.setBackgroundResource(R.drawable.ojo);
            }

        }




        public void cambiarEstado(int position) {
            Episodio aux = new Episodio();
            ComponenteCAD cad = null;
            try {
                cad = new ComponenteCAD(mContext);
                if (cad.leerSerieBD(codigoSerieElegida) != null) {
                    aux = cad.leerEpisodio(codigoSerieElegida, numeroTemporadaElegida, position);
                    Episodio episodio = new Episodio();
                    if (aux == null) {
                        episodio.setSerieId(codigoSerieElegida);
                        episodio.setNumeroTemporada(numeroTemporadaElegida);
                        episodio.setNumeroEpisodio(position);
                        cad.insertarEpisodio(episodio);
                        Log.d("EPISODIO INSERTADO", "" + episodio);
                        visto.setBackgroundResource(R.drawable.ojotachado);
                    } else {
                        cad.eliminarEpisodio(codigoSerieElegida, numeroTemporadaElegida, position);
                        Log.d("EPISODIO ELIMINADO", "Serie:" + codigoSerieElegida + ",Temporada:" + numeroTemporadaElegida + ",Episodio:" + position);
                        visto.setBackgroundResource(R.drawable.ojo);
                    }

                    boolean episodiosVistos = true;
                    ComponenteCAD cad1 = new ComponenteCAD(mContext);
                    int numeroTemporadas  = (cad.leerTemporadasBD(codigoSerieElegida)).size();
                    for (int i=0;i<numeroTemporadas;i++){
                        int numeroEpisodiosWS = (cad1.leerTemporada(codigoSerieElegida,i)).getNumeroCapitulos();
                        int numeroEpisodiosBD = (cad.leerEpisodios(codigoSerieElegida,i)).size();
                        if(numeroEpisodiosWS != numeroEpisodiosBD){
                            episodiosVistos = false;
                            break;
                        }
                    }
                    if(episodiosVistos = true){
                        Serie serie = new Serie();
                        serie.setSerieId(codigoSerieElegida);
                        serie.setEstado("V");
                        cad.modificarSerie(codigoSerieElegida,serie);
                    }
                }else{
                    Toast.makeText(mContext,"Primero indica la serie como Pendiente o Siguiendo",Toast.LENGTH_LONG).show();
                }
                } catch(ExcepcionRecordWatch excepcionRecordWatch){
                    excepcionRecordWatch.printStackTrace();
                }


        }
    }
}
