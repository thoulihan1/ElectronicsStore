package groupid;

import groupid.model.Admin;
import groupid.model.Customer;
import groupid.model.User;

/**
 * Created by Thomas on 3/31/17.
 */
public class UserFactory {

    public User createUser(String type){
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("Admin")){
            return new Admin();

        } else if(type.equalsIgnoreCase("Customer")) {
            return new Customer();
        }

        return null;
    }
}
