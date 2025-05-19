//package ee.bcs.dosesyncback.service;
//
//import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileDto;
//import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
//import ee.bcs.dosesyncback.persistence.isotope.Isotope;
//import ee.bcs.dosesyncback.persistence.isotope.IsotopeRepository;
//import ee.bcs.dosesyncback.persistence.machine.Machine;
//import ee.bcs.dosesyncback.persistence.machine.MachineRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CalculationProfileService {
//
//    private final CalculationProfileMapper calculationProfileMapper;
//    private final MachineRepository machineRepository;
//    private final IsotopeRepository isotopeRepository;
//    private final CalculationProfileRepository calculationProfileRepository;
//
//    public void addCalculationProfile(CalculationProfileDto calculationProfileDto) {
//        CalculationProfile calculationProfile = calculationProfileMapper.toCalculationProfile(calculationProfileDto);
//        Integer machineId = calculationProfileDto.getMachineId();
//        Machine machine = machineRepository.findById(machineId)
//                .orElseThrow(() -> new ForeignKeyNotFoundException("machineId", machineId));
//        calculationProfile.setMachine(machine);
//        Integer isotopeId = calculationProfileDto.getIsotopeId();
//        Isotope isotope = isotopeRepository.findById(isotopeId)
//                .orElseThrow(() -> new ForeignKeyNotFoundException("isotopeId", isotopeId));
//        calculationProfile.setIsotope(isotope);
//        calculationProfileRepository.save(calculationProfile);
//    }
//}
