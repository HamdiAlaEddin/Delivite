package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("CLIENT")
public class Client  extends User{
    @Column(name = "deliveries_count", nullable = true)
    private int deliveriesCount;
    @Column(name = "last_quarterly_discount_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date lastQuarterlyDiscountDate; // Date de la dernière réduction trimestrielle
    public Client() {
        super();
        this.setRole(Role.CLIENT);
    }
    @OneToMany(mappedBy = "cli", cascade = CascadeType.ALL)
    private List<Livraison> livraisons = new ArrayList<>();
}
