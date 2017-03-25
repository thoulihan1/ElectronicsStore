package groupid.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 *
 *

 */

@NamedQueries( {
        @NamedQuery(name = "Manufacturer.getAll", query = "select o from Manufacturer o"),
        @NamedQuery(name = "Manufacturer.getById", query = "select o from Manufacturer o where o.id=:id"),
})

@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;

    public Manufacturer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}