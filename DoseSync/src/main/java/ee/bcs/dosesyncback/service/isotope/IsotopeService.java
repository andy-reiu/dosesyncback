package ee.bcs.dosesyncback.service.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.persistence.isotope.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IsotopeService {

    private final IsotopeRepository isotopeRepository;
    private final IsotopeInfoMapper isotopeInfoMapper;
    private final IsotopeMapper isotopeMapper;

    public List<IsotopeInfo> getAllActiveIsotopes() {
        List<Isotope> isotopes = isotopeRepository.findAllBy(IsotopeStatus.ACTIVE.getCode());
        List<IsotopeInfo> isotopeInfos = isotopeInfoMapper.toIsotopeInfos(isotopes);
        return isotopeInfos;
    }
    public List<IsotopeDto> getAllIsotopes() {
        List<Isotope> isotopes = isotopeRepository.findAll();
        List<IsotopeDto> isotopeDtos = isotopeMapper.toIsotopeDtos(isotopes);
        return isotopeDtos;
    }
    @Transactional
    public Isotope addIsotope(IsotopeDto isotopeDto) {
        //validate that name isnt already taken
        if (isotopeRepository.isotopeExistsBy(isotopeDto.getIsotopeName())) {
            throw new ForbiddenException(
                    "See isotoop on juba süsteemis!",
                    403);
        }
        // map dto to entity and save it
        Isotope isotope = isotopeMapper.toIsotope(isotopeDto);
        Isotope saved = isotopeRepository.save(isotope);

        return saved;
    }
}
