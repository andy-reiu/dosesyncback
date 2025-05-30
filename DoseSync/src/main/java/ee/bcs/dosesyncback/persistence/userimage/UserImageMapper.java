package ee.bcs.dosesyncback.persistence.userimage;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileUpdateInfo;
import ee.bcs.dosesyncback.util.ImageConverter;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserImageMapper {
    @Mapping(source = "imageData", target = "data", qualifiedByName = "toByteArray")
    UserImage toUserImage(ProfileUpdateInfo profileUpdateInfo);

    @Named("toByteArray")
    static byte[] toByteArray(String imageData) {

        return ImageConverter.stringToBytes(imageData);
    }


}