package tn.solixy.delivite.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("Client")
public class Client  extends User{
    private int deliveriesCount;
    private Date lastQuarterlyDiscountDate; // Date de la dernière réduction trimestrielle
    public Client() {

    }
}
