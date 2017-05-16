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
    

    public ArrayList<Movies> getMovies() {
        return movies;
    }
    
    public void loadDataset(String moviesFile, String usersFile, String ratingsFile) throws IOException
    {
        this.loadMovies(moviesFile);
        this.loadUsers(usersFile);
        this.loadRatings(ratingsFile);
        
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
        
        calculaMedia();
    }
    
    public void calculaMedia()
    {
        for (Users user : users)
        {
            Map<Integer,Integer> userRating = new HashMap();
            userRating= userRatings.get(user.getUser_id()); 
            
            float sum=0.0f;
            float avg=0.0f;
            
            for(float f: userRating.values())
            {
                sum+=f;
            }
            avg=sum/userRating.size();
            user.setAvg_rating(avg);
        }
    }
    
    public float calculaMediaParam(Map<Integer,Integer> userRating)
    {
        float sum=0.0f;
        float avg=0.0f;
            
        for(float f: userRating.values())
        {
            sum+=f;
        }
        avg=sum/userRating.size();
        
        return avg;
    }
    
    public Map<Integer,Map<Integer,Integer>> getV(Map<Integer, Integer> evaluations)
    {
        Map<Integer,Map<Integer,Integer>> equal = new HashMap();
        int control=0;
        
        for(Users user : users)
        {
            
            Map<Integer, Integer> values = new HashMap();
            values=userRatings.get(user.getUser_id());
               
            if(evaluations.size()<=values.size())
            {
                for(Integer i: evaluations.keySet())
                {
                    if(values.containsKey(i))
                    {
                        control++;
                    }
                }

                if(control==evaluations.size()-1)
                {
                    //Si estamos aqui significará que todas las peliculas coinciden
                    
                    for(Integer i: evaluations.keySet())
                    {
                        if(equal.containsKey(user.getUser_id()))
                        {
                            //Añadimos nueva pelicula puntuada por el user
                            equal.get(user.getUser_id()).put(i,values.get(i));
                        }
                        else
                        {
                            HashMap<Integer,Integer> userRating = new HashMap();
                            userRating.put(i,values.get(i));
                            equal.put(user.getUser_id(),userRating);
                        }   
                    }
                    
                }
                
            }            
        } 
        return equal;
    }
    
    public Map<Integer,Float> vecindario(Map<Integer, Integer> evaluations, Map<Integer,Map<Integer,Integer>> v, int k)
    {
        
        
        Map<Integer,Float> result = new HashMap<>();
        
        for(Integer i: v.keySet())
        {
            //iteramos sobre todos los usuarios que tienen las mismas evaluaciones
            
        }
        
        
        return null;

    }
    
    public void calcDenominador(Map<Integer, Integer> evaluationsU, Map<Integer, Integer> evaluationsV, float v_avg)
    {
        float u_avg;
        u_avg=calculaMediaParam(evaluationsU);
        
        
    }
    
    public void calcNumerador(Map<Integer, Integer> evaluationsU, Map<Integer, Integer> evaluationsV)
    {
        
    }
}
