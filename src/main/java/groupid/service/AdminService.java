package groupid.service;

import com.google.gson.Gson;
import groupid.UserFactory;
import groupid.dao.UserDAO;
import groupid.model.Admin;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
