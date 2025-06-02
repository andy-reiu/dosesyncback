package ee.bcs.dosesyncback.persistence.machinefill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MachineFillRepository extends JpaRepository<MachineFill, Integer> {

    @Modifying
    @Query("delete from MachineFill m where m.injection.id = :injectionId")
    void deleteByInjection(Integer injectionId);
}