package com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.momentumlab.marvelcomicvisor.pruebabetania.R;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Comic;

import java.util.List;


/*
* Adaptador de la lista de comics del que se obtienen con los modelos de retofit
* */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    private List<Comic> items;
    private Context mContext;
    private ItemClickListener clickListener;

    public ComicAdapter(Context contexts, List<Comic> myDataset) {
        this.mContext = contexts;
        this.items = myDataset;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_comic, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String urlimage = "";
        try {
            urlimage = items.get(position).getImages().get(0).getPath();
            urlimage = urlimage + "." + items.get(position).getImages().get(0).getExtension();
            Log.d("SUCCES", urlimage);
        } catch (Exception e) {

        }


        Glide
                .with(mContext)
                .load(urlimage)
                .placeholder(R.drawable.sin_foto)
                .error(R.drawable.sin_foto)
                .crossFade()
                .into(holder.imagen);
        holder.nombre.setText(items.get(position).getTitle());

        if (items.get(position).getPrices().get(0).getPrice()!=0){

            holder.precio.setText("$" + items.get(position).getPrices().get(0).getPrice().toString());
        }else {

            holder.precio.setText("Agotado");
        }



    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombre;
        public ImageView imagen;
        public TextView precio;

        public ViewHolder(View v) {
            super(v);
            itemView.setOnClickListener(this);
            nombre = (TextView) v.findViewById(R.id.nombrePersonaje);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            precio = (TextView) v.findViewById(R.id.textPrecio);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClickRecycler(view, getAdapterPosition());
        }
    }
}
