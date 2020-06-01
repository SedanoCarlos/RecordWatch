package com.recordwatch.recordwatch.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.R;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Serie;

import java.util.ArrayList;

public class AdaptadorSeries extends RecyclerView.Adapter<AdaptadorSeries.ViewHolderDatos> implements View.OnClickListener{

    private Context mContext;
    private View.OnClickListener escuchador;//Este es el escuchador del onclick
    private ArrayList<Serie> miLista;

    public AdaptadorSeries(Context mContext , ArrayList<Serie> miLista) {
        this.mContext = mContext;
        this.miLista = miLista;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_series,null,false);
        laVista.setOnClickListener(this); //Cada vez que se crea un elemento se invoca al setClickListener
        return new ViewHolderDatos(laVista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(miLista.get(position));
    }

    @Override
    public int getItemCount() {
        if(miLista!=null) return miLista.size();
        return 0;
    }

    //Se asigna el click de la vista a nuestro escuchador
    public void setOnClickListener(View.OnClickListener escucha){
        this.escuchador=escucha;
    }

    //Este es el escuchador de la lista
    @Override
    public void onClick(View v) {
        if(escuchador!=null) escuchador.onClick(v);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView valoracion;
        ImageView foto;
        Button estado;



        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.idTituloEpisodio);
            valoracion=(TextView) itemView.findViewById(R.id.idValoracionSerie);
            foto=(ImageView) itemView.findViewById(R.id.idFotoEpisodio);
            estado = (Button) itemView.findViewById(R.id.buttonEstadoSerie);

        }

        public void asignarDatos(Serie serie) {
            //Se asigna los datos del objeto recibido a las variables
            estado.setVisibility(View.INVISIBLE);
            if ((mContext.getClass().getSimpleName().equals("SeriesActivity")) ||
                    (mContext.getClass().getSimpleName().equals("BuscarSerie"))) {
                try {
                    Serie aux = new Serie();
                    ComponenteCAD cad = new ComponenteCAD(mContext);
                    aux = cad.leerSerieBD(serie.getSerieId());
                    if (aux != null) {
                        estado.setVisibility(View.VISIBLE);
                        if (aux.getEstado().equals("S")) {
                            estado.setBackgroundResource(R.drawable.ojo);
                        } else if (aux.getEstado().equals("P")) {
                            estado.setBackgroundResource(R.drawable.pendiente);
                        } else if (aux.getEstado().equals("V")) {
                            estado.setBackgroundResource(R.drawable.ojotachado);
                        }
                    } else {
                        estado.setVisibility(View.INVISIBLE);
                    }
                } catch (ExcepcionRecordWatch excepcionRecordWatch) {
                    excepcionRecordWatch.printStackTrace();
                }
            }
            String poster = serie.getRutaPoster();
            titulo.setText(serie.getTitulo());
            valoracion.setText("Valoración : "+serie.getValoracion());
            Glide.with(mContext).load(poster).placeholder(R.drawable.series).into(foto);


        }
    }
}
