package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.Livraison;

import java.util.List;

@Repository
public interface ILivraisonRepository extends JpaRepository<Livraison,Long> {

    @Query("SELECT l FROM Livraison l WHERE l.cli.userID = :userId or l.chauf.userID = :userId")
    List<Livraison> findBylivraisonbyUserId(@Param("userId") Long userId);



}
