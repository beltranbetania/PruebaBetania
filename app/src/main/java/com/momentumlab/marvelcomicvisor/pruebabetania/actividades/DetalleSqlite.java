package com.momentumlab.marvelcomicvisor.pruebabetania.actividades;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import com.momentumlab.marvelcomicvisor.pruebabetania.Fragmentos.PageHolderFragmentSQLite;
import com.momentumlab.marvelcomicvisor.pruebabetania.R;
import com.momentumlab.marvelcomicvisor.pruebabetania.ViewPagerCustomDuration;
import com.momentumlab.marvelcomicvisor.pruebabetania.ZoomOutPageTransformer;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ComicsSQLiteHelper;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.ModeloComicSqlite;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd.QueryComic;

import java.io.File;


public class DetalleSqlite extends AppCompatActivity {

    private ViewPagerCustomDuration pager;
    private TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;
    public static ModeloComicSqlite comic;
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
        comic = (ModeloComicSqlite) bundle.getSerializable(Const.COMIC_KEY);
        titulo = (TextView) findViewById(R.id.textTITULO);
        descripcion = (TextView) findViewById(R.id.textDESCR);
        precio = (TextView) findViewById(R.id.textPRCIO);
        fecha = (TextView) findViewById(R.id.textFECHAA);
        paginas = (TextView) findViewById(R.id.textPAG);
        imagen = (ImageView) findViewById(R.id.imagen);

        titulo.setText(comic.getTitulo());

        precio.setText(comic.getPrecio());

        fecha.setText(comic.getFecha());

        paginas.setText(String.valueOf(comic.getPaginas()));

        descripcion.setText(comic.getDescripcion());

        String url = "url";
        try {

            url = comic.getPath();
        } catch (Exception e) {

            Log.d("errorimage", url);
        }

      /*  ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String newname = comic.getId()+"."+ "jpg";
        File myImageFile = new File(directory, newname);*/

        Glide
                .with(this)
                .load(comic.getPath())
                .placeholder(R.drawable.sin_foto)
                .error(R.drawable.sin_foto)
                .crossFade()
                .into(imagen);

        pager = (ViewPagerCustomDuration) findViewById(R.id.view_pager);
        pager.setOffscreenPageLimit(3);
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

    public void guardarFavorito() {

        String q = QueryComic.insertFavoritos(iduser, comic.getId());

        ComicsSQLiteHelper usdbh =
                new ComicsSQLiteHelper(this, Const.NOMBRE_BD_ACTUAL, null, Const.VERSION_BD_ACTUAL);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL(q);
                fav.setImageResource(R.drawable.ic_favorite_black_36dp);
            } catch (SQLiteException e) {

            }

            db.close();
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

            }
            db.close();
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
                    return PageHolderFragmentSQLite.newInstance(0, Const.FRAGMENT_CREADORES, comic);
                case 1:
                    return PageHolderFragmentSQLite.newInstance(1, Const.FRAGMENT_PERSONAJES, comic);
                case 2:
                    return PageHolderFragmentSQLite.newInstance(2, Const.FRAGMENT_SERIES, comic);
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


    public void borrarImagen (){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, "my_image.jpeg");
        if (myImageFile.delete()) Log.d("image on the disk", "d");
    }

}