package ee.bcs.dosesyncback.persistence.machinefill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MachineFillRepository extends JpaRepository<MachineFill, Integer> {


    @Query("select m from MachineFill m where m.injection.id = :injectionId")
    MachineFill findMachineFillBy(Integer injectionId);

    @Transactional
    @Modifying
    @Query("delete from MachineFill m where m.injection.id = :injectionId")
    void deleteByInjection(Integer injectionId);


    @Query("select m from MachineFill m where m.injection.id = :injectionId")
    List<MachineFill> findAllByInjectionId(Integer injectionId);
}