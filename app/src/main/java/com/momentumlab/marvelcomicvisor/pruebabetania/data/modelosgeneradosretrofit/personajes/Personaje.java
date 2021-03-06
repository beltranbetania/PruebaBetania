
package com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.personajes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Stories;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Thumbnail;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Url;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.Comics;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.Events;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.Series;

public class Personaje {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("comics")
    @Expose
    private Comics comics;
    @SerializedName("series")
    @Expose
    private Series series;
    @SerializedName("stories")
    @Expose
    private Stories stories;
    @SerializedName("events")
    @Expose
    private Events events;
    @SerializedName("urls")
    @Expose
    private List<Url> urls = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Stories getStories() {
        return stories;
    }

    public void setStories(Stories stories) {
        this.stories = stories;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

}
