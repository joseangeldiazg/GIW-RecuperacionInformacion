/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneindexer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 *
 * @author joseadiazg
 * @author Andrey Corcodel 
 */
public class Peliculas {
    
    //Como en Json el nombre va en mayusculas es necesario declarar asi el tipo
    @JsonProperty("Title")
    private String Title = "";
    
    @JsonProperty("Year")
    private int Year = 0;
    
    @JsonProperty("Released")
    private String Released = "";
    
    @JsonProperty("Runtime")
    private String Runtime = "";
    
    @JsonProperty("Genre")
    private String Genre = "";
    
    @JsonProperty("Director")
    private String Director="";
    
    @JsonProperty("Actors")
    private String Actors="";
    
    @JsonProperty("Plot")
    private String Plot="";
    
    @JsonProperty("Language")
    private String Language="";
    
    @JsonProperty("Country")
    private String Country="";
    
    @JsonProperty("Awards")
    private String Awards="";
    
    @JsonProperty("ImbdRating")
    private double ImbdRating=0.0;
    

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
    
    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        this.Released = released;
    }
    
    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        this.Runtime = runtime;
    }
    
    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        this.Genre = genre;
    }
    
    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }
    
    
    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        this.Actors = actors;
    }
    
    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        this.Plot = plot;
    }
    
    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        this.Language = language;
    }
    
    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        this.Awards = awards;
    }
    
    public double getImbdRating() {
        return ImbdRating;
    }

    public void setImbdRating(double imbdRating) {
        this.ImbdRating = imbdRating;
    }
    
    @Override
    public String toString() {
        return "Pelicula{" + "Title=" + Title + ", Year=" + Year + ",Released=" + Released +", Runtime="+Runtime+", Genre="+Genre+
                ", Director"+ Director +", Actors="+Actors +", Plot=" +Plot+", Language="+Language+", Country=" + Country + 
                ", Awards="+Awards+", ImbdRating="+ImbdRating+"}";
    }     
}
