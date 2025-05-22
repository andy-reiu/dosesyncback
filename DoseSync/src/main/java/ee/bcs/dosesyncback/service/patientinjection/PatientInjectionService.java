package ee.bcs.dosesyncback.service.patientinjection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.injection.InjectionRepository;
import ee.bcs.dosesyncback.persistence.injection.PatientInjectionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientInjectionService {

    private final InjectionRepository injectionRepository;
    private final PatientInjectionMapper patientInjectionMapper;

    public PatientInjectionService(InjectionRepository injectionRepository, PatientInjectionMapper patientInjectionMapper) {
        this.injectionRepository = injectionRepository;
        this.patientInjectionMapper = patientInjectionMapper;
    }

    public List<PatientInjectionInfo> getAllStudiesPatientInjections(Integer studyId) {
        //Injection injection = injectionRepository.getReferenceById(studyId); //VALE
        PatientInjectionInfo patientInjectionInfo = patientInjectionMapper.toPatientInjectionInfo(injection);

        return null;
    }
}
