/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendationsystem;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author joseadiazg
 */
public class RecommendationSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        String moviesFile = "/Users/joseadiazg/Desktop/MASTER/GIW-RecuperacionInformacion/RecommendationSystem/ml-data/u.item";
        String usersFile = "/Users/joseadiazg/Desktop/MASTER/GIW-RecuperacionInformacion/RecommendationSystem/ml-data/u.user";
        String ratingsFile = "/Users/joseadiazg/Desktop/MASTER/GIW-RecuperacionInformacion/RecommendationSystem/ml-data/u.data";
        
        Dataset dataset = new Dataset();
        
        ArrayList<Movies> movies = dataset.getMovies();
        
        
        /*for (Movies movie : movies) {
            movie.toString();
        }*/
    } 
}
