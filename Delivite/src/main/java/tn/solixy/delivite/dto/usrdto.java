package tn.solixy.delivite.dto;

import tn.solixy.delivite.entities.Image;
import tn.solixy.delivite.entities.Role;

public record usrdto(Long id,
                     String firstName,
                     String lastName,
                     String phoneNumber,
                     Image image,
                     Role role) {
}
