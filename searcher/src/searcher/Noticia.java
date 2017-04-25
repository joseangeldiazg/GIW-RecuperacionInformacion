/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher;

/**
 *
 * @author joseadiazg
 * 
 */
public class Noticia {
    
    //Como en Json el nombre va en mayusculas es necesario declarar asi el tipo
    private String Title = "";
    
    private String Text = "";
    
    private int Date  = 0;
    
    

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
    
    public String getText() {
        return Text;
    }

    public void setText(String text) {
        this.Text = text;
    }
    
    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        this.Date = date;
    }
    
    
    @Override
    public String toString() {
        return "<DOC>"
                    +"\t<DATE>" + Date + "</DATE> \n"
                    +"\t <TITLE>" + Title +"</TITLE> \n"
                    +"\t <TEXT>"+Text+"</TEXT>\n"
              + "</DOC>";
    }     
}
