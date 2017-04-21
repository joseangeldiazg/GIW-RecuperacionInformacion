/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import java.util.Vector;
import org.xml.sax.XMLReader;

/**
 *
 * @author joseadiazg
 */
public class IndexTest {

    /**
     * @param args the command line arguments
     */
    
    public static Vector instancias = new Vector ();
    
    public IndexTest()
    {
    }

    public void procesarFichero ()
    {
        try
        {
            /*
            puede paracer un lio mezclar "SAXParser" y "XMLReader", pero solo es
            un problema de nomenclatura entre versiones. Se usa un SAXParser,
            que implementa el interface XMLReader (todos los parsers de XML lo
            hacen), porque en la version 1 de SAX ya se uso el termino "parser",
            y se mantiene por convencion.
            */
            XMLReader parser = new SAXParser();
            //añadimos al parser nuestro "ContentHandler", pasandole el vector de instancias.
            parser.setContentHandler(new MiXMLHandler(instancias));
            //y le decimos que empieze a procesar nuestro fichero de favoritos
            parser.parse("./data/sencillo.xml");
        }
        catch (Exception e)
        {
            System.out.println ("Error al procesar el fichero de favoritos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    
    public static void main(String[] args) 
    {
        IndexTest test = new IndexTest();

        test.procesarFichero();  
        
        System.out.print(instancias.size());
        
        for(int i=0; i<instancias.size();i++)
        {
            System.out.print(instancias.get(i).toString());
        }
    }
    
}
