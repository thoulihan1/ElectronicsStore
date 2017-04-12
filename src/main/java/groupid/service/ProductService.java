package groupid.service;

import com.google.gson.Gson;
import groupid.CartItemIterator;
import groupid.Iterator;
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

    @GET
    @Path("/{id}/reviews")
    public String getAProductsReviews(@PathParam("id")String id) {

        StockItem stockItem = StockItemDAO.getStockItemById(Integer.parseInt(id));

        List<Review> reviews = ReviewDAO.getReviewsbyStockItem(stockItem);


        Gson gson = new Gson();

        String json = gson.toJson(reviews);
        return json;
    }

    @GET
    @Path("{id}/review")
    public String reviewProduct(@PathParam("id")String id, @QueryParam("customerId")String customerId,
                                @QueryParam("review")String review, @QueryParam("rating")String rating){
        StockItem s = StockItemDAO.getStockItemById(Integer.parseInt(id));

        Review r = new Review();
        r.setStockItem(s);
        r.setReview(review);
        r.setRating(Integer.parseInt(rating));
        r.setCustomer(UserDAO.getCustomerById(Integer.parseInt(customerId)));


        ReviewDAO.addReview(r);
        return s.getTitle() + "rated. Thank you";
    }


    @POST
    @Path("/{id}/add_to_cart")
    public Response addItemToCart(@PathParam("id")String id, @QueryParam("userId")String userId, @QueryParam("quantity")int quantity){

        Customer c = UserDAO.getCustomerById(Integer.parseInt(userId));

        StockItem item = StockItemDAO.getStockItemById(Integer.parseInt(id));


        if(isProductInCart(item, c)){
            CartItem cartItem = CartItemDAO.getCartItemByCartAndStockItem(c.getCart(), item);
            System.out.println(cartItem.getStockITem().getTitle() + " is in the cart");

            c.getCart().setTotalPrice(c.getCart().getTotalPrice()+(quantity*item.getPrice()));
            CartDAO.updateCart(c.getCart());
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            CartItemDAO.updateCartItem(cartItem);
        } else {
            addNewToCart(c, item, quantity);
        }
        return Response.status(200).build();
    }

    public static boolean isProductInCart(StockItem item, Customer c){
        boolean isInCart = false;
        List<CartItem> cartItems = CartItemDAO.getCartItemsByCart(c.getCart());
        Iterator cartItemIterator = new CartItemIterator(cartItems);
        while (cartItemIterator.hasNext()) {
            CartItem current = cartItemIterator.next();
            if (current.getStockITem().getId() == item.getId()) {
                isInCart = true;
            }
        }
        return isInCart;
    }

    public static void addNewToCart(Customer c, StockItem item, int quantity){
        System.out.println("Adding to basket.");
        c.getCart().setTotalPrice(c.getCart().getTotalPrice() + (quantity * item.getPrice()));
        CartDAO.updateCart(c.getCart());
        CartItem newCartItem = new CartItem(c.getCart(), item, quantity);
        CartItemDAO.addCartItem(newCartItem);
        System.out.println(item.getId());
    }





/*
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
*/
    @POST
    @Path("/{id}/updateQuantity")
    public Response updateQuantity(@PathParam("id")String id, @QueryParam("quantity")String quantity){
        StockItem item = StockItemDAO.getStockItemById(Integer.parseInt(id));
        item.setLeftInStock(Integer.parseInt(quantity));
        StockItemDAO.updateStockItem(item);

        return Response.status(200).build();
    }



}
