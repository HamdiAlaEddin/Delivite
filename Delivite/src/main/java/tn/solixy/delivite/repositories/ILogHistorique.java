package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.LogHisorique;
@Repository
public interface ILogHistorique extends JpaRepository<LogHisorique,Long> {
}
