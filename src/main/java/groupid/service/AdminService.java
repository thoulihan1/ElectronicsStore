package groupid.service;

import com.google.gson.Gson;
import groupid.UserFactory;
import groupid.dao.OrderHistoryDAO;
import groupid.dao.UserDAO;
import groupid.model.Admin;
import groupid.model.OrderHistory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Thomas on 3/31/17.
 */

@Path("/admins")
public class AdminService{
    Gson gson = new Gson();

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCustomer(String json){
        Admin newUser = gson.fromJson(json, Admin.class);
        UserDAO.addUser(newUser);
        return Response.status(200).build();
    }

    @GET
    @Path("/order_history")
    @Produces("application/json")
    public String getOrderHistory( ){
        List<OrderHistory> allOrderHistories = OrderHistoryDAO.getAllOrderHistories();


        Gson gson = new Gson();
        String json = gson.toJson(allOrderHistories);

        System.out.println(json);
        return json;

    }


}
