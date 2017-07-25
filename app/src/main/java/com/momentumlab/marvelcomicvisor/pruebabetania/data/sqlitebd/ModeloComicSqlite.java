package com.momentumlab.marvelcomicvisor.pruebabetania.data.sqlitebd;

import java.io.Serializable;

/**
 * Modelo de Comics que se inflan en las listas obtenidads desde la bd interna
 */

public class ModeloComicSqlite implements Serializable {

    private String titulo;
    private String precio;
    private String fecha;
    private int id;
    private String descripcion;
    private int paginas;
    private String path;


    public ModeloComicSqlite() {

    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPrecio() {
        return precio;
    }

    public String getFecha() {
        return fecha;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPaginas() {
        return paginas;
    }

    public String getPath() {
        return path;
    }
}
