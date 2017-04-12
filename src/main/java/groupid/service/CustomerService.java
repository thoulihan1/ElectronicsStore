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
    @Path("/{id}/setPaymentType/{type}")
    public String setPaymentType(@PathParam("id")String id, @PathParam("type")String type) {
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));
        u.setPaymentType(type);
        UserDAO.updateUser(u);

        return "Payment method set to " + type;
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
    @Path("/{id}/purchase_history")
    public String getOPurchaseHistory(@PathParam("id")String id) {
        Customer u = UserDAO.getCustomerById(Integer.parseInt(id));

        List<OrderHistory> oh = OrderHistoryDAO.getOrderHistoryByCustomer(u);
        Gson gson = new Gson();
        String json = gson.toJson(oh);
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

