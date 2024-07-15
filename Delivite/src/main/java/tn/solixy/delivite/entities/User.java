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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Column(insertable=false, updatable=false)
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
}
