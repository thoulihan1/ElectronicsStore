package groupid.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */

@NamedQueries( {
        @NamedQuery(name = "Cart.getAll", query = "select o from Cart o"),
        @NamedQuery(name = "Cart.getById", query = "select o from Cart o where o.id=:id"),
})


@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double totalPrice;

    public Cart() {
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
