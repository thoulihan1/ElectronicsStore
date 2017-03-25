package groupid;

import com.google.gson.Gson;
import groupid.dao.*;
import groupid.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/store")
public class Hello {

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json){

        Gson gson = new Gson();
        User newUser = gson.fromJson(json, User.class);
        Cart cart = new Cart();
        CartDAO.addCart(cart);
        newUser.setCart(cart);

        UserDAO.addUser(newUser);
        return Response.status(200).build();
    }

    @GET
    @Path("/login")
    public String login(@QueryParam("username")String username, @QueryParam("password")String password) {
        User u;
        Gson gson = new Gson();
        try {
             u = UserDAO.getUserByUsernameAndPassword(username, password);

        } catch(Exception e){
            return null;
        }

        String json = gson.toJson(u);
        return json;
    }

    @GET
    @Path("/products")
    public String getAllProducts() {
        List<StockItem> allStockItems = StockItemDAO.getAllStockItems();

        Gson gson = new Gson();

        String json = gson.toJson(allStockItems);
        return json;
    }

    @GET
    @Path("/manufacturers")
    public String getManufacturers() {
        List<Manufacturer> allManufacturers = ManufacturerDAO.getAllManufacturers();
        Gson gson = new Gson();

        String json = gson.toJson(allManufacturers);
        return json;
    }

    @GET
    @Path("/manufacturers/{id}")
    public String getManufacturerById(@PathParam("id")String id) {
        Manufacturer m = ManufacturerDAO.getManufacturerById(id);

        List<StockItem> stockItems = StockItemDAO.getStockItemByManufacturer(m);
        Gson gson = new Gson();

        String json = gson.toJson(stockItems);
        return json;
    }


    @GET
    @Path("/users/{id}/cart")
    public String getUsersCart(@PathParam("id")String id) {
        User u = UserDAO.getUserById(Integer.parseInt(id));
        Cart cart = u.getCart();
        List<CartItem> cartItems = CartItemDAO.getCartItemsByCart(cart);
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        return json;
    }


    @POST
    @Path("/manufacturers")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createManufacturer(String json){

        Gson gson = new Gson();
        Manufacturer newManufacturer = gson.fromJson(json, Manufacturer.class);

        ManufacturerDAO.addManufacturer(newManufacturer);
        return Response.status(200).build();
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(String json){

        Gson gson = new Gson();
        StockItem newStockItem = gson.fromJson(json, StockItem.class);

        Manufacturer m = ManufacturerDAO.getManufacturerById(newStockItem.getManufacturer().getId()+"");
        newStockItem.setManufacturer(m);

        StockItemDAO.addStockItem(newStockItem);
        return Response.status(200).build();
    }


    @POST
    @Path("/products/{id}/add_to_cart")
    public Response addItemToCart(@PathParam("id")String id, @QueryParam("userId")String userId, @QueryParam("quantity")int quantity){

        User u = UserDAO.getUserById(Integer.parseInt(userId));

        StockItem item = StockItemDAO.getStockItemById(Integer.parseInt(id));

        u.getCart().setTotalPrice(u.getCart().getTotalPrice()+(quantity*item.getPrice()));
        CartDAO.updateCart(u.getCart());
        CartItem newCartItem = new CartItem(u.getCart(), item, quantity);
        CartItemDAO.addCartItem(newCartItem);

        return Response.status(200).build();
    }

    @POST
    @Path("users/{id}/clear_cart")
    public Response clearCart(@PathParam("id")String id){
        User u = UserDAO.getUserById(Integer.parseInt(id));
        Cart c = u.getCart();


        List<CartItem> cartItems = CartItemDAO.getCartItemsByCart(c);
        for(CartItem cartItem : cartItems)
            CartItemDAO.removeCartItem(cartItem);
        cartItems.clear();
        c.setTotalPrice(0);
        CartDAO.updateCart(c);
        UserDAO.updateUser(u);

        return Response.status(200).build();
    }
}
