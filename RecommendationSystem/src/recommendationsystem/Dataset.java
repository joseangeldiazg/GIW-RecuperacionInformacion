
package recommendationsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Esta clase implementa todos los métodos necesarios para
 * la carga de los datos de entrada en las estructuras 
 * apropiadas. También obtiene el vecindario y predice
 * las películas que podrían gustarle al usuario. 
 * 
 * 
 * @author joseadiazg
 * @version 1.0
 * @since 2017-05-17
 */
public final class Dataset 
{
    
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
    
    /**
     * El método se encarga de cargar los datos en las estructuas. 
     * 
     * @param moviesFile fichero con las peliculas.
     * @param usersFile fichero con los datos de los usuarios. 
     * @param ratingsFile fichero con los ratings. 
     */
    
    public void loadDataset(String moviesFile, String usersFile, String ratingsFile) throws IOException
    {
        this.loadMovies(moviesFile);
        this.loadUsers(usersFile);
        this.loadRatings(ratingsFile);
        
    }
    
    
    /**
     * El método se encarga de cargar las películas. 
     * 
     * @param moviesFile fichero con las peliculas.
     */
    
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
        
     /**
     * El método se encarga de cargar  los usuarios. 
     * 
     * @param usersFile fichero con los datos de usuarios.
     */
    
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
    
     /**
     * El método se encarga de cargar  los ratings. 
     * 
     * @param ratingsFile fichero con los ratings.
     */
    
    
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
    
    /**
     * Una vez cargado los ratings, añade para cada usuario 
     * un las películas que ve y su rating, de la siguiente forma:
     * 
     * IdUsuario[[IdPeliculaX, RatingPeliculaX],
     *           [IdPeliculaX, RatingPeliculaX],...]
     * 
     * Finalmente calcula la media de ratings para cada usuario.
     */
    
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
    
    /**
     *Calcula la media de ratings dados por todos los usuarios. 
     */
    
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
    
    /**
     *Calcula la media de ratings dados por un usuario en concreto. 
     */
    
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
    
    
    /**
     *Obtiene el valor V, es decir, una estructura con los usuarios
     * que comparten alguna similitud con el usuario objetivo.
     * 
     * Para concluir que comparten alguna similitud basta con que hayan
     * visto alguna película similar. 
     * 
     * @param evaluations Evaluaciones dadas por el usuario objetivo. 
     * @return Map con los usuarios que han visto alguna película similar,
     * la película y el rating dado a esta. 
     */
    
