package tn.solixy.delivite.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@DiscriminatorValue("Resto")
public class Resto extends User{

    public Resto() {
        super();
        this.setRole(Role.Resto);
    }
}
