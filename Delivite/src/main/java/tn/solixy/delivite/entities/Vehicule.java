package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "vehic")
    private List<LogHisorique> loghs;

    @OneToMany(mappedBy = "vehicula", cascade = CascadeType.ALL)
    private List<Chauffeur> chauffeurs = new ArrayList<>();
}
