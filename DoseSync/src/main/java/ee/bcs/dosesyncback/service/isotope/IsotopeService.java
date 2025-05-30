package ee.bcs.dosesyncback.service.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeDto;
import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.isotope.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IsotopeService {

    private final IsotopeRepository isotopeRepository;
    private final IsotopeMapper isotopeMapper;

    public List<IsotopeInfo> getAllActiveIsotopes() {
        List<Isotope> isotopes = isotopeRepository.findIsotopesBy(IsotopeStatus.ACTIVE.getCode());
        List<IsotopeInfo> isotopeInfos = isotopeMapper.toIsotopeInfos(isotopes);

        return isotopeInfos;
    }

    public List<IsotopeDto> getAllIsotopes() {
        List<Isotope> isotopes = isotopeRepository.findAll();
        List<IsotopeDto> isotopeDtos = isotopeMapper.toIsotopeDtos(isotopes);

        return isotopeDtos;
    }

    public IsotopeDto getIsotopeById(int id) {
        Isotope isotope = isotopeRepository.findById(id)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("id"), id));

        return isotopeMapper.toIsotopeDto(isotope);
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
    @Transactional
    public Isotope removeIsotope(Integer isotopeId) {
        Isotope isotope = isotopeRepository.findById(isotopeId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("isotopeId", isotopeId));
        isotope.setStatus(IsotopeStatus.DISABLED.getCode());

        return isotopeRepository.save(isotope);
    }

    @Transactional
    public IsotopeDto updateIsotopeStatus(int id, String status) {
        Isotope isotope = isotopeRepository.findById(id)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("id"), id));
        isotope.setStatus((status));
        isotopeRepository.save(isotope);

        return getIsotopeById(id);

    }
    @Transactional
    public IsotopeDto updateIsotope(Integer id, IsotopeDto isotopeDto) {
        Isotope isotope = isotopeRepository.findById(id)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("id"), id));
        isotopeMapper.updateFromIsotopeDto(isotopeDto, isotope);
        isotopeRepository.save(isotope);

        return isotopeMapper.toIsotopeDto(isotope);
    }
}
