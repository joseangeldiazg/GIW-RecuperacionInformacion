
package recommendationsystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal. 
 * 
 * 
 * @author joseadiazg
 * @version 1.0
 * @since 2017-05-17
 */
public class RecommendationSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        final int K = 10;
        final int VALORACIONES = 20;
        
        String moviesFile = "ml-data/u.item";
        String usersFile = "ml-data/u.user";
        String ratingsFile = "ml-data/u.data";
        
        Dataset dataset = new Dataset();
        
        int movie=0;
        
        dataset.loadDataset(moviesFile, usersFile, ratingsFile);
        dataset.loadUserRatings();
        
        //Obtenemos el conjunto de prueba
        
        int numero;
        
        Scanner entradaEscaner = new Scanner (System.in);
        Random r = new Random();
        
        Map<Integer, Integer> valoraciones = new HashMap();
        ArrayList<Integer> repetidos = new ArrayList();
        
        System.out.println("******************************************************");
        System.out.println("Se mostrarán una seríe de peliculas y tendras que valorarlas...");
        System.out.println("******************************************************");
        
        for(int i=0; i<VALORACIONES; i++)
        {
            numero=r.nextInt(dataset.getMovies().size());
            int rating=0;
            while(repetidos.contains(numero))
            {
                numero=r.nextInt(dataset.getMovies().size());
            }
            
            System.out.println("Valoración (1-5) para: "+
                    dataset.getMovies().get(numero).getMovie_title());
            rating = Integer.parseInt(entradaEscaner.nextLine());
            
            if(rating > 5)
            {
                System.out.println("Lo máximo es 5. Se asignará este valor.");
                rating=5;
            }
            else if(rating<1)
            {
                System.out.println("Lo mínimo es 1. Se asignará este valor.");
                rating=1;
            }
            
            valoraciones.put(dataset.getMovies().get(numero).getMovie_id(), rating);
            repetidos.add(numero);
        }

        //Calculamos el vecindario 
        
        Map<Integer,Float> vecindario = dataset.vecindario(valoraciones, K);
        ArrayList<Integer> recomendaciones = dataset.recomendaciones(valoraciones, vecindario); 
        
        System.out.println("******************************************************");
        System.out.println("Quizá te podrían interesar las siguientes películas...");
        System.out.println("******************************************************");
       
        for (Integer recomendacion : recomendaciones) 
        {
            System.out.println(dataset.findMovie(recomendacion));
        }  
    } 
}