    public Map<Integer,Map<Integer,Integer>> getV(Map<Integer, Integer> evaluations)
    {
        Map<Integer,Map<Integer,Integer>> equal = new HashMap();
        
        for(Users user : users)
        {
            
            Map<Integer, Integer> values = new HashMap();
            values=userRatings.get(user.getUser_id());
               
                for(Integer i: evaluations.keySet())
                {
                    if(values.containsKey(i))
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
        return equal;
    }
    
    /**
     * Cálculo del vecindario con los k vecinos más similares al usuario 
     * objetivo. 
     * 
     * @param evaluations Evaluaciones dadas por el usuario objetivo. 
     * @param k número de vecinos a tener en cuenta. 
     * 
     * @return Map con el id de usuario vecino y su valor de similitud.
     */
    
    public Map<Integer,Float> vecindario(Map<Integer, Integer> evaluations, int k)
    {
        Map<Integer,Map<Integer,Integer>> v = getV(evaluations);
        Map<Integer,Float> similitud = new HashMap<>();
        Map<Integer,Float> salida = new HashMap<>();
        float v_avg, denominador=0, numerador=0, resultado=0, control=0;
        
        //iteramos sobre todos los usuarios que tienen las mismas evaluaciones
        for(Integer i: v.keySet())
        {
            v_avg=getAvg(i);
            denominador= calcDenominador(evaluations, v.get(i), v_avg);
            numerador = calcNumerador(evaluations, v.get(i), v_avg);
            resultado=numerador/denominador;
            similitud.put(i,resultado);
        }

        // ordenamos los valores por mayor valor de value y devolvemos los k primeros
        Map<Integer,Float> ordenado = sortHashMapByValues(similitud);
        
        for(Integer i : ordenado.keySet())
        {
            if(control<k)
            {
                salida.put(i, ordenado.get(i));
            }
            else 
                break;
        }
        
        return salida;
    }
    
    
    /**
     * Prediccion de las peliculas a recomendar.  
     * 
     * @param evaluationsU Evaluaciones dadas por el usuario objetivo. 
     * @param vecindario K vecinos más cercanos. 
     * 
     * @return ArrayList de integer con los id de las películas a recomendar. 
     */
    
    public ArrayList<Integer> recomendaciones(Map<Integer, Integer> evaluationsU, 
            Map<Integer,Float> vecindario)
    {
        ArrayList<Integer> recomendaciones = new ArrayList<>();
        
        float c, sumatorio_sim, sumatorio_final;
        float u_avg, v_avg, r_v_i, r_u_i;
        u_avg=calculaMediaParam(evaluationsU);
        
        
        //Iteramos por todas las peliculas en el dataset
        for(Movies movie : movies)
        {
            sumatorio_sim=0;
            sumatorio_final=0;
            v_avg=0;
            r_v_i=0;
            r_u_i=0;
            c=0;
            
            if(!evaluationsU.containsKey(movie.getMovie_id()))
            {
                //Vemos si cada uno de los usuarios del vecindario ha visto esa pelicula
                for(Integer i : vecindario.keySet())
                {
                    if(userRatings.get(i).containsKey(movie.getMovie_id()))
                    {
                        v_avg=getAvg(i);
                        r_v_i=userRatings.get(i).get(movie.getMovie_id());
                        sumatorio_sim+=vecindario.get(i);
                        sumatorio_final+=vecindario.get(i)*(r_v_i-v_avg);
                    }                     
                }
                c=(1/abs(sumatorio_sim));
                r_u_i=u_avg+c*sumatorio_final;
                
                if(r_u_i>=4)
                {
                    recomendaciones.add(movie.getMovie_id());
                }
            }
            else
                break;
        }   
        return recomendaciones;
    }
    
     /**
     * Obtiene el denominador de la correlación Pearson.  
     * 
     * @param evaluationsU Evaluaciones dadas por el usuario objetivo. 
     * @param evaluationsV Evaluaciones dadas uno de los vecinos. 
     * @param v_avg media de ratings del usuario vecino. 
     * 
     * @return denominador para la correlación Pearson. 
     */
    
    public float calcDenominador(Map<Integer, Integer> evaluationsU, Map<Integer, Integer> evaluationsV, float v_avg)
    {
        float u_avg, denominador=0, sumatoria_v=0, sumatoria_u=0;
        u_avg=calculaMediaParam(evaluationsU);
        Map<Integer, Integer> interseccion = new HashMap(evaluationsU);
        
        //Calculamos la intersección para no tener que comprobar si estamos 
        //ante la misma película en ambos map continuamente.
        
        interseccion.keySet().retainAll(evaluationsV.keySet());
        
        SortedSet<Integer> keys = new TreeSet<Integer>(interseccion.keySet());
        
        for(Integer i : keys)
        {          
            sumatoria_u+=Math.pow(evaluationsU.get(i)-u_avg,2);
            sumatoria_v+=Math.pow(evaluationsV.get(i)-v_avg,2);
        }
        
        denominador = (float) (sqrt(sumatoria_u)*sqrt(sumatoria_v));
        
        return denominador; 
    }
    
    /**
     * Obtiene el numerador de la correlación Pearson.  
     * 
     * @param evaluationsU Evaluaciones dadas por el usuario objetivo. 
     * @param evaluationsV Evaluaciones dadas uno de los vecinos. 
     * @param v_avg media de ratings del usuario vecino. 
     * 
     * @return numerador para la correlación Pearson. 
     */
    
    public float calcNumerador(Map<Integer, Integer> evaluationsU, Map<Integer, Integer> evaluationsV, float v_avg)
    {
        float u_avg, numerador=0;
        u_avg=calculaMediaParam(evaluationsU);
        
        
        Map<Integer, Integer> interseccion = new HashMap(evaluationsU);
        
        //Calculamos la intersección para no tener que comprobar si estamos 
        //ante la misma película en ambos map continuamente.
        
        interseccion.keySet().retainAll(evaluationsV.keySet());
        
        SortedSet<Integer> keys = new TreeSet<Integer>(interseccion.keySet());
        
        for(Integer i : keys)
        {
            numerador+=((evaluationsU.get(i)-u_avg)*(evaluationsV.get(i)-v_avg));
        }   
        return numerador;  
    }
    
    /**
     * Ordena un Map por value.  
     * 
     * @param passedMap Map a ordenar. 
     * 
     * @return map ordenado por valor. 
     */
    
    public LinkedHashMap<Integer, Float> sortHashMapByValues(Map<Integer, Float> passedMap) 
    {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Float> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, Float> sortedMap = new LinkedHashMap<>();

        Iterator<Float> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Float val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                Float comp1 = passedMap.get(key);
                Float comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
    
    
    /**
     * Obtiene el título de una película. 
     * 
     * @param id_movie Id de la película a obtener. 
     * 
     * @return Titulo de la película. 
     */
    
    public String findMovie(int id_movie)
    {
        String title=null;
        for(Movies movie: movies)
        {
            if(movie.getMovie_id()==id_movie)
            {
                title=movie.getMovie_title();
            }
        }
        return title;
    }
    
    /**
     * Obtiene la media de ratings de un usuario.
     * 
     * @param user_id Id del usuario a obtener la media. 
     * 
     * @return media de ratings de un usuario en concreto. 
     */
    
    public float getAvg(int user_id)
    {
        float avg=0;
        for(Users user: users)
        {
            if(user.getUser_id()==user_id)
            {
                avg=user.getUser_id();
            }
        }
        return avg;
    }
}
