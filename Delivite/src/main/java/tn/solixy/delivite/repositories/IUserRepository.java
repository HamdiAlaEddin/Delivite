package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.Chauffeur;
import tn.solixy.delivite.entities.Role;
import tn.solixy.delivite.entities.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
  List<User> findByRole(Role role);



}
