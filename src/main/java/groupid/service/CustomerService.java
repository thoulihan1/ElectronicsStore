package groupid.service;


import com.google.gson.Gson;
import groupid.UserFactory;
import groupid.dao.CartDAO;
import groupid.dao.CartItemDAO;
import groupid.dao.StockItemDAO;
import groupid.dao.UserDAO;
import groupid.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/customers")
public class CustomerService {
    Gson gson = new Gson();
    UserFactory userFactory = new UserFactory();

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAdmin(String json) {
        Customer newUser = gson.fromJson(json, Customer.class);

        Cart cart = new Cart();
        CartDAO.addCart(cart);
        newUser.setCart(cart);

        UserDAO.addUser(newUser);
        return Response.status(200).build();
    }

    @GET
    @Path("/{id}/cart")
    public String getUsersCart(@PathParam("id")String id) {
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));
        Cart cart = u.getCart();
        List<CartItem> cartItems = CartItemDAO.getCartItemsByCart(cart);
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        return json;
    }

    @POST
    @Path("/{id}/removeItemFromCart")
    public Response removeItemFromCart(@PathParam("id")String id, @QueryParam("itemId")String itemId){
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));
        Cart c = u.getCart();
        CartItem cartItem = CartItemDAO.getCartItemById(Integer.parseInt(itemId));
        CartItemDAO.removeCartItem(cartItem);
        c.setTotalPrice(c.getTotalPrice()-(cartItem.getStockITem().getPrice()*cartItem.getQuantity()));
        CartDAO.updateCart(c);
        return Response.status(200).build();
    }



    @POST
    @Path("/{id}/checkout")
    public Response checkout(@PathParam("id")String id, @QueryParam("paymentType")String paymentType) {
        Customer customer = UserDAO.getCustomerById(Integer.parseInt(id));

        Cart cart = customer.getCart();
        List<CartItem> cartItems = CartItemDAO.getCartItemsByCart(cart);
        for(CartItem item:cartItems){
            StockItem stockItem = item.getStockITem();
            stockItem.setLeftInStock(stockItem.getLeftInStock()-item.getQuantity());
            StockItemDAO.updateStockItem(stockItem);
        }

        for(CartItem cartItem : cartItems)
            CartItemDAO.removeCartItem(cartItem);
        cartItems.clear();
        cart.setTotalPrice(0);
        CartDAO.updateCart(cart);
        UserDAO.updateUser(customer);

        return Response.status(200).build();
    }


    @POST
    @Path("/{id}/clear_cart")
    public Response clearCart(@PathParam("id")String id){
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));
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


    /*
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
    */

