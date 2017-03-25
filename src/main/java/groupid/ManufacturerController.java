package groupid;

/**
 * Created by Thomas on 3/13/17.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import groupid.dao.ManufacturerDAO;
import groupid.dao.StockItemDAO;
import groupid.model.Manufacturer;
import groupid.model.StockItem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/manufacturer")
public class ManufacturerController {

    Gson gson = new GsonBuilder().create();

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getMessage(@PathParam("id")String id) {
        Manufacturer m = ManufacturerDAO.getManufacturerById(id);

        String s = gson.toJson(m);
        return s;
    }
}
