package groupid;

import com.google.gson.Gson;
import groupid.dao.*;
import groupid.model.*;

import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */
public class Main {

    public static void main(String[] args){
        User u = UserDAO.getUserById(1);
        Cart cart = u.getCart();
        List<CartItem> cartItems = CartItemDAO.getCartItemsByCart(cart);
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        System.out.println(json);
    }

    public void addUserAndCart(){
        User u = new User();
        u.setName("New USER");
        u.setPassword("NEW PASSWORD");
        UserDAO.addUser(u);
        Cart cart = new Cart();
        u.setCart(cart);
        CartDAO.addCart(cart);
        UserDAO.updateUser(u);

    }
}
