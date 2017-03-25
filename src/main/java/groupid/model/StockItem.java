package groupid.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */


@NamedQueries( {
        @NamedQuery(name = "StockItem.getAll", query = "select o from StockItem o"),
        @NamedQuery(name = "StockItem.getById", query = "select o from StockItem o where o.id=:id"),
        @NamedQuery(name = "StockItem.getByManufacturer", query = "select o from StockItem o where o.manufacturer=:manufacturer"),
})

@Entity
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private double price;
    private String category;
    private String imgUrl;
    private int leftInStock;

    @ManyToOne
    public Manufacturer manufacturer;

    public StockItem() {
    }

    public StockItem(String title, double price, String category, String imgUrl, int leftInStock) {

        this.title = title;
        this.price = price;
        this.category = category;
        this.imgUrl = imgUrl;
        this.leftInStock = leftInStock;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLeftInStock() {
        return leftInStock;
    }

    public void setLeftInStock(int leftInStock) {
        this.leftInStock = leftInStock;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
