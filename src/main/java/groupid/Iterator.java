package groupid;

import groupid.model.CartItem;
import groupid.model.StockItem;

/**
 * Created by Thomas on 4/11/17.
 */
public interface Iterator {
    public boolean hasNext();
    public CartItem next();
}
