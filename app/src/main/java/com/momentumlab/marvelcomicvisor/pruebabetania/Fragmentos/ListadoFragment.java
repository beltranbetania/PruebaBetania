package com.momentumlab.marvelcomicvisor.pruebabetania.Fragmentos;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.momentumlab.marvelcomicvisor.pruebabetania.Const;
import com.momentumlab.marvelcomicvisor.pruebabetania.R;
//import com.momentumlab.marvelcomicvisor.pruebabetania.actividades.Detalle;
import com.momentumlab.marvelcomicvisor.pruebabetania.actividades.Detalle;
import com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores.ComicAdapter;
import com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores.ItemClickListener;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.ApiClient;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Comic;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoFragment extends Fragment implements ItemClickListener {

    public List<Comic> personajes;
    private int a;
    private RecyclerView mRecyclerView;
    private ComicAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout relativeLoaded;
    Comic seleccionado;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListadoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListadoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoFragment newInstance(String param1, String param2) {
        ListadoFragment fragment = new ListadoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listado, container, false);
        this.personajes = new ArrayList<>();
        this.a = 0;

        relativeLoaded = (RelativeLayout) rootView.findViewById(R.id.loader);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ComicAdapter(getActivity(), personajes);

        mAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mAdapter);


        ShowLoaded(); //MOSTRAR ANIMACION CARGANDO
        // buscaComics();//CARGAR LOS PRIMEROS PERSONAJES
        getbasic();
        // return inflater.inflate(R.layout.fragment_listado, container, false);
        materialDesignFAM = (FloatingActionMenu) rootView.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item2);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                research();
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                editarOrden();

            }
        });


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void buscaComics(int total) {
        int max = total - 31;
        Random r = new Random();
        int random2 = r.nextInt(max);

        try {
            ApiClient.Factory.getIntance().groupList(String.valueOf(random2)).enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {

                    if (response.isSuccessful()) {


                        for (Comic f :
                                response.body().getData().getResults()) {
                            personajes.add(f);
                            mAdapter.notifyDataSetChanged();
                        }


                        HideLoaded();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {

                    Context context = getActivity().getApplicationContext();
                    CharSequence text = t.toString();
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
        }

    }

    public void getbasic() {

        final ArrayList<Integer> total = new ArrayList();
        try {
            ApiClient.Factory.getIntance().getbasics().enqueue(new Callback<Example>() {

                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {

                    if (response.isSuccessful()) {
                        a = response.body().getData().getTotal();
                        buscaComics(a);

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {

                    t.printStackTrace();
                }
            });
        } catch (Exception e) {

        }


        //  return total.get(0).intValue();
    }


    /*
    * AL TOCAR UN PERSONAJE SE ABRE EL ACTIVITY DETALLE
    * */
    @Override
    public void onClickRecycler(View view, int position) {
        Intent intent = new Intent(getActivity(), Detalle.class);

        Bundle bundle = new Bundle();
        seleccionado = personajes.get(position);
        bundle.putSerializable(Const.COMIC_KEY, seleccionado); // SE PASA EL PERSONAJE CORRESPONDIENTE AL SELECCIONADO
        intent.putExtras(bundle);
        startActivity(intent);
    }


    //oculta el looader
    public void HideLoaded() {
        relativeLoaded.setVisibility(View.INVISIBLE);
    }

   //mustra el looader

    public void ShowLoaded() {
        relativeLoaded.setVisibility(View.VISIBLE);


    }

    //metodo para editar el orden de los items del recycle view

    public void editarOrden() {
        //usar lista aux
        Collections.shuffle(personajes);

        mAdapter.notifyDataSetChanged();

    }

    //metodo para buscar otro conjunto aleatorio de 30 comics

    public void research() {

        int tam = personajes.size();
        for (int i = 0; i < tam; i++) {
            personajes.remove(0);
        }

        mAdapter.notifyDataSetChanged();
        ShowLoaded();
        getbasic();

    }


}
