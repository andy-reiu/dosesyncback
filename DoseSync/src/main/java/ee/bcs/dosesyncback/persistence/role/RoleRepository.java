package ee.bcs.dosesyncback.persistence.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where r.name = :roleName")
    Role findRoleBy(String roleName);

    @Query("select r from Role r")
    List<Role> findAll();
}