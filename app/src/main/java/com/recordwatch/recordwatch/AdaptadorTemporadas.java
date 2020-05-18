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

public class AdaptadorTemporadas extends RecyclerView.Adapter<AdaptadorTemporadas.ViewHolderDatos> implements View.OnClickListener{

    private Context mContext;
    private View.OnClickListener escuchador;//Este es el escuchador del onclick
    private ArrayList<Temporada> miLista;

    public AdaptadorTemporadas(Context mContext , ArrayList<Temporada> miLista) {
        this.mContext = mContext;
        this.miLista = miLista;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temporadas,null,false);
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

        TextView nombre;
        TextView numeroCapitulos;
        ImageView foto;



        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre=(TextView) itemView.findViewById(R.id.idNombre);
            numeroCapitulos=(TextView) itemView.findViewById(R.id.idNumeroCapitulos);
            foto=(ImageView) itemView.findViewById(R.id.idFotoEpisodio);

        }

        public void asignarDatos(Temporada temporada) {
            //Se asigna los datos del objeto recibido a las variables
            String poster = temporada.getRutaPoster();
            nombre.setText(temporada.getTituloTemporada());
            numeroCapitulos.setText("NºCapítulos: "+temporada.getNumeroCapitulos()+"");
            Glide.with(mContext).load(poster).into(foto);


        }
    }
}
