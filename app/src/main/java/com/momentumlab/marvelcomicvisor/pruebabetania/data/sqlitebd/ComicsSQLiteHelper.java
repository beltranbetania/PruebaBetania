package com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
* Mediante esta clase se crean y modifican las tabla de la bae de datos interna
*
* */

public class ComicsSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateComic = QueryComic.creaTablaComics();
    String sqlCreate = QueryComic.creaTablafavorito();
    String sqlCreatePersonaje = QueryComic.creaTablaPersonajes();
    String sqlCreateSerie = QueryComic.creaTablaSeries();
    String sqlCreateCreador = QueryComic.creaTablaCreadores();
    String sqlCreatePersonajeComic = QueryComic.creaTablaPersonajescomic();
    String sqlCreateSerieComic = QueryComic.creaTablaSeriescomic();
    String sqlCreateCreadorComic = QueryComic.creaTablaCreadorescomic();

    String sqlDropPersonajeComic = QueryComic.dropTablaPersonajescomic();
    String sqlDropSerieComic = QueryComic.dropTablaSeriescomic();
    String sqlDropCreadorComic = QueryComic.dropTablaCreadorescomic();
    String sqlDropPersonaje = QueryComic.dropTablaPersonajes();
    String sqlDropSerie = QueryComic.dropTablaSeries();
    String sqlDropCreador = QueryComic.dropTablaCreadores();
    String sqlDrop = QueryComic.dropTablafavorito();
    String sqlDropComic = QueryComic.dropTablaComics();


    public ComicsSQLiteHelper(Context contexto, String nombre,
                              CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        creaBD(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad se usa directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        dropBd(db);

        //Se crea la nueva versión de la tabla
        creaBD(db);
    }

    public void creaBD(SQLiteDatabase db) {

        db.execSQL(sqlCreateComic);
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreatePersonaje);
        db.execSQL(sqlCreateSerie);
        db.execSQL(sqlCreateCreador);
        db.execSQL(sqlCreatePersonajeComic);
        db.execSQL(sqlCreateSerieComic);
        db.execSQL(sqlCreateCreadorComic);

    }

    public void dropBd(SQLiteDatabase db) {
        db.execSQL(sqlDropPersonajeComic);
        db.execSQL(sqlDropSerieComic);
        db.execSQL(sqlDropCreadorComic);
        db.execSQL(sqlDropPersonaje);
        db.execSQL(sqlDropSerie);
        db.execSQL(sqlDropCreador);
        db.execSQL(sqlDrop);
        db.execSQL(sqlDropComic);
    }


}




