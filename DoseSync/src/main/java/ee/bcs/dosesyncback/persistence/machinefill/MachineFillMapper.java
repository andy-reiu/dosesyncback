package ee.bcs.dosesyncback.persistence.machinefill;

import ee.bcs.dosesyncback.controller.machinefill.dto.MachineFillInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MachineFillMapper {

    @Mapping(source = "id", target = "machineFillId")
    @Mapping(source = "vialActivityBeforeInjection", target = "vialActivityBeforeInjection")
    @Mapping(source = "vialActivityAfterInjection", target = "vialActivityAfterInjection")
    @Mapping(source = "injectedVolume", target = "injectedVolume")
    @Mapping(source = "remainingVolume", target = "remainingVolume")
    MachineFillInfo toMachineFillInfo(MachineFill machineFill);

    List<MachineFillInfo> toMachineFillInfos(List<MachineFill> machineFills);

}