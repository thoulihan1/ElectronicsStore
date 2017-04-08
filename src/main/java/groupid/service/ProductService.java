package groupid.service;

import com.google.gson.Gson;
import groupid.dao.*;
import groupid.model.*;
import groupid.observer.Topic;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Thomas on 3/31/17.
 */

@Path("/products")
public class ProductService {
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(String json){

        Gson gson = new Gson();
        StockItem newStockItem = gson.fromJson(json, StockItem.class);


        Manufacturer m = ManufacturerDAO.getManufacturerById(newStockItem.getManufacturer().getId()+"");
        newStockItem.setManufacturer(m);

        Topic topic = new Topic();
        topic.setStockItem(newStockItem);

        StockItemDAO.addStockItem(newStockItem);

        return Response.status(200).build();
    }

    @GET
    @Path("/all")
    public String getAllProducts() {
        List<StockItem> allStockItems = StockItemDAO.getAllStockItems();

        Gson gson = new Gson();

        String json = gson.toJson(allStockItems);
        return json;
    }

    @POST
    @Path("/{id}/add_to_cart")
    public Response addItemToCart(@PathParam("id")String id, @QueryParam("userId")String userId, @QueryParam("quantity")int quantity){

        Customer c = UserDAO.getCustomerById(Integer.parseInt(userId));

        StockItem item = StockItemDAO.getStockItemById(Integer.parseInt(id));

        c.getCart().setTotalPrice(c.getCart().getTotalPrice()+(quantity*item.getPrice()));
        CartDAO.updateCart(c.getCart());
        CartItem newCartItem = new CartItem(c.getCart(), item, quantity);
        CartItemDAO.addCartItem(newCartItem);

        return Response.status(200).build();
    }

    @POST
    @Path("/{id}/updateQuantity")
    public Response updateQuantity(@PathParam("id")String id, @QueryParam("quantity")String quantity){
        StockItem item = StockItemDAO.getStockItemById(Integer.parseInt(id));
        item.setLeftInStock(Integer.parseInt(quantity));
        StockItemDAO.updateStockItem(item);

        return Response.status(200).build();
    }



}
