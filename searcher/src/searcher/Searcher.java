/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


/**
 *
 * @author joseadiazg
 */
public class Searcher 
{
    
    private static SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
    private static int max = 100000;
    private static String indexLocation = "/Users/joseadiazg/Desktop/MASTER/GIW-RecuperacionInformacion/indexer/indexDir/";
    
    public ArrayList<Noticia> busquedaTexto(String cadena) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
        
        ArrayList<Noticia> noticias = new ArrayList();
        FSDirectory directory = FSDirectory.open(new File(Searcher.indexLocation));
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);   
        Sort sort;
        TopDocs hits;
        
            
        QueryParser parser = new QueryParser(Version.LUCENE_43, "Text", analyzer);
        Query consulta = parser.parse(cadena);
          
        sort = new Sort();
        sort.setSort(SortField.FIELD_DOC);
        hits = isearcher.search(consulta, max, sort);
        
        ScoreDoc[] arrayResultados = hits.scoreDocs;
       
        
        for (int i = 0; i < arrayResultados.length; i++) 
        {
             
            Document doc = isearcher.doc(arrayResultados[i].doc);
             
            Noticia noticia = new Noticia();
            // Añadimos la información del documento
            noticia.setTitle(doc.get("Title"));
            noticia.setDate(Integer.parseInt(doc.get("Date")));
            noticia.setText(doc.get("Text"));
            noticias.add(noticia);
        }    
        return noticias;     
    }
    
    
    public ArrayList<Noticia> consultaRango(Integer rangoA, Integer rangoB) throws IOException
    {       
        FSDirectory directory = FSDirectory.open(new File(Searcher.indexLocation));
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);        
        TopDocs hits;
        Sort sort;
        ArrayList<Noticia> noticias = new ArrayList();
        
        Query q = NumericRangeQuery.newIntRange("Date", rangoA, rangoB, true, true);
          
        sort = new Sort();
        sort.setSort(SortField.FIELD_DOC);
        hits = isearcher.search(q, max, sort);
        
        ScoreDoc[] arrayResultados = hits.scoreDocs; 
        
        String cabecera=("Hemos obtenido "+ hits.totalHits + " resultados : \n");
        String cuerpo="Cuerpo de la salida";
        
        for (int i = 0; i < arrayResultados.length; i++) {
            Document doc = isearcher.doc(arrayResultados[i].doc);
             
             Noticia noticia = new Noticia();
             // Añadimos la información del documento
             noticia.setTitle(doc.get("Title"));
             noticia.setDate(Integer.parseInt(doc.get("Date")));
             noticia.setText(doc.get("Text"));
             noticias.add(noticia);
        }    
         // miInterfaz.setSalida(cabecera, cuerpo);  
         return noticias;
    }
}
