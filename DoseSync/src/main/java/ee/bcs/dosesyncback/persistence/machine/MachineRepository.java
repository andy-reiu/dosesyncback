package ee.bcs.dosesyncback.persistence.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Integer> {

    @Query("select m from Machine m where m.id = :machineId")
    Optional<Machine> findMachineBy(Integer machineId);

    @Query("select m from Machine m")
    List<Machine> findAll();

    @Query("select m from Machine m where m.status = :status")
    List<Machine> findMachinesBy(String status);

    @Query("select (count(m) > 0) from Machine m where m.serialNumber = :serialNumber")
    boolean serialNumberExistBy(String serialNumber);

}
