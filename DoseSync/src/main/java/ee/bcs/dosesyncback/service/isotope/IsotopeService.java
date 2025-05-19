package ee.bcs.dosesyncback.service.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeInfoMapper;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeRepository;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IsotopeService {

    private final IsotopeRepository isotopeRepository;
    private final IsotopeInfoMapper isotopeInfoMapper;

    public List<IsotopeInfo> getAllIsotopes() {

        List<Isotope> isotopes = isotopeRepository.findAllBy(IsotopeStatus.ACTIVE.getCode());
        List<IsotopeInfo> isotopeInfos = isotopeInfoMapper.toIsotopeInfos(isotopes);
        return isotopeInfos;
    }
}
