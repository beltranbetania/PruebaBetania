package com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.momentumlab.marvelcomicvisor.pruebabetania.R;

import java.util.List;

/*
* ADAPTADOR PARA EL RECICLE VIEW QUE SE MUESTRA EN CADA FRAGMENTO DEL PAGER DE LISTAS DETALLE
* */
public class ListaInternaAdapter extends RecyclerView.Adapter<ListaInternaAdapter.ViewHolder> {

    private List<String> items;
    private ItemClickListener clickListener;

    public ListaInternaAdapter(List<String> myDataset) {
        this.items = myDataset;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listas_internas, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombre;

        public ViewHolder(View v) {
            super(v);
            itemView.setOnClickListener(this);
            nombre = (TextView) v.findViewById(R.id.nombreLI);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClickRecycler(view, getAdapterPosition());
        }
    }
}
