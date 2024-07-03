package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long VehiculeID;
    private String marque;
    private String modele;
    private String immatriculation;

    @Enumerated(EnumType.STRING)
    private TypeVehicule type;

    private String couleur;

    @OneToMany(mappedBy = "vehicule")
    private List<User> chauffeurs;

}
