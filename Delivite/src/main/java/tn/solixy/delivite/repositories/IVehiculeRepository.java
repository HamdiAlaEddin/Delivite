package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.Vehicule;

@Repository
public interface IVehiculeRepository extends JpaRepository<Vehicule,Long> {
}
