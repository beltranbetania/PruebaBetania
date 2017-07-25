package com.momentumlab.marvelcomicvisor.pruebabetania.Fragmentos;

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
import com.momentumlab.marvelcomicvisor.pruebabetania.data.ApiClient;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Comic;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.Creador;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.CreadoresConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.personajes.Personaje;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.personajes.PersonajesConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.series.SeriesConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.series.SeriesResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* FRAGMENTO EN EL QUE SE MUESTRA LA LISTA CORRESPONDIENTE AL ITEM SELECCIONADO DEL PAGER
* */
public class PagerHolderFragment extends Fragment implements ItemClickListener {

    private RecyclerView mRecyclerView;
    private ListaInternaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String suiche;
    private List<String> listaString = new ArrayList<String>();
    private Comic comic; // PERSONAJE SOBRE EL CUAL SE MUESTRAN LAS LISTAS DETALLE

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }

    public PagerHolderFragment() {

    }

    public static PagerHolderFragment newInstance(int param1, String param2, Serializable h) {
        PagerHolderFragment fragment = new PagerHolderFragment();
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
            comic = (Comic) getArguments().getSerializable(Const.COMIC_KEY);
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
        String id = comic.getId().toString();
        try {
            ApiClient.Factory.getIntance().getCreadores(id).enqueue(new Callback<CreadoresConsulta>() {
                @Override
                public void onResponse(Call<CreadoresConsulta> call, Response<CreadoresConsulta> response) {

                    if (response.isSuccessful()) {

                        for (Creador f :
                                response.body().getData().getResults()) {
                            listaString.add(f.getFullName());
                            mAdapter.notifyDataSetChanged();
                        }

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CreadoresConsulta> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {

        }


    }


    public void cargaSeries() {


        String id = comic.getId().toString();
        try {
            ApiClient.Factory.getIntance().getSeries(id).enqueue(new Callback<SeriesConsulta>() {
                @Override
                public void onResponse(Call<SeriesConsulta> call, Response<SeriesConsulta> response) {

                    if (response.isSuccessful()) {

                        for (SeriesResult f :
                                response.body().getData().getResults()) {
                            listaString.add(f.getTitle());

                            mAdapter.notifyDataSetChanged();
                        }

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<SeriesConsulta> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {

        }


    }

    public void cargaPersonajes() {

        String id = comic.getId().toString();
        try {
            ApiClient.Factory.getIntance().getPersonajes(id).enqueue(new Callback<PersonajesConsulta>() {
                @Override
                public void onResponse(Call<PersonajesConsulta> call, Response<PersonajesConsulta> response) {

                    if (response.isSuccessful()) {

                        for (Personaje f :
                                response.body().getData().getResults()) {
                            listaString.add(f.getName());

                            mAdapter.notifyDataSetChanged();
                        }

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<PersonajesConsulta> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {

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
