package groupid.model;

import groupid.observer.Observer;
import groupid.observer.Topic;

import javax.persistence.*;

/**
 * Created by Thomas on 3/31/17.
 */


@NamedQueries( {
        @NamedQuery(name = "Admin.getAll", query = "select o from Admin o"),
        @NamedQuery(name = "Admin.getById", query = "select o from Admin o where o.id=:id"),
        @NamedQuery(name = "Admin.getByEmailAndPassword", query = "select o from Admin o where o.email=:email AND o.password=:password")
})

@Entity
public class Admin extends User{



}
