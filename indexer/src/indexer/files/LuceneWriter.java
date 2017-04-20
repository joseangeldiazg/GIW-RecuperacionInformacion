/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer.files;

import java.io.File;
import java.io.IOException;
import indexer.data.Noticias;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author joseadiazg
 * 
 */
public class LuceneWriter {
    
    String pathToIndex = "";
    IndexWriter indexWriter = null;

    private LuceneWriter() {
    }

    public LuceneWriter(String pathToIndex) {
        this.pathToIndex = pathToIndex;
    }
    
    public boolean openIndex(){
        
        
        try {
            
            //Abrimos el directorio
            Directory dir = FSDirectory.open(new File(pathToIndex));
            
            //Elegimos un Analyzer . Y especificamos la versión de Lucene que usamos
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
        
            //Creamos un IndexWriterConfig 
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer);
            //Siempre vamos a sobreescribir el indice que tenemos en el directorio
            iwc.setOpenMode(OpenMode.CREATE);
            
            indexWriter = new IndexWriter(dir, iwc);
            
            return true;
        } catch (Exception e) {
            System.out.println("Ocurrio un problema abriendo el documento para escritura: " + e.getClass() + " :: " + e.getMessage());
            return false;
        }            
    }
    
    public void addPelicula(Noticias noticia){
        Document doc = new Document();
        doc.add(new StringField("Title", noticia.getTitle(), Field.Store.YES));
        doc.add(new TextField("Plot", noticia.getText(), Field.Store.NO));
        doc.add(new StringField("Date", noticia.getDate(), Field.Store.YES));
        try {
            indexWriter.addDocument(doc);
        } catch (IOException ex) {
            System.out.println("Ocurrio un problema al añadir el documento: " + ex.getClass() + " :: " + ex.getMessage());
        }
        System.out.println("Añadida noticia: "+noticia.getTitle() );
        
    }    
    public void finish(){
        try {
            //Realizamos un commit para guardar
            indexWriter.commit();
            
            //Cerramos el indexwriter
            indexWriter.close();
        } catch (IOException ex) {
            System.out.println("Ocurrio un problema cerrando el indice: " + ex.getClass() + " :: " + ex.getLocalizedMessage());
        }
    }  
}