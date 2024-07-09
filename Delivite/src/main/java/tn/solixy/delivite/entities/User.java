package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserID;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String location;
    private String numPermisConduit;// (chauffeur)
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String preferredLanguage;
    private LocalDate registrationDate;
    private int rate; // (chauffeur)
    private boolean activated; // Statut du compte (activé ou désactivé)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule; // Véhicule assigné à cet utilisateur (chauffeur)
}
