package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.solixy.delivite.entities.Image;
import tn.solixy.delivite.entities.Livraison;

public interface IImageRepository extends JpaRepository<Image,Long> {
}
