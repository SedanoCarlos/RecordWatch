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
import com.recordwatch.recordwatch.componentes.ComponenteBD;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.componentes.ComponenteWS;
import com.recordwatch.recordwatch.pojos.Episodio;
import com.recordwatch.recordwatch.ExcepcionRecordWatch;
import com.recordwatch.recordwatch.R;
import com.recordwatch.recordwatch.pojos.Serie;

import java.util.ArrayList;

import static com.recordwatch.recordwatch.SeriesActivity.codigoSerieElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.numeroTemporadaElegida;
import static com.recordwatch.recordwatch.TemporadasActivity.primeraTemporada;

/**
 * Clase que utilizamos para crear y mostrar una lista de objetos de tipo Episodio
 */
public class AdaptadorEpisodios extends RecyclerView.Adapter<AdaptadorEpisodios.ViewHolderDatos> implements View.OnClickListener {

    private Context mContext;
    private View.OnClickListener escuchador;//Este es el escuchador del onclick
    private ArrayList<Episodio> miLista;

    /**
     * Método que crea una instancia de un objeto de tipo AdaptadorEpisodios
     *
     * @param mContext indica la actividad desde donde es llamada la clase
     * @param miLista  que pasa un listado de objetos de tipo episodio
     */
    public AdaptadorEpisodios(Context mContext, ArrayList<Episodio> miLista) {
        this.mContext = mContext;
        this.miLista = miLista;
    }

    /**
     * Crea la vista de la lista de episodios
     *
     * @param parent   indica la vista donde se reflejara la lista
     * @param viewType indica el tipo de la vista
     * @return objeto de tipo vista
     */
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episodios, null, false);
        laVista.setOnClickListener(this); //Cada vez que se crea un elemento se invoca al setClickListener
        return new ViewHolderDatos(laVista);
    }

    /**
     * Guarda los datos asignados en la lista en un holder
     *
     * @param holder   objeto que guarda el contenido de la vista
     * @param position cada una de las posiciones de la lista
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(miLista.get(position));
    }

    /**
     * Comprueba que la lista tiene elementos y si es así indica cuantos
     *
     * @return numero de elementos en la lista
     */
    @Override
    public int getItemCount() {
        if (miLista != null) return miLista.size();
        return 0;
    }


    /**
     * Se asigna el click de la vista a nuestro escuchador
     *
     * @param escucha indica nuestro escuchador para la vista
     */
    public void setOnClickListener(View.OnClickListener escucha) {
        this.escuchador = escucha;
    }

    /**
     * Escucha para cuando se clica en un elemento de la lista
     *
     * @param v elemento de la lista
     */
    @Override
    public void onClick(View v) {
        if (escuchador != null) escuchador.onClick(v);
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView foto;
        Button visto;


        /**
         * Declaración de cada uno de los subelementos de cada elemento de la lista
         *
         * @param itemView cada elemento de la vista
         */
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.idTituloEpisodio);
            foto = (ImageView) itemView.findViewById(R.id.idFotoEpisodio);
            visto = (Button) itemView.findViewById(R.id.idVisto);
            visto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (escuchador != null) {
                        int position = getAdapterPosition();
                        cambiarEstado(position);
                    }
                }
            });


        }

        /**
         * Pasa cada dato de la serie a un elemento de la lista
         *
         * @param episodio elemento pasado a la lista
         */
        public void asignarDatos(Episodio episodio) {
            //Se asigna los datos del objeto recibido a las variables
            String poster = episodio.getRutaPoster();
            nombre.setText("" + episodio.getNumeroEpisodio() + "." + episodio.getTituloEpisodio());
            if(poster.equals("")){
                Glide.with(mContext).load(R.drawable.series).placeholder(R.drawable.series).into(foto);
            }else{
                Glide.with(mContext).load(poster).placeholder(R.drawable.series).into(foto);
            }
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

        /**
         * Al clicar en un episodio se cambia su estado
         *
         * @param position elemento pasado a la lista
         */
        public void cambiarEstado(int position) {
            Episodio aux = new Episodio();
            ComponenteCAD cad;
            ComponenteBD bd = null;
            try {
                cad = new ComponenteCAD(mContext);
                bd = new ComponenteBD(mContext);
                if (cad.leerSerieBD(codigoSerieElegida) != null) {
                    aux = cad.leerEpisodio(codigoSerieElegida, numeroTemporadaElegida, position);
                    Episodio episodio = new Episodio();
                    //Comprobar si el episodio existe en el sistema
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
                    ComponenteWS ws = new ComponenteWS();
                    int numeroTemporadas = (ws.leerTemporadas(codigoSerieElegida)).size();
                    Log.d("Prueba 1",""+numeroTemporadas);
                    if(primeraTemporada==0){
                    }else{
                        numeroTemporadas++;
                    }
                    for (int i = ws.leerTemporadas(codigoSerieElegida).get(0).getNumeroTemporada(); i < numeroTemporadas; i++) {
                        Log.d("TAG",""+codigoSerieElegida+" , "+i);
                        int numeroEpisodiosWS = (ws.leerTemporada(codigoSerieElegida, i)).getNumeroCapitulos();
                        int numeroEpisodiosBD = 0;
                        if(bd.leerEpisodios(codigoSerieElegida,i)!=null) {
                            numeroEpisodiosBD = (bd.leerEpisodios(codigoSerieElegida, i)).size();
                        }
                        Log.d("Prueba 2",""+numeroEpisodiosBD+" == "+numeroEpisodiosWS);
                        if (numeroEpisodiosWS != numeroEpisodiosBD) {
                            episodiosVistos = false;
                            break;
                        }
                    }
                    if (episodiosVistos) {
                        Serie serie = new Serie();
                        serie.setSerieId(codigoSerieElegida);
                        serie.setEstado("V");
                        bd.modificarSerie(codigoSerieElegida, serie);
                        cad.modificarSerie(codigoSerieElegida, serie);
                    }
                } else {
                    Toast.makeText(mContext, "Primero indica la serie como Pendiente o Siguiendo", Toast.LENGTH_LONG).show();
                }
            } catch (ExcepcionRecordWatch excepcionRecordWatch) {
                excepcionRecordWatch.printStackTrace();
            }
        }
    }
}
