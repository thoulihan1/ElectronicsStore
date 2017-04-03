package groupid.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 4/3/17.
 */

@NamedQueries( {
        @NamedQuery(name = "OrderHistory.getAll", query = "select o from OrderHistory o"),
        @NamedQuery(name = "OrderHistory.getById", query = "select o from OrderHistory o where o.id=:id"),
        @NamedQuery(name = "OrderHistory.getByCustomer", query = "select o from OrderHistory o where o.customer=:customer"),
})
@Entity
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dateTime;

    private double price;
    private String paymentMethod;


    //Change this
    @OneToMany
    @JoinColumn(name = "orderHistory")
    public List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @ManyToOne
    public Customer customer;


    public OrderHistory() {
    }

    public OrderHistory(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
