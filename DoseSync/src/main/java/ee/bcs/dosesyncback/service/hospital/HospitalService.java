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
        List<HospitalDto> hospitalDtos = hospitalMapper.toHospitalDtos(hospitals);

        return hospitalDtos;
    }

    @Transactional
    public Hospital addHospital(HospitalDto hospitalDto) {
        Hospital hospital = hospitalMapper.toHospital(hospitalDto);
        Hospital saved = hospitalRepository.save(hospital);

        return saved;
    }
    @Transactional
    public HospitalDto updateHospital(Integer hospitalId, HospitalDto hospitalDto) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("hospitalId"), hospitalId));

        hospitalMapper.updateFromHospitalDto(hospitalDto, hospital);
        hospitalRepository.save(hospital);

        return hospitalMapper.toHospitalDto(hospital);
    }
}

