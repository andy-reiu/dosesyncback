package ee.bcs.dosesyncback.service.machinefill;

import ee.bcs.dosesyncback.controller.machinefill.dto.MachineFillInfo;
import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineFillService {

    private final DailyStudyRepository dailyStudyRepository;

    public void getAllMachineFills(Integer studyId) {
        List<DailyStudy> dailyStudies = dailyStudyRepository.getDailyStudiesBy(studyId);
        List<MachineFillInfo> patientInjectionInfos = new ArrayList<>();

    }
}


