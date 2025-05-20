package ee.bcs.dosesyncback.persistence.isotope;

import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IsotopeMapper {
    @Mapping(source = "id", target = "isotopeId")
    @Mapping(source = "name", target = "isotopeName")
    @Mapping(source = "halfLifeHr", target = "halfLifeHr")
    @Mapping(source = "unit", target = "unit")
    @Mapping(source = "status", target = "isotopeStatus")
    IsotopeDto toIsotopeDto(Isotope isotope);

    List<IsotopeDto> toIsotopeDtos(List<Isotope> isotopes);

    Isotope toEntity(IsotopeDto isotopeDto);
}