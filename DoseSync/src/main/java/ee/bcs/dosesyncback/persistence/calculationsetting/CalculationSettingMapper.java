package ee.bcs.dosesyncback.persistence.calculationsetting;

import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CalculationSettingMapper {


    @Mapping(source = "id", target = "settingId")
    @Mapping(source = "minActivity", target = "settingMinActivity")
    @Mapping(source = "maxActivity", target = "settingMaxActivity")
    @Mapping(source = "minVolume", target = "settingMinVolume")
    @Mapping(source = "machineVolumeMax", target = "settingMachineVolumeMax")
    @Mapping(source = "machineVolumeMin", target = "settingMachineVolumeMin")
    @Mapping(source = "injectionInterval", target = "injectionInterval")
    @Mapping(source = "defaultPatientWeight", target = "defaultPatientWeight")
    @Mapping(source = "activityPerKg", target = "activityPerKg")

    //convert objects
    CalculationSettingDto toCalculationSettingDto(CalculationSetting calculationSetting);

    //mapstruct can convert list
    List<CalculationSettingDto> toCalculationSettingDtos(List<CalculationSetting> calculationSettings);



    @Mapping(source = "settingId", target = "id")
    @Mapping(source = "settingMinActivity", target = "minActivity")
    @Mapping(source = "settingMaxActivity", target = "maxActivity")
    @Mapping(source = "settingMinVolume", target = "minVolume")
    @Mapping(source = "settingMachineVolumeMax", target = "machineVolumeMax")
    @Mapping(source = "settingMinActivity", target = "machineVolumeMin")
    @Mapping(source = "injectionInterval", target = "injectionInterval")
    @Mapping(source = "defaultPatientWeight", target = "defaultPatientWeight")
    @Mapping(source = "activityPerKg", target = "activityPerKg")
    CalculationSetting toCalculateSetting(CalculationSettingDto calculationSettingDto);

    List<CalculationSetting> calculationSettings(List<CalculationSettingDto> calculationSettingDtos);

    @Mapping(source = "settingMinActivity", target = "minActivity")
    @Mapping(source = "settingMaxActivity", target = "maxActivity")
    @Mapping(source = "settingMinVolume", target = "minVolume")
    @Mapping(source = "settingMachineVolumeMax", target = "machineVolumeMax")
    @Mapping(source = "settingMinActivity", target = "machineVolumeMin")
    @Mapping(source = "injectionInterval", target = "injectionInterval")
    @Mapping(source = "defaultPatientWeight", target = "defaultPatientWeight")
    @Mapping(source = "activityPerKg", target = "activityPerKg")
    void updateFromCalculationSettingDto(CalculationSettingDto calculationSettingDto,@MappingTarget CalculationSetting calculationSetting);
}