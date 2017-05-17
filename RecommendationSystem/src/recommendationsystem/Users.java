
package recommendationsystem;

/**
 * Clase para definir y mantener los 
 * usuarios y sus datos asociados. 
 * 
 * 
 * @author joseadiazg
 * @version 1.0
 * @since 2017-05-17
 */
public class Users {
    
    private int user_id;
    private String sex;
    private int age;
    private float avg_rating;

    public Users(int user_id, int age, String sex) {
        this.user_id = user_id;
        this.sex = sex;
        this.age = age;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(float avg_rating) {
        this.avg_rating = avg_rating;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "user_id=" + user_id + ", sex=" + sex + ", age=" + age + ", avg_rating=" + avg_rating + '}';
    } 
}
