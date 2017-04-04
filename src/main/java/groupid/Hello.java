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
        LogInChain loginAsAdmin = new LoginAsAdmin();
        LogInChain loginAsCustomer = new LoginAsCustomer();

        loginAsAdmin.setNextChain(loginAsCustomer);

        User u = loginAsAdmin.login(email, password);

        if (u != null) {
            String json = gson.toJson(u);
            return Response.status(200).entity(json).build();
        } else {
            return Response.status(401).build();
        }
    }

    public interface LogInChain{
        public void setNextChain(LogInChain nextInChain);
        public User login(String email, String password);
    }


    public static class LoginAsAdmin implements LogInChain{

        private  LogInChain nextInChain;

        public void setNextChain(LogInChain nextChain) {
            nextInChain = nextChain;
        }

        public User login(String email, String password) {
            try{
                User u = UserDAO.getAdminByEmailAndPassword(email, password);
                System.out.println("Logging in as admin");
                return u;
            } catch(Exception e){
                System.out.println("Not an admin, try as customer");
                return nextInChain.login(email, password);
            }
        }
    }

    public static class LoginAsCustomer implements LogInChain{

        private  LogInChain nextInChain;

        public void setNextChain(LogInChain nextChain) {
            nextInChain = nextChain;
        }

        public User login(String email, String password) {
            try{
                User u = UserDAO.getCustomerByEmailAndPassword(email, password);
                System.out.println("Logging in as customer");
                return u;
            } catch(Exception e){
                System.out.println("Not a customer, end of chain");
                return null;
            }
        }
    }
}

