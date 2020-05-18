package com.recordwatch.recordwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolderDatos> implements View.OnClickListener{

    private Context mContext;
    private View.OnClickListener escuchador;//Este es el escuchador del onclick
    private ArrayList<Pelicula> miLista;

    public AdaptadorPeliculas(Context mContext , ArrayList<Pelicula> miLista) {
        this.mContext  = mContext;
        this.miLista = miLista;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peliculas,null,false);
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



        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.idTituloEpisodio);
            valoracion=(TextView) itemView.findViewById(R.id.idValoracionSerie);
            foto=(ImageView) itemView.findViewById(R.id.idFotoEpisodio);

        }

        public void asignarDatos(Pelicula pelicula) {
            //Se asigna los datos del objeto recibido a las variables
            String poster = pelicula.getRutaPoster();
            titulo.setText(pelicula.getTitulo());
            valoracion.setText("Valoración : "+pelicula.getValoracion());
            Glide.with(mContext).load(poster).placeholder(R.drawable.pelicula).into(foto);


        }
    }
}
