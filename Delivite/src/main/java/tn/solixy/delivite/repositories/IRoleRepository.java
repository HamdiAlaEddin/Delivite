package tn.solixy.delivite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.solixy.delivite.entities.Role;
import tn.solixy.delivite.entities.RoleName;
import tn.solixy.delivite.entities.User;

@Repository
public interface IRoleRepository  extends JpaRepository<Role,Long> {
    Role findByRole(RoleName roleName);
}
