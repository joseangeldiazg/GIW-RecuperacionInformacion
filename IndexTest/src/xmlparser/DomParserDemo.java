/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author joseadiazg
 */

public class DomParserDemo 
{
   
    public static  ArrayList<Noticia> noticias = new ArrayList<Noticia>();
    
    public static void main(String[] args){

      try {	
         File inputFile = new File("./data/sencillo.xml");
         
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
            
            noticia.toString();
         }
      } 
      catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
      }
   }
}