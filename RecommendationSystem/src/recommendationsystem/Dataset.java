/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendationsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joseadiazg
 */
public final class Dataset {
    
    private ArrayList<Movies> movies;
    private ArrayList<Ratings> ratings =null;
    private ArrayList<Users> users = null;
    
    private Map<Integer, Map<Integer,Integer>> userRatings = null;
    
    public Dataset()
    {
        this.movies = new ArrayList();
        this.ratings = new ArrayList();
        this.users = new ArrayList();
        this.userRatings = new HashMap<>(); 
        
    }
    
    public void loadDataset(String moviesFile, String usersFile, String ratingsFile) throws IOException
    {
        this.loadMovies(moviesFile);
        this.loadUsers(usersFile);
        this.loadRatings(ratingsFile);
        
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }
    
    public void loadMovies(String moviesFile) throws IOException
    {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File (moviesFile);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            
            while((linea=br.readLine())!=null)
            {
                String[] split = linea.split("\\|");
                movies.add(new Movies(Integer.parseInt(split[0]), split[1]));
            }
                
        }
        catch(IOException e){
        }
        finally{
            try{                    
               if( null != fr ){   
                    fr.close();     
                }                  
            }catch (IOException e2){ 
            }
        }
    }
        
    
    public void loadUsers(String usersFile)
    {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File (usersFile);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;            
            while((linea=br.readLine())!=null)
            {
                String[] split = linea.split("\\|");
                users.add(new Users(Integer.parseInt(split[0]), Integer.parseInt(split[1]), split[2]));
            }
                
        }
        catch(IOException e){
        }
        finally{
            try{                    
               if( null != fr ){   
                    fr.close();     
                }                  
            }catch (IOException e2){ 
            }
        }
        
    }
    
    public void loadRatings(String ratingsFile)
    {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File (ratingsFile);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;            
            while((linea=br.readLine())!=null)
            {
                String[] split = linea.split("\t");
                ratings.add(new Ratings(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
                
        }
        catch(IOException e){
        }
        finally{
            try{                    
               if( null != fr ){   
                    fr.close();     
                }                  
            }catch (IOException e2){ 
            }
        }
    }
    
    public void loadUserRatings()
    {
        for(Ratings rating : ratings)
        {
            if(userRatings.containsKey(rating.getUser_id()))
            {
                //Añadimos nueva pelicula puntuada por el user
                userRatings.get(rating.getUser_id()).put(rating.getItem_id(),rating.getRating());
            }
            else
            {
                //Añadimos un nuevo usuario y la pelicula asociada
                HashMap<Integer,Integer> userRating = new HashMap();
                userRating.put(rating.getItem_id(),rating.getRating());
                userRatings.put(rating.getUser_id(),userRating);
            }
        }
    }
}
