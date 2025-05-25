package ee.bcs.dosesyncback.service.hospital;

import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.persistence.hospital.HospitalDto;
import ee.bcs.dosesyncback.persistence.hospital.HospitalMapper;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
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

    public Hospital addHospital(HospitalDto hospitalDto) {
        Hospital hospital = hospitalMapper.toHospital(hospitalDto);
        Hospital saved = hospitalRepository.save(hospital);
        return saved;

    }
}

