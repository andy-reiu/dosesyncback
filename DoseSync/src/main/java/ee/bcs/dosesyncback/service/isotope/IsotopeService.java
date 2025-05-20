package ee.bcs.dosesyncback.service.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.persistence.isotope.*;
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
}
