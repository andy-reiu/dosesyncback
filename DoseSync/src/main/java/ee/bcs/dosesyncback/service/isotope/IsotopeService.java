package ee.bcs.dosesyncback.service.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeDto;
import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeMapper;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeRepository;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeStatus;
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
        return isotopeMapper.toIsotopeInfos(isotopes);
    }

    public List<IsotopeDto> getAllIsotopes() {
        List<Isotope> isotopes = isotopeRepository.findAll();
        return isotopeMapper.toIsotopeDtos(isotopes);
    }

    public IsotopeDto getIsotopeById(int isotopeId) {
        Isotope isotope = getValidIsotope(isotopeId);
        return isotopeMapper.toIsotopeDto(isotope);
    }

    @Transactional
    public Isotope addIsotope(IsotopeDto isotopeDto) {
        checkIfIsotopeAlreadyTaken(isotopeDto);
        Isotope isotope = isotopeMapper.toIsotope(isotopeDto);
        return isotopeRepository.save(isotope);
    }

    @Transactional
    public void removeIsotope(Integer isotopeId) {
        Isotope isotope = getValidIsotope(isotopeId);
        isotope.setStatus(IsotopeStatus.DISABLED.getCode());
        isotopeRepository.save(isotope);
    }

    @Transactional
    public IsotopeDto updateIsotope(Integer isotopeId, IsotopeDto isotopeDto) {
        Isotope isotope = getValidIsotope(isotopeId);
        isotopeMapper.updateFromIsotopeDto(isotope, isotopeDto);
        isotopeRepository.save(isotope);
        return isotopeMapper.toIsotopeDto(isotope);
    }

    @Transactional
    public IsotopeDto updateIsotopeStatus(Integer isotopeId, String status) {
        Isotope isotope = getValidIsotope(isotopeId);
        isotope.setStatus((status));
        isotopeRepository.save(isotope);
        return getIsotopeById(isotopeId);
    }

    private Isotope getValidIsotope(int isotopeId) {
        return isotopeRepository.findIsotopeBy(isotopeId)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("isotopeId"), isotopeId));
    }

    private void checkIfIsotopeAlreadyTaken(IsotopeDto isotopeDto) {
        if (isotopeRepository.isotopeExistsBy(isotopeDto.getIsotopeName())) {
            throw new ForbiddenException("See isotoop on juba süsteemis!", 403);
        }
    }
}
