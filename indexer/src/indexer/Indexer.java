/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import indexer.data.Peliculas;
import indexer.files.FileOpener;
import indexer.files.LuceneWriter;
import org.apache.lucene.index.IndexWriter;

/**
 *
 * @author joseadiazg
 * 
 */
public class Indexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

        //abrimos nuestros datos
        FileOpener fOpener = new FileOpener("peliculas.json");
        
        LuceneWriter luceneWriter = new LuceneWriter("indexDir");
        ObjectMapper objectMapper = new ObjectMapper();
        
        
        try {
            
            //Vemos si podemos abrir un directorio para escribir
            if (luceneWriter.openIndex()){
                
                BufferedReader breader = new BufferedReader(fOpener.getFileForReading());
                String value = null;
                while((value = breader.readLine()) != null){
                    Peliculas pelicula  = objectMapper.readValue(value, Peliculas.class);
                    //añadimos cada pelicula al indice
                    luceneWriter.addPelicula(pelicula);
                    
                }
                
            } else {
                System.out.println("Problema detectado para abrir el directorio para escribir");
            }
             
            
        } catch (Exception e) {
            System.out.println("Threw exception " + e.getClass() + " :: " + e.getMessage());
        } finally {
            
            luceneWriter.finish();
        }
    }  
}