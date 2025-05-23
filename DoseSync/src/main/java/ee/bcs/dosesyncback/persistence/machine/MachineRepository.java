package ee.bcs.dosesyncback.persistence.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Integer> {

    @Query("select m from Machine m")
    List<Machine> findAll();

    @Query("select m from Machine m where m.status = :status")
    List<Machine> findMachinesBy(String status);

    @Query("select (count(m) > 0) from Machine m where m.serialNumber = :serialNumber")
    boolean serialNumberExistBy(String serialNumber);
}
