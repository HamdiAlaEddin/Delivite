package tn.solixy.delivite.dto;

public record Commandedto(
        Long LivraisonID,
        String position,
        Long id_chauffeur
) {
}
