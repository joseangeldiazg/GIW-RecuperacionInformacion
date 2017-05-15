/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendationsystem;

/**
 *
 * @author joseadiazg
 */
public class Movie {
    
    private int movie_id;
    private String movie_title;

    public Movie(int movie_id, String movie_title) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    @Override
    public String toString() {
        return "Movie{" + "movie_id=" + movie_id + ", movie_title=" + movie_title + '}';
    }  
    
}
