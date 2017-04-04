package groupid;

import com.google.gson.Gson;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import groupid.dao.*;
import groupid.model.*;

import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */
public class Main {

    public static void main(String[] args) {

        Hello.LogInChain loginAsAdmin = new Hello.LoginAsAdmin();
        Hello.LogInChain loginAsCustomer = new Hello.LoginAsCustomer();

        loginAsAdmin.setNextChain(loginAsCustomer);

Gson gson = new Gson();

        User u = loginAsAdmin.login("t@gmail.com", "pass");

        System.out.println(u.getName());
        if (u != null) {
            String json = gson.toJson(u);
            System.out.println("users not null");
        } else {
            System.out.println("users null");
        }
    }


}

