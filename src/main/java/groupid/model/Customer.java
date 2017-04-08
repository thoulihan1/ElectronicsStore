package groupid.model;

import groupid.dao.CartDAO;
import groupid.observer.Observer;
import groupid.observer.Topic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Thomas on 3/31/17.
*/



@NamedQueries( {
        @NamedQuery(name = "Customer.getAll", query = "select o from Customer o"),
        @NamedQuery(name = "Customer.getById", query = "select o from Customer o where o.id=:id"),
        @NamedQuery(name = "Customer.getByEmailAndPassword", query = "select o from Customer o where o.email=:email AND o.password=:password"),
        @NamedQuery(name = "Customer.getBySubscribed", query = "select o from Customer o where o.isSubscribed=:isSubscribed")
})


@Entity
public class Customer extends User implements Observer {

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

    private boolean isSubscribed;

    @Transient
    private Topic topic;

    public void setTopic(Topic topic) {
        this.topic = topic;
        topic.register(this);
    }

    public void update(StockItem stockItem) {
        //Send email or text
        System.out.print("\nHi " + this.getName() + ", " + stockItem.getTitle() + " has now been added to our catalog!");
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}
