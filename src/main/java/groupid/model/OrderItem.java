package groupid.model;

import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;

import javax.persistence.*;

/**
 * Created by Thomas on 3/14/17.
 */

@NamedQueries( {
        @NamedQuery(name = "OrderItem.getAll", query = "select o from OrderItem o"),
        @NamedQuery(name = "OrderItem.getById", query = "select o from CartItem o where o.id=:id"),
})

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    //@ManyToOne
    //private OrderHistory orderHistory;

    @ManyToOne
    private StockItem stockItem;

    public OrderItem() {
    }

    public OrderItem(StockItem stockItem, int quantity) {
        this.stockItem = stockItem;
        this.quantity = quantity;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
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