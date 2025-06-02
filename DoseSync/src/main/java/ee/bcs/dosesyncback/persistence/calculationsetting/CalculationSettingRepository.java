package ee.bcs.dosesyncback.persistence.calculationsetting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CalculationSettingRepository extends JpaRepository<CalculationSetting, Integer> {

    @Query("select c from CalculationSetting c where c.id = :calculationSettingId")
    Optional<CalculationSetting> findCalculationSettingBy(Integer calculationSettingId);
}