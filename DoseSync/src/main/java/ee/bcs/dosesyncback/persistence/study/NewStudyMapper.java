package ee.bcs.dosesyncback.persistence.study;

import ee.bcs.dosesyncback.controller.study.dto.NewStudy;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewStudyMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "studyDate", target = "date")
    Study toStudy(NewStudy newStudy);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "studyDate", target = "date")
    void updateStudy(@MappingTarget Study study, NewStudy newStudy);
}