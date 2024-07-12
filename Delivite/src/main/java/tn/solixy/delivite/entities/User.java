package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String location;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;
    private String preferredLanguage;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date registrationDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;
    private int rate; // (chauffeur)
    private boolean disponible; //(chauffeur)
    private String numPermisConduit;// (chauffeur)
    private Long vehicule_id; //(chauffeur)
}
