package groupid.observer;

import groupid.model.StockItem;

/**
 * Created by Thomas on 4/8/17.
 */
public interface Observer {
    void update(StockItem stockItem);
    void setTopic(Topic topic);
}
