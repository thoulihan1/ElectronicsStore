package groupid.model;

import javax.persistence.*;

/**
 * Created by Thomas on 4/10/17.
 */

@NamedQueries( {
        @NamedQuery(name = "Review.getAll", query = "select o from Review o"),
        @NamedQuery(name = "Review.getById", query = "select o from Review o where o.id=:id"),
        @NamedQuery(name = "Review.getByStockItem", query = "select o from Review o where o.stockItem=:stockItem")
})

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private StockItem stockItem;

    @ManyToOne
    private Customer customer;

    private String review;

    private int rating;

    public Review() {
    }

    public Review(StockItem stockItem, Customer customer, String review, int rating) {
        this.stockItem = stockItem;
        this.customer = customer;
        this.review = review;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
