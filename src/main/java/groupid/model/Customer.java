package groupid.model;

import groupid.dao.CartDAO;

import javax.persistence.*;

/**
 * Created by Thomas on 3/31/17.
*/



@NamedQueries( {
        @NamedQuery(name = "Customer.getAll", query = "select o from Customer o"),
        @NamedQuery(name = "Customer.getById", query = "select o from Customer o where o.id=:id"),
        @NamedQuery(name = "Customer.getByEmailAndPassword", query = "select o from Customer o where o.email=:email AND o.password=:password")
})


@Entity
public class Customer extends User{


    @OneToOne
    private Cart cart;

    public Customer() {
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart c) {
        this.cart = c;
    }
}
