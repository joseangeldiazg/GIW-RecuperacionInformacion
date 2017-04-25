/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

import java.io.IOException;
import java.text.ParseException;
import indexer.data.Noticia;
import indexer.files.LuceneWriter;
import indexer.files.Parser;

/**
 *
 * @author joseadiazg
 * 
 */
public class Indexer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @throws org.apache.lucene.queryparser.classic.ParseException
     */
    public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

        //Abrimos el fichero y lo parseamos
        
        if (args.length !=3) 
        {   //si hay más de 1 parámetro
            System.out.println("Los parámetros no coinciden debe ser: java -jar indexer.java directorio/ficherosxml/ directorioIndex/ directoriopalababrasvacias/fichero.txt ");
        }
        else
        {
            Parser parser = new Parser();
            parser.parseNews(args[0]);

            LuceneWriter luceneWriter = new LuceneWriter(args[1], args[2]);
            try 
            {   
                //Vemos si podemos abrir un directorio para escribir
                if (luceneWriter.openIndex())
                {
                    for (Noticia noticia : parser.getNoticias()) 
                    {
                        luceneWriter.addNoticia(noticia);
                    }
                }   
                else 
                {
                    System.out.println("Problema detectado para abrir el directorio para escribir");
                }

            } catch (Exception e) {
                System.out.println("Threw exception " + e.getClass() + " :: " + e.getMessage());
            } finally {

                luceneWriter.finish();
            }
        }
        
    } 
}