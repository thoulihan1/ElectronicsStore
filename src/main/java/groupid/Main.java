package groupid;

import groupid.dao.*;
import groupid.model.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */
public class Main {
/*
    public static void main(String[] args){
        Customer c = UserDAO.getCustomerById(Integer.parseInt("9"));

        StockItem item = StockItemDAO.getStockItemById(Integer.parseInt("2"));

        int quantity = 1;

        System.out.println(String.valueOf(isProductInCart(item, c)));

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

*/



}

