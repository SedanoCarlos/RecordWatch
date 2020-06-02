package com.recordwatch.recordwatch.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.recordwatch.recordwatch.R;
import com.recordwatch.recordwatch.pojos.Temporada;

import java.util.ArrayList;

/**
 * Clase que utilizamos para crear y mostrar una lista de objetos de tipo Temporada
 */
public class AdaptadorTemporadas extends RecyclerView.Adapter<AdaptadorTemporadas.ViewHolderDatos> implements View.OnClickListener{

    private Context mContext;
    private View.OnClickListener escuchador;//Este es el escuchador del onclick
    private ArrayList<Temporada> miLista;

    /**
     * Método que crea una instancia de un objeto de tipo AdaptadorTemporadas
     * @param mContext indica la actividad desde donde es llamada la clase
     * @param miLista que pasa un listado de objetos de tipo temporada
     */
    public AdaptadorTemporadas(Context mContext , ArrayList<Temporada> miLista) {
        this.mContext = mContext;
        this.miLista = miLista;
    }

    /**
     * Crea la vista de la lista de temporadas
     * @param parent indica la vista donde se reflejara la lista
     * @param viewType indica el tipo de la vista
     * @return objeto de tipo vista
     */
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temporadas,null,false);
        laVista.setOnClickListener(this); //Cada vez que se crea un elemento se invoca al setClickListener
        return new ViewHolderDatos(laVista);
    }

    /**
     * Guarda los datos asignados en la lista en un holder
     * @param holder objeto que guarda el contenido de la vista
     * @param position cada una de las posiciones de la lista
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(miLista.get(position));
    }

    /**
     * Comprueba que la lista tiene elementos y si es así indica cuantos
     * @return numero de elementos en la lista
     */
    @Override
    public int getItemCount() {
        if(miLista!=null) return miLista.size();
        return 0;
    }

    /**
     * Se asigna el click de la vista a nuestro escuchador
     * @param escucha indica nuestro escuchador para la vista
     */
    public void setOnClickListener(View.OnClickListener escucha){
        this.escuchador=escucha;
    }

    /**
     * Escucha para cuando se clica en un elemento de la lista
     * @param v elemento de la lista
     */
    @Override
    public void onClick(View v) {
        if(escuchador!=null) escuchador.onClick(v);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView numeroCapitulos;
        ImageView foto;


        /**
         * Declaración de cada uno de los subelementos de cada elemento de la lista
         * @param itemView cada elemento de la vista
         */
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre=(TextView) itemView.findViewById(R.id.idNombre);
            numeroCapitulos=(TextView) itemView.findViewById(R.id.idNumeroCapitulos);
            foto=(ImageView) itemView.findViewById(R.id.idFotoEpisodio);

        }
        /**
         * Pasa cada dato de la serie a un elemento de la lista
         * @param temporada elemento pasado a la lista
         */
        public void asignarDatos(Temporada temporada) {
            //Se asigna los datos del objeto recibido a las variables
            String poster = temporada.getRutaPoster();
            nombre.setText(temporada.getTituloTemporada());
            numeroCapitulos.setText("NºCapítulos: "+temporada.getNumeroCapitulos()+"");
            Glide.with(mContext).load(poster).into(foto);


        }
    }
}
