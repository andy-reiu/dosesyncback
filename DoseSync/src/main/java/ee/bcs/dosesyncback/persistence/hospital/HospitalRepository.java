package ee.bcs.dosesyncback.persistence.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {


    @Query("select h from Hospital h where h.name = :hospitalName")
    Hospital findHospitalBy(String hospitalName);
}