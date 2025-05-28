package ee.bcs.dosesyncback.service.calculationsetting;

import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSetting;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingDto;
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
        //pull all calculationsetting entities
        List<CalculationSetting> calculationSettings = calculationSettingRepository.findAll();
        //map them to DTOs(and capture result)
        List<CalculationSettingDto> calculationSettingDtos = calculationSettingMapper.toCalculationSettingDtos(calculationSettings);

        return calculationSettingDtos;
    }

    @Transactional
    public CalculationSetting addCalculationSetting(CalculationSettingDto calculationSettingDto) {

        CalculationSetting calculateSetting = calculationSettingMapper.toCalculateSetting(calculationSettingDto);

        CalculationSetting saved = calculationSettingRepository.save(calculateSetting);
        return saved;
    }
    @Transactional
    public CalculationSettingDto updateCalculationSetting(Integer id, CalculationSettingDto calculationSettingDto) {
        CalculationSetting calculationSetting = calculationSettingRepository.findById(id)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("Id"), id));

        if (calculationSettingDto.getSettingMinActivity() != null) {
            calculationSetting.setMinActivity(calculationSettingDto.getSettingMinActivity());
        }
        if(calculationSettingDto.getSettingMaxActivity() != null) {
            calculationSetting.setMaxActivity(calculationSettingDto.getSettingMaxActivity());
        }
        if (calculationSettingDto.getSettingMinVolume() != null) {
            calculationSetting.setMinVolume(calculationSettingDto.getSettingMinVolume());
        }
        if (calculationSettingDto.getActivityPerKg() != null) {
            calculationSetting.setActivityPerKg(calculationSettingDto.getActivityPerKg());
        }
        if (calculationSettingDto.getDefaultPatientWeight() != null) {
            calculationSetting.setDefaultPatientWeight(calculationSettingDto.getDefaultPatientWeight());
        }
        if (calculationSettingDto.getInjectionInterval() != null) {
            calculationSetting.setInjectionInterval(calculationSettingDto.getInjectionInterval());
        }
        if (calculationSettingDto.getSettingMachineVolumeMax() != null) {
            calculationSetting.setMachineVolumeMax(calculationSettingDto.getSettingMachineVolumeMax());
        }
        if (calculationSettingDto.getSettingMachineVolumeMin() != null) {
            calculationSetting.setMachineVolumeMin(calculationSettingDto.getSettingMachineVolumeMin());
        }

        calculationSettingRepository.save(calculationSetting);

        return calculationSettingMapper.toCalculationSettingDto(calculationSetting);
    }
}
