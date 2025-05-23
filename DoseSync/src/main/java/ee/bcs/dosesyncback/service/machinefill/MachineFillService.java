package ee.bcs.dosesyncback.service.machinefill;

import ee.bcs.dosesyncback.controller.machinefill.dto.MachineFillInfo;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineFillService {

    private final DailyStudyRepository dailyStudyRepository;
    private final MachineFillMapper machineFillMapper;

    public List<MachineFillInfo> getAllMachineFills(Integer studyId) {
        List<DailyStudy> dailyStudies = dailyStudyRepository.getDailyStudiesBy(studyId);
        List<MachineFillInfo> machineFillInfos = new ArrayList<>();
        for (DailyStudy dailyStudy : dailyStudies) {
            MachineFillInfo machineFillInfo = machineFillMapper.toMachineFillInfo(dailyStudy.getMachineFill());
            machineFillInfos.add(machineFillInfo);
        }
        return machineFillInfos;
    }
}


