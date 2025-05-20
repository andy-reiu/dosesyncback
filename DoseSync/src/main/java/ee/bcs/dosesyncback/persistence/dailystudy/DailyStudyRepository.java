package ee.bcs.dosesyncback.persistence.dailystudy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyStudyRepository extends JpaRepository<DailyStudy, Integer> {

    @Query("select d from DailyStudy d where d.status = :status")
    List<DailyStudy> findAllBy(String status);
}