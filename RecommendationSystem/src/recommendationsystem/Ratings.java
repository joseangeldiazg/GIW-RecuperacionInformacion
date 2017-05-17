package recommendationsystem;

/**
 * Clase para definir y mantener los ratings de un
 * un usuario con una película dada. 
 * 
 * 
 * @author joseadiazg
 * @version 1.0
 * @since 2017-05-17
 */
public class Ratings {
    
    private int user_id;
    private int item_id;
    private int rating;
    
    public Ratings(int user_id, int item_id, int rating)
    {
        this.user_id=user_id;
        this.item_id=item_id;
        this.rating=rating;  
    }

    public int getUser_id() {
        return user_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getRating() {
        return rating;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Ratings{" + "user_id=" + user_id + ", item_id=" + item_id + ", rating=" + rating + '}';
    } 
}
