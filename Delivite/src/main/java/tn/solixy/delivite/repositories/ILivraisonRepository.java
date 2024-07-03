package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.Livraison;

@Repository
public interface ILivraisonRepository extends JpaRepository<Livraison,Long> {
}
