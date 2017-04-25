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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
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
    private static String indexLocation = "indexDir/";
    
    public ArrayList<Noticia> busquedaTexto(String cadena, String ordenacion) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
        
        ArrayList<Noticia> noticias = new ArrayList();
        FSDirectory directory = FSDirectory.open(new File(Searcher.indexLocation));
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);   
        Sort sort;
        TopDocs hits;
        
            
        QueryParser parser = new QueryParser(Version.LUCENE_43, "TEXT", analyzer);
        Query consulta = parser.parse(cadena);
        
        if(ordenacion.equals("OrdenacionTitulo"))
        {   
            sort = new Sort();
            sort.setSort(SortField.FIELD_DOC);
            hits = isearcher.search(consulta, max, sort);
        }
        else
        {
            hits = isearcher.search(consulta, max);
        }
        //hits = isearcher.search(consulta, max);
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
    
    public ArrayList<Noticia> consultaBooleana(ArrayList <String> campo, ArrayList <String> cadena, boolean or, boolean and) throws IOException, org.apache.lucene.queryparser.classic.ParseException
    {     
        ArrayList<Noticia> noticias =new ArrayList();
        DirectoryReader ireader=null;
        FSDirectory directory = FSDirectory.open(new File(this.indexLocation));
        
        
        try {
            ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            // Analiza la consulta del  usuario
            BooleanQuery booleanQuery = new BooleanQuery();
            // Vemos que operador se ha pasado por parámetro
            Occur operator = null;
            if(and) {
                operator = BooleanClause.Occur.MUST;
            } else {
                operator = BooleanClause.Occur.SHOULD;
            }
            // Comprobamos si se ha especificado el titulo de la noticia
            if(campo.get(0)!= null && !campo.get(0).equals("")) 
            {
                QueryParser parser1 = new QueryParser(Version.LUCENE_43, "TITLE", analyzer);
                Query query1 = parser1.parse(cadena.get(0));
                booleanQuery.add(query1, operator);
            }
            // Comprobamos si se ha alguna palabra del texto
            if(campo.get(1)!= null && !campo.get(1).equals("")) {
                QueryParser parser2 = new QueryParser(Version.LUCENE_43, "TEXT", analyzer);
                Query query2 = parser2.parse(cadena.get(1));
                booleanQuery.add(query2, operator);
            }
            // Realizamos la búsqueda
            ScoreDoc[] hits = isearcher.search(booleanQuery, null, 1000).scoreDocs;
            
            // Para cada resultado de la búsqueda
            for (ScoreDoc hit : hits) {
                Document doc = isearcher.doc(hit.doc);  
                Noticia noticia = new Noticia();
                // Añadimos la información del documento
                noticia.setTitle(doc.get("Title"));
                noticia.setDate(Integer.parseInt(doc.get("Date")));
                noticia.setText(doc.get("Text"));
                noticias.add(noticia);
            }   
           
        }catch (IOException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        ireader.close();
        return noticias; 
    } 
    
    public ArrayList<Noticia> consultaRango(Integer rangoA, Integer rangoB, String ordenacion) throws IOException
    {       
        FSDirectory directory = FSDirectory.open(new File(Searcher.indexLocation));
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);        
        TopDocs hits;
        Sort sort;
        ArrayList<Noticia> noticias = new ArrayList();
        
        Query q = NumericRangeQuery.newIntRange("Date", rangoA, rangoB, true, true);
          
        if(ordenacion.equals("OrdenacionTitulo"))
        {   
            sort = new Sort();
            sort.setSort(SortField.FIELD_DOC);
            hits = isearcher.search(q, max, sort);
        }
        else
        {
            hits = isearcher.search(q, max);
        }
        
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
