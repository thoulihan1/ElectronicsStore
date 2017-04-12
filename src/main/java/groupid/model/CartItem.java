package groupid.model;

import javax.persistence.*;

/**
 * Created by Thomas on 3/14/17.
 */

@NamedQueries( {
        @NamedQuery(name = "CartItem.getAll", query = "select o from CartItem o"),
        @NamedQuery(name = "CartItem.getById", query = "select o from CartItem o where o.id=:id"),
        @NamedQuery(name = "CartItem.getByCart", query = "select o from CartItem o where o.cart=:cart"),
        @NamedQuery(name = "CartItem.getCartItemByCartAndStockItem", query = "select o from CartItem o where o.cart=:cart and o.stockItem=:stockItem"),
})

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private StockItem stockItem;

    public CartItem() {
    }

    public CartItem(Cart cart, StockItem stockItem, int quantity) {
        this.cart = cart;
        this.stockItem = stockItem;
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public StockItem getStockITem() {
        return stockItem;
    }

    public void setStockITem(StockItem stockITem) {
        this.stockItem = stockITem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}