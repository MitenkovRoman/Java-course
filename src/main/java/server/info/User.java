package server.info;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@IdClass(UserPrKey.class)
public class User implements Serializable{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Id
    @NotNull
    private String name;

    @NotNull
    private String password;
    @NotNull private Timestamp regdate;
    private String email;

    public User() {}

    public User(@NotNull String _name, @NotNull String _password, @NotNull String _email){
        this.name = _name;
        this.email = _email;
        this.regdate = new Timestamp(System.currentTimeMillis());
        this.password = _password;
    }

    public User(@NotNull String _name, @NotNull String _password){
        this.name = _name;
        this.email = null;
        this.regdate = new Timestamp(System.currentTimeMillis());
        this.password = _password;
    }

    @Override
    public String toString(){
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", registration date" + regdate +
                ", email" + email +
                '}';
    }

    public String getName(){
        return name;
    }
    public void setName(@NotNull String _name){
        name = _name;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public Timestamp getRegistrationdate() {
       return (Timestamp) regdate.clone();
   }

    public String getEmail() {
        return email;
    }
    public void setEmail(@NotNull String email) {
        this.email = email;
    }
}