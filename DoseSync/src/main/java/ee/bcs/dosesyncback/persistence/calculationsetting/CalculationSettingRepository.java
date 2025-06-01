package ee.bcs.dosesyncback.persistence.calculationsetting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface CalculationSettingRepository extends JpaRepository<CalculationSetting, Integer> {


    CalculationSetting findByMinVolume(BigDecimal minVolume);
}