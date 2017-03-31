package groupid;

import com.google.gson.Gson;
import groupid.dao.*;
import groupid.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/store")
public class Hello {
    Gson gson = new Gson();


    @GET
    @Path("/login")
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        User u = UserDAO.getUserByEmailAndPassword(email, password);
        if (u != null) {
            String json = gson.toJson(u);
            return Response.status(200).entity(json).build();
        } else {
            return Response.status(401).build();
        }
    }
}

       /*
    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json){

        Gson gson = new Gson();
        User newUser = gson.fromJson(json, User.class);
        Cart cart = new Cart();
        CartDAO.addCart(cart);
        newUser.setCart(cart);

        UserDAO.addUser(newUser);
        return Response.status(200).build();
    }





    @GET
    @Path("/manufacturers")
    public String getManufacturers() {
        List<Manufacturer> allManufacturers = ManufacturerDAO.getAllManufacturers();
        Gson gson = new Gson();

        String json = gson.toJson(allManufacturers);
        return json;
    }







    @POST
    @Path("/manufacturers")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createManufacturer(String json){

        Gson gson = new Gson();
        Manufacturer newManufacturer = gson.fromJson(json, Manufacturer.class);

        ManufacturerDAO.addManufacturer(newManufacturer);
        return Response.status(200).build();
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(String json){

        Gson gson = new Gson();
        StockItem newStockItem = gson.fromJson(json, StockItem.class);

        Manufacturer m = ManufacturerDAO.getManufacturerById(newStockItem.getManufacturer().getId()+"");
        newStockItem.setManufacturer(m);

        StockItemDAO.addStockItem(newStockItem);
        return Response.status(200).build();
    }




    */

