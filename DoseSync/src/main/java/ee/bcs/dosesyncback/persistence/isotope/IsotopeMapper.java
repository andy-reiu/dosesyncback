package ee.bcs.dosesyncback.persistence.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeDto;
import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface IsotopeMapper {
    @Mapping(source = "id", target = "isotopeId")
    @Mapping(source = "name", target = "isotopeName")
    @Mapping(source = "halfLifeHr", target = "halfLifeHr")
    @Mapping(source = "unit", target = "unit")
    @Mapping(source = "status", target = "isotopeStatus")
    IsotopeDto toIsotopeDto(Isotope isotope);

    List<IsotopeDto> toIsotopeDtos(List<Isotope> isotopes);

    @Mapping(source = "isotopeId", target = "id")
    @Mapping(source = "isotopeName", target = "name")
    @Mapping(source = "halfLifeHr", target = "halfLifeHr")
    @Mapping(source = "unit", target = "unit")
    @Mapping(source = "isotopeStatus", target = "status")
    Isotope toIsotope(IsotopeDto isotopeDto);

    List<Isotope> toIsotopes(List<IsotopeDto> isotopeDtos);

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "isotopeName", target = "name")
    @Mapping(source = "halfLifeHr", target = "halfLifeHr")
    @Mapping(source = "unit", target = "unit")
    void updateFromIsotopeDto(@MappingTarget Isotope isotope, IsotopeDto isotopeDto);

    @Mapping(source = "id", target = "isotopeId")
    @Mapping(source = "name", target = "isotopeName")
    IsotopeInfo toIsotopeInfo(Isotope isotope);

    List<IsotopeInfo> toIsotopeInfos(List<Isotope> isotope);
}