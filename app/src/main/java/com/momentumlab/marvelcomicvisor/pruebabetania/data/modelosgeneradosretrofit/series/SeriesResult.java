
package com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.comic.Events;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.Comics;
import com.momentumlab.marvelcomicvisor.pruebabetania.data.modelosgeneradosretrofit.creadores.Series;

public class SeriesResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("thumbnail")
    @Expose
    private Object thumbnail;
    @SerializedName("creators")
    @Expose
    private Creators creators;
    @SerializedName("characters")
    @Expose
    private Characters characters;
    @SerializedName("series")
    @Expose
    private Series series;
    @SerializedName("comics")
    @Expose
    private Comics comics;
    @SerializedName("events")
    @Expose
    private Events events;
    @SerializedName("originalIssue")
    @Expose
    private OriginalIssue originalIssue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Object getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Creators getCreators() {
        return creators;
    }

    public void setCreators(Creators creators) {
        this.creators = creators;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public OriginalIssue getOriginalIssue() {
        return originalIssue;
    }

    public void setOriginalIssue(OriginalIssue originalIssue) {
        this.originalIssue = originalIssue;
    }

}
