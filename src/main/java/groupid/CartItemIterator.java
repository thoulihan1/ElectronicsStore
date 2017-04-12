package groupid;


import groupid.model.CartItem;
import groupid.model.StockItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 4/11/17.
 */
public class CartItemIterator implements Iterator{

    List<CartItem> cartItems;

    int position = 0;

    public CartItemIterator(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }

    public boolean hasNext() {
        if(position >= cartItems.size()){
            return false;
        } else return true;
    }

    public CartItem next() {
        CartItem item = cartItems.get(position);
        position++;
        return item;
    }
}
