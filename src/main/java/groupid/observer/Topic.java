package groupid.observer;

import groupid.dao.UserDAO;
import groupid.model.Customer;
import groupid.model.StockItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 4/8/17.
 */

public class Topic {

    private List<Observer> observers = UserDAO.getCustomerWhoAreSubscribed();
    public StockItem stockItem;

    public void setStockItem(StockItem stockItem){
        this.stockItem = stockItem;
        notifyObservers();
    }

    public void register(Observer obs){
        observers.add(obs);
        Customer c = (Customer) obs;
        c.setSubscribed(true);
        UserDAO.updateUser(c);

        System.out.print("Observer added to list");
    }

    public void unregister(Observer obs){
        observers.remove(obs);
        Customer c = (Customer) obs;
        c.setSubscribed(false);
        UserDAO.updateUser(c);

        System.out.print("Observer removed from list");
    }

    public void notifyObservers(){
        System.out.println("Notifying all " +observers.size() + " observers.");
        for (Observer o: observers){
            o.update(stockItem);
        }
    }
}
