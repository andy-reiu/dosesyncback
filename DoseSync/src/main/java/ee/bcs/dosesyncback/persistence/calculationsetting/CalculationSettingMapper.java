package ee.bcs.dosesyncback.persistence.calculationsetting;

import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CalculationSettingMapper {

//    private Integer settingId;
//    private BigDecimal settingMinActivity;
//    private BigDecimal settingMaxActivity;
//    private BigDecimal settingMinVolume;
//    private BigDecimal settingMachineVolumeMax;
//    private BigDecimal settingMachineVolumeMin;
    @Mapping(source = "id", target = "settingId")
    @Mapping(source = "minActivity", target = "settingMinActivity")
    @Mapping(source = "maxActivity", target = "settingMaxActivity")
    @Mapping(source = "minVolume", target = "settingMinVolume")
    @Mapping(source = "machineVolumeMax", target = "settingMachineVolumeMax")
    @Mapping(source = "machineVolumeMin", target = "settingMachineVolumeMin")
    //convert objects
    CalculationSettingDto toCalculationSetting(CalculationSetting calculationSetting);

    //mapstruct can convert list
    List<CalculationSettingDto> toCalculationSettings(List<CalculationSetting> calculationSettings);


    CalculationSetting toEntity(CalculationSettingDto calculationSettingDto);



}