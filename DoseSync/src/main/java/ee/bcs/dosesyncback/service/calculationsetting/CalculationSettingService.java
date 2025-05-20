package ee.bcs.dosesyncback.service.calculationsetting;

import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSetting;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingDto;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingMapper;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationSettingService {

    private final CalculationSettingRepository calculationSettingRepository;
    private final CalculationSettingMapper calculationSettingMapper;


    public List<CalculationSettingDto> getAllCalculationSettings() {
        //pull all calculationsetting entities
        List<CalculationSetting> calculationSettings = calculationSettingRepository.findAll();
        //map them them to DTOs(and capture result)
        List<CalculationSettingDto> calculationSettingDtos = calculationSettingMapper.toCalculationSettings(calculationSettings);

        return calculationSettingDtos;
    }
}
