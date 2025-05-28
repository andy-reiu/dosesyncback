package ee.bcs.dosesyncback.persistence.machinefillcalculationprofile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MachineFillCalculationProfileRepository extends JpaRepository<MachineFillCalculationProfile, Integer> {

  @Transactional
  @Modifying
  @Query("delete from MachineFillCalculationProfile m where m.machineFill.id = :machineFillId")
  void deleteByMachineFillId(Integer machineFillId);
}