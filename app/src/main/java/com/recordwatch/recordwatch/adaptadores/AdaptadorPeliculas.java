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
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.pojos.Pelicula;
import com.recordwatch.recordwatch.R;
import java.util.ArrayList;

/**
 * Clase que utilizamos para crear y mostrar una lista de objetos de tipo Peliculas
 */
public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolderDatos> implements View.OnClickListener {

    private Context mContext;
    private View.OnClickListener escuchador;//Este es el escuchador del onclick
    private ArrayList<Pelicula> miLista;

    /**
     * Método que crea una instancia de un objeto de tipo AdaptadorPeliculas
     * @param mContext indica la actividad desde donde es llamada la clase
     * @param miLista que pasa un listado de objetos de tipo película
     */
    public AdaptadorPeliculas(Context mContext, ArrayList<Pelicula> miLista) {
        this.mContext = mContext;
        this.miLista = miLista;
    }

    /**
     * Crea la vista de la lista de películas
     * @param parent indica la vista donde se reflejara la lista
     * @param viewType indica el tipo de la vista
     * @return objeto de tipo vista
     */
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peliculas, null, false);
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
        if (miLista != null) return miLista.size();
        return 0;
    }

    /**
     * Se asigna el click de la vista a nuestro escuchador
     * @param escucha indica nuestro escuchador para la vista
     */
    public void setOnClickListener(View.OnClickListener escucha) {
        this.escuchador = escucha;
    }

    /**
     * Escucha para cuando se clica en un elemento de la lista
     * @param v elemento de la lista
     */
    @Override
    public void onClick(View v) {
        if (escuchador != null) escuchador.onClick(v);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView valoracion;
        ImageView foto;
        Button estado;

        /**
         * Declaración de cada uno de los subelementos de cada elemento de la lista
         * @param itemView cada elemento de la vista
         */
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.idTituloEpisodio);
            valoracion = (TextView) itemView.findViewById(R.id.idValoracionSerie);
            foto = (ImageView) itemView.findViewById(R.id.idFotoEpisodio);
            estado = (Button) itemView.findViewById(R.id.buttonEstadoPelicula);

        }

        /**
         * Pasa cada dato de la película a un elemento de la lista
         * @param pelicula elemento pasado a la lista
         */
        public void asignarDatos(Pelicula pelicula) {
            //Se asigna los datos del objeto recibido a las variables
            estado.setVisibility(View.INVISIBLE);
            if ((mContext.getClass().getSimpleName().equals("PeliculasActivity")) ||
                    (mContext.getClass().getSimpleName().equals("BuscarPelicula"))) {
                try {
                    Pelicula aux = new Pelicula();
                    ComponenteCAD cad = new ComponenteCAD(mContext);
                    aux = cad.leerPeliculaBD(pelicula.getPeliculaId());
                    //Comprueba si la película existe
                    if (aux != null) {
                        //Comprueba el estado de la película
                        estado.setVisibility(View.VISIBLE);
                        if (aux.getEstado().equals("F")) {
                            estado.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
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
            //Asigna los valores a cada elemento de la lista
            String poster = pelicula.getRutaPoster();
            titulo.setText(pelicula.getTitulo());
            valoracion.setText("Valoración : " + pelicula.getValoracion());
            Glide.with(mContext).load(poster).placeholder(R.drawable.pelicula).into(foto);
        }
    }
}
