package ee.bcs.dosesyncback.persistence.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IsotopeInfoMapper {

    @Mapping(source = "id", target = "isotopeId")
    @Mapping(source = "name", target = "isotopeName")
    IsotopeInfo toIsotopeInfo(Isotope isotope);

    List<IsotopeInfo> toIsotopeInfos(List<Isotope> isotope);
}