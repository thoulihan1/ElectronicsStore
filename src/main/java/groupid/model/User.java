package groupid.model;

import javax.persistence.*;

/**
 * Created by Thomas o
 *n 3/13/17.
 */

@NamedQueries( {
        @NamedQuery(name = "User.getAll", query = "select o from User o"),
        @NamedQuery(name = "User.getById", query = "select o from User o where o.id=:id"),
        @NamedQuery(name = "User.getByUsernameAndPassword", query = "select o from User o where o.name=:name AND o.password=:password"),
})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String password;

    @OneToOne
    private Cart cart;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
