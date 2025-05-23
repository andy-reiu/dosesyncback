package ee.bcs.dosesyncback.persistence.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {


    @Query("select p from Patient p where p.patientNationalId = :patientNationalId")
    Optional<Patient> findPatientBy(String patientNationalId);
}