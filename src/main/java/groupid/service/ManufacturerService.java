package groupid.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import groupid.dao.ManufacturerDAO;
import groupid.dao.StockItemDAO;
import groupid.model.Manufacturer;
import groupid.model.StockItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Thomas on 3/31/17.
 */

@Path("/manufacturers")
public class ManufacturerService {


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createManufacturer(String json){

        Gson gson = new Gson();
        Manufacturer newManufacturer = gson.fromJson(json, Manufacturer.class);

        ManufacturerDAO.addManufacturer(newManufacturer);

        return Response.status(200).build();
    }

    @GET
    @Path("/all")
    public String getManufacturers() {
        List<Manufacturer> allManufacturers = ManufacturerDAO.getAllManufacturers();
        Gson gson = new Gson();

        String json = gson.toJson(allManufacturers);
        return json;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getMessage(@PathParam("id")String id) {
        Manufacturer m = ManufacturerDAO.getManufacturerById(id);
        Gson gson = new Gson();
        String s = gson.toJson(m);
        return s;
    }

    @GET
    @Path("/{id}/products/all")
    @Produces("application/json")
    public String getManufacturersProducts(@PathParam("id")String id) {
        Manufacturer m = ManufacturerDAO.getManufacturerById(id);
        List<StockItem> stockItems = StockItemDAO.getStockItemByManufacturer(m);
        Gson gson = new Gson();
        String s = gson.toJson(stockItems);
        return s;
    }
}
