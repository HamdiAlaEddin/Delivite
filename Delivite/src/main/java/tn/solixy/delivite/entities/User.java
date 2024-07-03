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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserID;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String location; // Localisation de l'utilisateur
    private String numPermisConduit; // Numéro de permis de conduire

    private LocalDate dateOfBirth; // Date de naissance de l'utilisateur
    private String phoneNumber; // Numéro de téléphone de l'utilisateur
    private String address; // Adresse complète de l'utilisateur
    private String profilePictureUrl; // URL de la photo de profil
    private String preferredLanguage; // Langue préférée de l'utilisateur
    private LocalDate registrationDate; // Date d'inscription de l'utilisateur
    private int rate; // Évaluation ou score associé à l'utilisateur
    private boolean activated; // Statut du compte (activé ou désactivé)

    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "vehicule_id") // Nom de la colonne de clé étrangère dans la table User (Chauffeur)
    private Vehicule vehicule;
}
