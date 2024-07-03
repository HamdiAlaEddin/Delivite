package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
}
