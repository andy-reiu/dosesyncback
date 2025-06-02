package ee.bcs.dosesyncback.persistence.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    @Query("select h from Hospital h where h.id = :hospitalId")
    Optional<Hospital> findHospitalBy(Integer hospitalId);
}