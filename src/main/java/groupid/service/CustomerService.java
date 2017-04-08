package groupid.service;


import com.google.gson.Gson;
import groupid.UserFactory;
import groupid.dao.*;
import groupid.model.*;
import groupid.observer.Topic;
import groupid.strategy.DiscountStrategy;
import groupid.strategy.FirstTimeDiscountStrategy;
import groupid.strategy.Over2000DiscountStrategy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        Topic topic = new Topic();
        newUser.setTopic(topic);
        return Response.status(200).build();
    }


    @GET
    @Path("/{id}/unsubscribe")
    public String unsub(@PathParam("id")String id) {
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));


        Topic topic = new Topic();
        topic.unregister(u);

        return "Unsubscribed";
    }

    @GET
    @Path("/{id}/subscribe")
    public String sub(@PathParam("id")String id) {
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));


        Topic topic = new Topic();
        u.setTopic(topic);

        return "Subscribed";
    }

    @GET
    @Path("/{id}")
    public String getUser(@PathParam("id")String id) {
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));


        Gson gson = new Gson();
        String json = gson.toJson(u);
        return json;
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



        List<OrderHistory> purchaseHistory = OrderHistoryDAO.getOrderHistoryByCustomer(customer);
        System.out.println("Previous purchases: " + purchaseHistory.size());

        System.out.println("Cart price before discount: " +cart.getTotalPrice());

        DiscountStrategy firstTimeDiscountStrategy= new FirstTimeDiscountStrategy();
        DiscountStrategy over2000DiscountStrategy = new Over2000DiscountStrategy();
        double discount = 0.0;

        if(purchaseHistory.size()==0){
            System.out.println("\nFirst purchase. applying 10% discount");
            cart.setDiscountStrategy(firstTimeDiscountStrategy);
            discount = cart.applyDiscount();
        }

        if(cart.getTotalPrice()>=2000.0){
            System.out.println("\nOver $2000. applying 15% discount");
            cart.setDiscountStrategy(over2000DiscountStrategy);
            discount = cart.applyDiscount();
        }
        System.out.println("Discount: " + discount);

        cart.setTotalPrice(cart.getTotalPrice()-discount);
        System.out.println("New Cart Price: " + cart.getTotalPrice());

        CartDAO.updateCart(cart);

        OrderHistory orderHistory = new OrderHistory();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        orderHistory.setDateTime(dateFormat.format(date));
        orderHistory.setCustomer(customer);
        orderHistory.setPaymentMethod(paymentType);
        orderHistory.setPrice(cart.getTotalPrice());
        List<OrderItem> orderItems = orderHistory.getOrderItems();


        List<CartItem> cartItems = CartItemDAO.getCartItemsByCart(cart);
        for(CartItem item:cartItems){
            StockItem stockItem = item.getStockITem();
            stockItem.setLeftInStock(stockItem.getLeftInStock()-item.getQuantity());
            StockItemDAO.updateStockItem(stockItem);

            OrderItem orderItem = new OrderItem();
            orderItem.setStockItem(item.getStockITem());
            orderItem.setQuantity(item.getQuantity());
            OrderItemDAO.addOrderItem(orderItem);

            orderItems.add(orderItem);
        }

        OrderHistoryDAO.addOrderHistory(orderHistory);


        for(CartItem cartItem : cartItems)
            CartItemDAO.removeCartItem(cartItem);
        cartItems.clear();
        cart.setTotalPrice(0);
        cart.setDiscountStrategy(null);
        CartDAO.updateCart(cart);
        UserDAO.updateUser(customer);

        String json = gson.toJson(orderHistory);

        return Response.status(200).entity(json).build();
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

