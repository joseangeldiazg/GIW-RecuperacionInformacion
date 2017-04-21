/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.util.Vector;

/**
 *
 * @author joseadiazg
 */

public class MiXMLHandler extends DefaultHandler
{

    //vector de instancias
    private Vector instancias;
    //"Pagina" que se esta procesando
    private Noticia actual;
    //valor contenido entre las etiquetas de un elemento
    private String valor;

    public MiXMLHandler (Vector v)
    {
        instancias = v;
    }

    /*
    localName: contiene el nombre de la etiqueta.
    att: de la clase "org.xml.sax.Attributes", es una tabla que contiene
    los atributos contenidos en la etiqueta.
    */

    public void startElement( String namespaceURI, String localName, String qName, Attributes attr ) 
    {
        //comprobamos si empezamos un elemento "pagina"
        if (localName.equals("DOC"))
        {
            //creamos una nueva pagina y la añadimos al vector
            actual = new Noticia ();
            instancias.addElement (actual);
        }
    }


    public void endElement (String namespaceURI, String localName, String rawName)
    {
        /*
        miramos de que elemento se trata y asignamos los atributos
        correspondientes a la "Pagina" actual segun el contenido del elemento
        recogido en "valor" por el metodo characters.
        */
        if (localName.equals("DATE"))
        {
            actual.setDate(valor);
        }
        else if (localName.equals("TITLE"))
        {
            actual.setTitle(valor);
        }
        else if(localName.equals("TEXT"))
        {
            actual.setTitle(valor);
        }
    }


    /*
    Los parametros que recibe es la localizacion de los carateres del elemento.
    */

    public void characters (char[] ch, int start, int end) 
    {
        //creamos un String con los caracteres del elemento y le quitamos
        //los espacios en blanco que pueda tener en los extremos.
        valor = new String (ch, start, end);
        valor = valor.trim();
    }

}