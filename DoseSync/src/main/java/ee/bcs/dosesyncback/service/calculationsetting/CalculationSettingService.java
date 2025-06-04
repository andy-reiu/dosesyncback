package ee.bcs.dosesyncback.service.calculationsetting;

import ee.bcs.dosesyncback.controller.calculationsetting.dto.CalculationSettingDto;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSetting;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingMapper;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationSettingService {

    private final CalculationSettingRepository calculationSettingRepository;
    private final CalculationSettingMapper calculationSettingMapper;

    public List<CalculationSettingDto> getAllCalculationSettings() {
        List<CalculationSetting> calculationSettings = calculationSettingRepository.findAll();
        return calculationSettingMapper.toCalculationSettingDtos(calculationSettings);
    }

    @Transactional
    public void addCalculationSetting(CalculationSettingDto calculationSettingDto) {
        CalculationSetting calculateSetting = calculationSettingMapper.toCalculateSetting(calculationSettingDto);
        calculationSettingRepository.save(calculateSetting);
    }

    @Transactional
    public CalculationSettingDto updateCalculationSetting(Integer calculationSettingId, CalculationSettingDto calculationSettingDto) {
        CalculationSetting calculationSetting = getValidCalculationSetting(calculationSettingId);
        calculationSettingMapping(calculationSetting, calculationSettingDto);
        calculationSettingRepository.save(calculationSetting);
        return calculationSettingMapper.toCalculationSettingDto(calculationSetting);
    }

    private void calculationSettingMapping(CalculationSetting calculationSetting, CalculationSettingDto calculationSettingDto) {
        calculationSetting.setMinActivity(calculationSettingDto.getSettingMinActivity());
        calculationSetting.setMaxActivity(calculationSettingDto.getSettingMaxActivity());
        calculationSetting.setMinVolume(calculationSettingDto.getSettingMinVolume());
        calculationSetting.setActivityPerKg(calculationSettingDto.getActivityPerKg());
        calculationSetting.setDefaultPatientWeight(calculationSettingDto.getDefaultPatientWeight());
        calculationSetting.setInjectionInterval(calculationSettingDto.getInjectionInterval());
        calculationSetting.setMachineVolumeMax(calculationSettingDto.getSettingMachineVolumeMax());
        calculationSetting.setMachineVolumeMin(calculationSettingDto.getSettingMachineVolumeMin());
    }

    private CalculationSetting getValidCalculationSetting(Integer calculationSettingId) {
        return calculationSettingRepository.findCalculationSettingBy(calculationSettingId)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("Id"), calculationSettingId));
    }
}
