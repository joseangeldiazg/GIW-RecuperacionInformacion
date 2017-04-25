/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer.files;

import indexer.data.Noticia;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author joseadiazg
 */
public class Parser 
{
    
    private static  ArrayList<Noticia> noticias;
    
    
    /**
     * 
     * Constructor
     * 
     */
    
    public Parser()
    {
        this.noticias= new ArrayList<Noticia>();
    }
    
    /**
    * Método para obtener las noticias parseadas.
    * @return estructura con las noticias y sus elementos
    */
    
    public ArrayList<Noticia> getNoticias()
    {
        return this.noticias;
    }
    
    /**
    * Método para parsear noticias y cargarlas en la clase.
    * @param directorio String con el directorio
    * @return Array de peliculas
    */
    
    public void parseNews(String directorio)
    {
        try {	
         File inputFile = new File(directorio);
         
         DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         
         //Obtenemos los documentos
         
         NodeList nList = doc.getElementsByTagName("DOC");
         
         for (int temp = 0; temp < nList.getLength(); temp++) 
         {
            Noticia noticia = new Noticia(); 
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;

               noticia.setDate(eElement.getElementsByTagName("DATE")
                  .item(0)
                  .getTextContent());
               
               noticia.setTitle(eElement.getElementsByTagName("TITLE")
                  .item(0)
                  .getTextContent());  
               
               noticia.setText(eElement.getElementsByTagName("TEXT")
                  .item(0)
                  .getTextContent());  
            }
            
            this.noticias.add(noticia);
         }         
      } 
      catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
      }
    }
}
