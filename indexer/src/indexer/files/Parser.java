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
        Parser.noticias= new ArrayList<Noticia>();
    }
    
    /**
    * Método para obtener las noticias parseadas.
    * @return estructura con las noticias y sus elementos
    */
    
    public ArrayList<Noticia> getNoticias()
    {
        return Parser.noticias;
    }
    
    /**
    * Método para parsear noticias y cargarlas en la clase.
    * @param directorio String con el directorio
    */
    
    public void parseNews(String directorio)
    {
        
        //Abrimos el directorio donde tenemos los documentos
        File f = new File(directorio);
        
        ArrayList<String> nombres = new ArrayList<String>();
        
        if (f.exists())
        { 
            File[] ficheros = f.listFiles();
            
            for (File fichero : ficheros) {
                if ((!fichero.getName().equals(".") || !fichero.getName().equals(".."))&&(fichero.getName().contains(".xml"))) 
                {
                    nombres.add(fichero.getName());
                }
            } 
            
            nombres.forEach((String nombre) -> {
                try
                {	
                    File inputFile = new File("./sencillo/"+nombre);

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
                        
                        if (nNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element eElement = (Element) nNode;

                            noticia.setDate(Integer.parseInt(eElement.getElementsByTagName("DATE")
                                    .item(0)
                                    .getTextContent()));
                           
                            noticia.setTitle(eElement.getElementsByTagName("TITLE")
                                    .item(0)
                                    .getTextContent());
                            
                            noticia.setText(eElement.getElementsByTagName("TEXT")
                                    .item(0)
                                    .getTextContent());
                        }
                        
                        Parser.noticias.add(noticia);
                    }
                }
                catch (IOException | ParserConfigurationException | DOMException | SAXException e) 
                {

                }
            });
        }
        else 
        {
            //Directorio no existe 
            System.err.println("Directorio no encontrado.");
            
        }
    }
}
