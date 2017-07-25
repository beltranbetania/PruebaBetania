package com.momentumlab.marvelcomicvisor.pruebabetania.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.momentumlab.marvelcomicvisor.pruebabetania.Const;
import com.momentumlab.marvelcomicvisor.pruebabetania.R;
import com.momentumlab.marvelcomicvisor.pruebabetania.actividades.DetalleSqlite;
import com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores.ComicSqliteAdapter;
import com.momentumlab.marvelcomicvisor.pruebabetania.adaptadores.ItemClickListener;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ComicsSQLiteHelper;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ModeloComicSqlite;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.QueryComic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
* Fragmento donde es presentada la lista de comics que se obtienen de la bd interna
*
* */
public class FavoritosFragment extends Fragment implements ItemClickListener {

    public List<ModeloComicSqlite> personajes;
    private int a;
    private RecyclerView mRecyclerView;
    private ComicSqliteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout relativeLoaded;
    ModeloComicSqlite seleccionado;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;
    String iduser;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListadoFragment.OnFragmentInteractionListener mListener;

    public FavoritosFragment() {
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

        iduser = Profile.getCurrentProfile().getId();


        relativeLoaded = (RelativeLayout) rootView.findViewById(R.id.loader);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ComicSqliteAdapter(getActivity(), personajes);

        mAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mAdapter);


        //  ShowLoaded(); //MOSTRAR ANIMACION CARGANDO
        buscaFilms();//CARGAR LOS PRIMEROS PERSONAJES

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
        if (context instanceof ListadoFragment.OnFragmentInteractionListener) {
            mListener = (ListadoFragment.OnFragmentInteractionListener) context;
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

    @Override
    public void onClickRecycler(View view, int position) {

        Intent intent = new Intent(getActivity(), DetalleSqlite.class);

        Bundle bundle = new Bundle();
        seleccionado = personajes.get(position);
        bundle.putSerializable(Const.COMIC_KEY, seleccionado); // SE PASA EL PERSONAJE CORRESPONDIENTE AL SELECCIONADO
        intent.putExtras(bundle);
        startActivity(intent);
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


    public void buscaFilms() {


        String q = QueryComic.getComicsbyUser(iduser);

        ComicsSQLiteHelper usdbh =
                new ComicsSQLiteHelper(getActivity(), Const.NOMBRE_BD_ACTUAL, null, Const.VERSION_BD_ACTUAL);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            Cursor c = db.rawQuery(q, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(0);
                    String titulo = c.getString(1);
                    String price = c.getString(2);
                    String path = c.getString(3);
                    ModeloComicSqlite f = new ModeloComicSqlite();
                    f.setId(id);
                    f.setTitulo(titulo);
                    f.setPrecio(price);
                    f.setPath(path);
                    personajes.add(f);
                    mAdapter.notifyDataSetChanged();
                } while (c.moveToNext());
            }
            db.close();
        }

    }

    /*
    * AL TOCAR UN PERSONAJE SE ABRE EL ACTIVITY DETALLE
    * */

    public void HideLoaded() {
        relativeLoaded.setVisibility(View.INVISIBLE);
    }

    public void ShowLoaded() {
        relativeLoaded.setVisibility(View.VISIBLE);
    }

    public void editarOrden() {
        //usar lista aux
        Collections.shuffle(personajes);

        mAdapter.notifyDataSetChanged();

    }

    public void research() {

        int tam = personajes.size();
        for (int i = 0; i < tam; i++) {
            personajes.remove(0);
        }

        mAdapter.notifyDataSetChanged();
        buscaFilms();

    }


}

