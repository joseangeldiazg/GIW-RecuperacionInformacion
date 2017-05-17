
package recommendationsystem;

/**
 * Clase para definir y mantener las 
 * peliculas y sus datos asociados. 
 * 
 * 
 * @author joseadiazg
 * @version 1.0
 * @since 2017-05-17
 */
public class Movies {
    
    private int movie_id;
    private String movie_title;

    public Movies(int movie_id, String movie_title) {
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
