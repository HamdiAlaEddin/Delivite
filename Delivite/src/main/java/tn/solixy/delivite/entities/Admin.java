package tn.solixy.delivite.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    public Admin() {
        super();
        this.setRole(Role.ADMIN);
    }
}
