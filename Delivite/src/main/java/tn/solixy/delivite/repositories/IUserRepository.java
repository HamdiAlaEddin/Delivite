package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.Role;
import tn.solixy.delivite.entities.User;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
 public List<User> findByRole(Role role);
}
