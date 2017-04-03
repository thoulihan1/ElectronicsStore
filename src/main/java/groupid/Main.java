package groupid;

import com.google.gson.Gson;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import groupid.dao.*;
import groupid.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */
public class Main {

    public static void main(String[] args){

        Customer customer = UserDAO.getCustomerById(10);

        OrderHistory orderHistory = new OrderHistory();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        orderHistory.setDateTime(dateFormat.format(date));
        orderHistory.setCustomer(customer);
        orderHistory.setPaymentMethod("Debit");



        List<OrderItem> orderItems = orderHistory.getOrderItems();


        OrderItem orderItem = new OrderItem();

        orderItem.setStockItem(StockItemDAO.getStockItemById(3));
        orderItem.setQuantity(1);
        OrderItemDAO.addOrderItem(orderItem);

        orderItems.add(orderItem);

        OrderHistoryDAO.addOrderHistory(orderHistory);


        System.out.println(orderItems.size());



       // OrderHistory h = OrderHistoryDAO.getOrderHistoryById(14);
        //System.out.println(h.getOrderItems().size());
    }
}
