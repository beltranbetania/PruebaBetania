package com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.momentumlab.marvelcomicvisor.pruebabetania.R;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ModeloComicSqlite;

import java.util.List;

/**
 * Adaptador de las listas de comics que se obtiene desde la bd interna, dado que la estructura es mas simple que las del api
 */

public class ComicSqliteAdapter extends RecyclerView.Adapter<ComicSqliteAdapter.ViewHolder> {

    private List<ModeloComicSqlite> items;
    private Context mContext;
    private ItemClickListener clickListener;
    public ComicSqliteAdapter(Context contexts, List<ModeloComicSqlite> myDataset) {
        this.mContext = contexts;
        this.items = myDataset;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ComicSqliteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_comic, parent, false);

        return new ComicSqliteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComicSqliteAdapter.ViewHolder holder, int position) {
        String urlimage="";
        try {
            urlimage   = items.get(position).getPath();
        }catch (Exception e){

        }


        Glide
                .with(mContext)
                .load(urlimage)
                .placeholder(R.drawable.sin_foto)
                .error(R.drawable.sin_foto)
                .crossFade()
                .into(holder.imagen);
        holder.nombre.setText(items.get(position).getTitulo());
        holder.precio.setText("$"+items.get(position).getPrecio());


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombre;
        public ImageView imagen;
        public  TextView precio;

        public ViewHolder(View v) {
            super(v);
            itemView.setOnClickListener(this);
            nombre = (TextView) v.findViewById(R.id.nombrePersonaje);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            precio=(TextView) v.findViewById(R.id.textPrecio);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClickRecycler(view, getAdapterPosition());
        }
    }
}
