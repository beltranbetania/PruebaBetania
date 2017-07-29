package com.momentumlab.marvelcomicvisor.pruebabetania.actividades;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.momentumlab.marvelcomicvisor.pruebabetania.Const;
import com.momentumlab.marvelcomicvisor.pruebabetania.Fragmentos.FavoritosFragment;
import com.momentumlab.marvelcomicvisor.pruebabetania.Fragmentos.PagerHolderFragment;
import com.momentumlab.marvelcomicvisor.pruebabetania.R;
import com.momentumlab.marvelcomicvisor.pruebabetania.ViewPagerCustomDuration;
import com.momentumlab.marvelcomicvisor.pruebabetania.ZoomOutPageTransformer;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.ApiClient;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Comic;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Example;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.Creador;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.CreadoresConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.personajes.Personaje;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.personajes.PersonajesConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.series.SeriesConsulta;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.series.SeriesResult;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ComicsSQLiteHelper;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.QueryComic;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
* En esta actividad se muestra el detalle de los comics, las llamadas al api se han hecho con Retrofit
*
* */


public class Detalle extends AppCompatActivity {

    private ViewPagerCustomDuration pager;
    private TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;
    public static Comic comic;
    TextView titulo;
    TextView descripcion;
    TextView precio;
    TextView fecha;
    TextView paginas;
    public ImageView imagen;
    public ImageView fav;
    private String iduser;
    private Integer idComic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Bundle bundle = getIntent().getExtras();
        comic = (Comic) bundle.getSerializable(Const.COMIC_KEY);
        titulo = (TextView) findViewById(R.id.textTITULO);
        descripcion = (TextView) findViewById(R.id.textDESCR);
        precio = (TextView) findViewById(R.id.textPRCIO);
        fecha = (TextView) findViewById(R.id.textFECHAA);
        paginas = (TextView) findViewById(R.id.textPAG);
        imagen = (ImageView) findViewById(R.id.imagen);

        titulo.setText(comic.getTitle());

        if (comic.getPrices().get(0).getPrice()!=0){

            precio.setText(comic.getPrices().get(0).getPrice().toString());
        }else {
            precio.setText("Agotado");
        }


        fecha.setText(comic.getDates().get(0).getDate());

        paginas.setText(comic.getPageCount().toString());

        descripcion.setText(comic.getDescription());
        /*
* Se carga la imagen con glide
*
* */
        String url = "url";
        try {

            url = comic.getImages().get(0).getPath() + "." + comic.getImages().get(0).getExtension();
        } catch (Exception e) {

            Log.d("errorimage", url);
        }
// cargando la imagen del personaje
        Glide
                .with(this)
                .load(url)
                .placeholder(R.drawable.sin_foto)
                .error(R.drawable.sin_foto)
                .crossFade()
                .into(imagen);


