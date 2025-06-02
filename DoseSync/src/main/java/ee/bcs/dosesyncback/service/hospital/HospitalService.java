package ee.bcs.dosesyncback.service.hospital;

import ee.bcs.dosesyncback.controller.hospital.dto.HospitalDto;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.persistence.hospital.HospitalMapper;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    public List<HospitalDto> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return hospitalMapper.toHospitalDtos(hospitals);
    }

    @Transactional
    public Hospital addHospital(HospitalDto hospitalDto) {
        Hospital hospital = hospitalMapper.toHospital(hospitalDto);
        return hospitalRepository.save(hospital);
    }

    @Transactional
    public HospitalDto updateHospital(Integer hospitalId, HospitalDto hospitalDto) {
        Hospital hospital = getValidHospital(hospitalId);
        hospitalMapper.updateFromHospitalDto(hospitalDto, hospital);
        hospitalRepository.save(hospital);
        return hospitalMapper.toHospitalDto(hospital);
    }

    private Hospital getValidHospital(Integer hospitalId) {
        return hospitalRepository.findHospitalBy(hospitalId)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("hospitalId"), hospitalId));
    }
}

