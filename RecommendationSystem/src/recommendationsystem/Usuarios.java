/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendationsystem;

/**
 *
 * @author joseadiazg
 */
public class Usuarios {
    
    private int user_id;
    private String sex;
    private int age;

    public Usuarios(int user_id, String sex, int age) {
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

    @Override
    public String toString() {
        return "Usuarios{" + "user_id=" + user_id + ", sex=" + sex + ", age=" + age + '}';
    }   
    
}