        pager = (ViewPagerCustomDuration) findViewById(R.id.view_pager);
        pager.setOffscreenPageLimit(5);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterViewPager);
        pager.addOnPageChangeListener(new ViewPagerCustomDuration.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
            }


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);


        iduser = Profile.getCurrentProfile().getId();
        idComic = comic.getId();

        fav = (ImageView) findViewById(R.id.floatingActionButton);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (esFavorito()) {

                    borrarFavorito(iduser, idComic);

                } else {
                    guardarFavorito();
                }
            }
        });


        if (esFavorito()) {
            fav.setImageResource(R.drawable.ic_favorite_black_36dp);
        }

    }

    //ADAPTADOR DEL PAGER QUE CONTIENE LAS LISTAS DE LOOS DETALLES
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return PagerHolderFragment.newInstance(0, Const.FRAGMENT_CREADORES, comic);
                case 1:
                    return PagerHolderFragment.newInstance(1, Const.FRAGMENT_PERSONAJES, comic);
                case 2:
                    return PagerHolderFragment.newInstance(2, Const.FRAGMENT_SERIES, comic);
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Creadores";
                case 1:
                    return "Personajes";
                case 2:
                    return "Series";

                default:
                    return null;
            }
        }
    }

    // indica si un comic es favorito para setear la imagen de identificacion
    public boolean esFavorito() {

        boolean bandera = true;
        ComicsSQLiteHelper usdbh =
                new ComicsSQLiteHelper(this, Const.NOMBRE_BD_ACTUAL, null, Const.VERSION_BD_ACTUAL);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            String query = QueryComic.getFavorito(idComic, iduser);
            Cursor c = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                bandera = true;
            } else {
                bandera = false;
            }

            db.close();
        }
        return bandera;
    }

    // cuando se clickea la imagen de favorito se guarda el item en la base de datos intrna SQLITE

    public void guardarFavorito() {

        String descripcion = "";
        String pathx = "";
        String precio ="";

        try {
            pathx = comic.getImages().get(0).getPath() + "." + comic.getImages().get(0).getExtension();

            descripcion = comic.getDescription();

            descripcion = descripcion.trim().replace("'", " ");
        } catch (Exception e) {
        }

        if (comic.getPrices().get(0).getPrice()==0){
            precio="Agotado";
        }else {

            precio=comic.getPrices().get(0).getPrice().toString();
        }

        String query = QueryComic.insertFavoritos(iduser, idComic);
        String h = QueryComic.insertComic(comic.getId(), comic.getTitle(), comic.getDates().get(0).getDate(), precio, descripcion, comic.getPageCount(), pathx);
        ComicsSQLiteHelper usdbh =
                new ComicsSQLiteHelper(this, Const.NOMBRE_BD_ACTUAL, null, Const.VERSION_BD_ACTUAL);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {

            try {
                db.execSQL(h);
            } catch (SQLiteException e) {
                Log.d("bdd1", "--->" + e.getMessage());
            }
            //guarda el Favorito
            try {
                db.execSQL(query); // guarda el fav
            } catch (SQLiteException e) {
                Log.d("bdd1", "--->" + e.getMessage());

            }
            db.close();

           //almacena en la bd interna los personajes del comic
            try {
                guardarPersonajes();

            } catch (Exception e) {

                Log.d("5am", e.getMessage());
            }
//almacena en la bd interna las seres del comic

            try {
                guardarSeries();

            } catch (Exception e) {


            }
//almacena en la bd interna los creadores del comic

            try {
                guardarCreadores();

            } catch (Exception e) {


            }

        }

       /* try {
            Log.d("defr","frfr");
            String anImageUrl= comic.getImages().get(0).getPath() +"."+ comic.getImages().get(0).getExtension();
            String newname = comic.getId()+"."+ comic.getImages().get(0).getExtension();
            Picasso.with(this).load(anImageUrl).into(picassoImageTarget(getApplicationContext(), "imageDir", newname));
            Log.d("defr2","frfr");
        }catch (Exception e){
            Log.d("---->",e.getMessage());
        }*/

        fav.setImageResource(R.drawable.ic_favorite_black_36dp);
    }



    public void guardarUnPersonaje(int id, String nombre) {

        nombre = nombre.trim().replace("'", " ");
        String query = QueryComic.insertpersonaje(id, nombre);
        String h = QueryComic.insertPersonajesComic(id, comic.getId());
        guardaDosQuery(query, h);

    }


    public void guardarUnCreador(int id, String nombre) {
        nombre = nombre.trim().replace("'", " ");
        String query = QueryComic.insertCreador(id, nombre);
        String h = QueryComic.insertCreadoresComic(id, comic.getId());
        guardaDosQuery(query, h);
    }

    public void guardarUnSerie(int id, String nombre) {
        nombre = nombre.trim().replace("'", " ");
        String query = QueryComic.insertSeries(id, nombre);
        String h = QueryComic.insertSeriesComic(id, comic.getId());
        guardaDosQuery(query, h);
    }

    public void guardaDosQuery(String q1, String q2) {
        ComicsSQLiteHelper usdbh =
                new ComicsSQLiteHelper(this, Const.NOMBRE_BD_ACTUAL, null, Const.VERSION_BD_ACTUAL);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL(q1);

            } catch (SQLiteException e) {

            }
            try {
                db.execSQL(q2); // guarda el fav

            } catch (SQLiteException e) {

            }
            db.close();
        }
    }

    //obtiene la lista de todos los personajes del comic y los almacena en la bd interna 1 a 1

    public void guardarPersonajes() {

        String id = comic.getId().toString();
        try {
            ApiClient.Factory.getIntance().getPersonajes(id).enqueue(new Callback<PersonajesConsulta>() {
                @Override
                public void onResponse(Call<PersonajesConsulta> call, Response<PersonajesConsulta> response) {

                    if (response.isSuccessful()) {

                        for (Personaje f :
                                response.body().getData().getResults()) {

                            guardarUnPersonaje(f.getId(), f.getName());
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
    //obtiene la lista de todos los series del comic y los almacena en la bd interna 1 a 1
    public void guardarSeries() {

        String id = comic.getId().toString();
        try {
            ApiClient.Factory.getIntance().getSeries(id).enqueue(new Callback<SeriesConsulta>() {
                @Override
                public void onResponse(Call<SeriesConsulta> call, Response<SeriesConsulta> response) {

                    if (response.isSuccessful()) {

                        for (SeriesResult f :
                                response.body().getData().getResults()) {

                            guardarUnSerie(f.getId(), f.getTitle());
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

    //obtiene la lista de todos los creadores del comic y los almacena en la bd interna 1 a 1

    public void guardarCreadores() {


        String id = comic.getId().toString();
        try {
            ApiClient.Factory.getIntance().getCreadores(id).enqueue(new Callback<CreadoresConsulta>() {
                @Override
                public void onResponse(Call<CreadoresConsulta> call, Response<CreadoresConsulta> response) {

                    if (response.isSuccessful()) {

                        for (Creador f :
                                response.body().getData().getResults()) {

                            guardarUnCreador(f.getId(), f.getFullName());
                        }

                    } else {
                        Log.d("NOU", "--->onFailure");
                    }
                }

                @Override
                public void onFailure(Call<CreadoresConsulta> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            Log.d("EXCEPTION", "--->catch excep");
        }
    }


    public void borrarFavorito(String iduser, int idcomic) {
        String q = QueryComic.eliminarFAV(iduser, idcomic);

        ComicsSQLiteHelper usdbh =
                new ComicsSQLiteHelper(this, Const.NOMBRE_BD_ACTUAL, null, Const.VERSION_BD_ACTUAL);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL(q);
                fav.setImageResource(R.drawable.ic_favorite_border_black_36dp);
            } catch (SQLiteException e) {
                Log.d("bdd1", "--->" + e.getMessage());
            }

            db.close();
        }

        FavoritosFragment.comics.remove(0);
        FavoritosFragment.mAdapter.notifyDataSetChanged();

    }
    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }

}