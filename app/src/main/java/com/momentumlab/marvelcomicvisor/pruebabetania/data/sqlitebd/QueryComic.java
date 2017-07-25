package com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd;

import android.content.ContentValues;
import android.util.Log;

/**
 * Contiene todas las consultas a la base de datos interna
 */

public class QueryComic {

///SELECTS

    public static String getFavorito(Integer idComic, String iduser) {
        String r = "SELECT * FROM Favoritos WHERE idcomic=" + idComic.toString() + " and iduser=\"" + iduser + "\"";
        return r;
    }

    public static String getComicsbyUser(String user) {
        String r = "Select Comics.idcomic, titulo, price, imgpath from Comics join Favoritos on (Comics.idcomic=Favoritos.idcomic and Favoritos.iduser='" + user + "');";
        return r;
    }

    public static String getComicbyId(Integer idComic) {
        String r = " Select idcomic, titulo, fecha, price, descripcion, paginas, imgpath from Comics where idcomic =" + idComic + ";";
        return r;
    }


    public static String getCreadoresbyComic(Integer idComic) {
        String r = "  select nombrecreador from Creadores join Creadorescomic on (Creadores.idcreador=Creadorescomic.idcreador and Creadorescomic.idcomic=" + idComic + ");";
        return r;
    }

    public static String getseriesbycomic(Integer idComic) {
        String r = "select nombreserie from Series join Seriescomic on (Series.idserie=Seriescomic.idserie and Seriescomic.idcomic=" + idComic + ");";
        return r;
    }


    public static String getpersonajesbycomic(Integer idComic) {
        String r = "  select nombrepersonaje from Personajes join Personajescomic on (Personajes.idpersonaje=Personajescomic.idpersonaje and Personajescomic.idcomic=" + idComic + ");";

        return r;
    }


///INSERTS

    public static String insertComic(int idComic, String titulo, String fecha, String precio, String descripcion, int paginas, String path) {
        //guarda el comic
        String nuevoRegistro = "insert into Comics (idcomic, titulo, fecha, price,descripcion, paginas, imgpath) values " +
                "(" + idComic + ", '" + titulo + "', '" + fecha + "', '" + precio + "','" + descripcion + "'," + paginas + ", '" + path + "');";
        Log.d("sqwe", nuevoRegistro);
        return nuevoRegistro;
    }

    public static String insertFavoritos(String iduser, int idcomic) {

        String r = "INSERT INTO Favoritos  (idcomic,iduser) VALUES (" + idcomic + ", '" + iduser + "'); ";
        Log.d("fff", r);
        return r;
    }

    public static String insertCreador(int idcreador, String nombre) {

        String r = "INSERT INTO Creadores (nombrecreador,idcreador) VALUES ('" + nombre + "'," + idcreador + ");";
        return r;
    }

    public static String insertpersonaje(int idpersonaje, String nombre) {

        String r = "INSERT INTO Personajes (nombrepersonaje,idpersonaje) VALUES ('" + nombre + "'," + idpersonaje + "); ";
        return r;
    }

    public static String insertSeries(int idserie, String nombre) {

        String r = "INSERT INTO Series  (nombreserie,idserie) VALUES ('" + nombre + "'," + idserie + "); ";
        return r;
    }

    public static String insertSeriesComic(int idserie, int idcomic) {

        String r = "INSERT INTO Seriescomic  (idcomic,idserie) VALUES (" + idcomic + "," + idserie + "); ";
        return r;
    }

    public static String insertPersonajesComic(int idpersonaje, int idcomic) {

        String r = "INSERT INTO Personajescomic  (idcomic,idpersonaje) VALUES (" + idcomic + "," + idpersonaje + "); ";
        return r;
    }

    public static String insertCreadoresComic(int idcreador, int idcomic) {

        String r = "INSERT INTO Creadorescomic  (idcomic,idcreador) VALUES (" + idcomic + "," + idcreador + "); ";
        return r;
    }

    //////// ELIMINAR ELEMENTOS


    //////// CREANDO LAS TABLAS

    public static String creaTablafavorito() {
        String r = "CREATE TABLE Favoritos (iduser TEXT, idcomic INTEGER, PRIMARY KEY( iduser,idcomic), FOREIGN KEY(idcomic) REFERENCES Comics(idcomic));";
        return r;
    }

    public static String creaTablaComics() {
        String r = "CREATE TABLE Comics (idcomic INTEGER, titulo TEXT, fecha TEXT, price TEXT, descripcion TEXT, paginas INTEGER, imgpath TEXT, PRIMARY KEY(idcomic));";
        return r;
    }

    public static String creaTablaPersonajes() {
        String r = "CREATE TABLE Personajes (idpersonaje INTEGER, nombrepersonaje TEXT,  PRIMARY KEY(idpersonaje) );";
        return r;
    }

    public static String creaTablaSeries() {
        String r = "CREATE TABLE Series (idserie INTEGER, nombreserie TEXT,  PRIMARY KEY(idserie));";
        return r;
    }

    public static String creaTablaCreadores() {
        String r = "CREATE TABLE Creadores (idcreador INTEGER, nombrecreador TEXT,  PRIMARY KEY(idcreador));";
        return r;
    }

    public static String creaTablaPersonajescomic() {
        String r = "CREATE TABLE Personajescomic (idcomic INTEGER,idpersonaje INTEGER,PRIMARY KEY(idcomic,idpersonaje), FOREIGN KEY(idcomic) REFERENCES Comics(idcomic), FOREIGN KEY(idpersonaje) REFERENCES Personajes(idpersonaje));";
        return r;
    }

    public static String creaTablaSeriescomic() {
        String r = "CREATE TABLE Seriescomic (idcomic INTEGER,idserie INTEGER,PRIMARY KEY(idcomic,idserie), FOREIGN KEY(idcomic) REFERENCES Comics(idcomic), FOREIGN KEY(idserie) REFERENCES Series(idserie));";
        return r;
    }

    public static String creaTablaCreadorescomic() {
        String r = "CREATE TABLE Creadorescomic (idcomic INTEGER,idcreador INTEGER ,PRIMARY KEY(idcomic,idcreador),FOREIGN KEY(idcomic) REFERENCES Comics(idcomic), FOREIGN KEY(idcreador) REFERENCES Creadores(idcreador));";
        return r;
    }


    public static String dropTablafavorito() {
        String r = "DROP TABLE Favoritos;";
        return r;
    }

    public static String dropTablaComics() {
        String r = "DROP TABLE Comics;";
        return r;
    }

    public static String dropTablaPersonajes() {
        String r = "DROP TABLE Personajes;";
        return r;
    }

    public static String dropTablaSeries() {
        String r = "DROP TABLE Series;";
        return r;
    }

    public static String dropTablaCreadores() {
        String r = "DROP TABLE Creadores;";
        return r;
    }

    public static String dropTablaPersonajescomic() {
        String r = "DROP TABLE Personajescomic;";
        return r;
    }

    public static String dropTablaSeriescomic() {
        String r = "DROP TABLE Seriescomic;";
        return r;
    }

    public static String dropTablaCreadorescomic() {
        String r = "DROP TABLE Creadorescomic";
        return r;
    }

//DELETE

    public static String eliminarFAV(String iduser, int idcomic) {

        String x = "DELETE FROM Favoritos WHERE idcomic=" + idcomic + " and iduser= '" + iduser + "'";
        return x;


    }

}
