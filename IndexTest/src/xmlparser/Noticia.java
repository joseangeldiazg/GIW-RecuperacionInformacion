
package xmlparser;

/**
 *
 * @author joseadiazg
 */

public class Noticia {
    
    //Como en Json el nombre va en mayusculas es necesario declarar asi el tipo
    private String Title = "";
    
    private String Text = "";
    
    private String Date  = "";
    
    

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
    
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }
    
    
    @Override
    public String toString() {
        return "<DOC>"
                    +"\t<DATE>" + this.Date + "</DATE> \n"
                    +"\t <TITLE>" + this.Title +"</TITLE> \n"
                    +"\t <TEXT>"+this.Text+"</TEXT>\n"
              + "</DOC>";
    }     
}
