package com.momentumlab.marvelcomicvisor.pruebabetania.Fragmentos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.momentumlab.marvelcomicvisor.pruebabetania.Const;
import com.momentumlab.marvelcomicvisor.pruebabetania.R;
import com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores.ItemClickListener;
import com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores.ListaInternaAdapter;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ComicsSQLiteHelper;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ModeloComicSqlite;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.QueryComic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siragon on 25/07/2017.
 */

public class PageHolderFragmentSQLite extends Fragment implements ItemClickListener {

    private RecyclerView mRecyclerView;
    private ListaInternaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String suiche;
    private List<String> listaString = new ArrayList<String>();
    private ModeloComicSqlite comic; // PERSONAJE SOBRE EL CUAL SE MUESTRAN LAS LISTAS DETALLE

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }

    public PageHolderFragmentSQLite() {

    }

    public static PageHolderFragmentSQLite newInstance(int param1, String param2, Serializable h) {
        PageHolderFragmentSQLite fragment = new PageHolderFragmentSQLite();
        Bundle args = new Bundle();
        args.putString(Const.FRAGMENT_KEY, param2); // SE OBTIENE LA CLAVE DE LA LISTA QUE SE DEBE MOSTRAR
        args.putSerializable(Const.COMIC_KEY, h); // SE OBTIENE EL PERSONAJE SOBRE EL CUAL SE DEBE MOSTRAR LA DATA
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comic = (ModeloComicSqlite) getArguments().getSerializable(Const.COMIC_KEY);
            suiche = getArguments().getString(Const.FRAGMENT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ListaInternaAdapter(listaString);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
        cargar();
        mAdapter.notifyDataSetChanged();
    }


    public void cargaCreadores() {
        String q = QueryComic.getCreadoresbyComic(comic.getId());
        consulta(q);
    }


    public void cargaSeries() {
        String q = QueryComic.getseriesbycomic(comic.getId());
        consulta(q);
    }

    public void cargaPersonajes() {
        String q = QueryComic.getpersonajesbycomic(comic.getId());

        consulta(q);
    }


    public void consulta(String query) {

        ComicsSQLiteHelper usdbh =
                new ComicsSQLiteHelper(getActivity(), Const.NOMBRE_BD_ACTUAL, null, Const.VERSION_BD_ACTUAL);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            Cursor c = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    listaString.add(id);
                    mAdapter.notifyDataSetChanged();
                } while (c.moveToNext());
            }
            db.close();
        }
    }


    /*CARGA LA LISTA CORRESPONDIENTE AL ITEM DEL PAGER SELECCIONADO*/
    public void cargar() {

        switch (suiche) {
            case "fragment_task":

                cargaCreadores();

                break;
            case "fragment_doing":

                cargaPersonajes();

                break;
            case "fragment_done":

                cargaSeries();

                break;

        }
    }

    @Override
    public void onClickRecycler(final View view, final int position) {


    }

}
