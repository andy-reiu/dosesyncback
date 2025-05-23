package ee.bcs.dosesyncback.persistence.dailystudy;

import ee.bcs.dosesyncback.persistence.study.Study;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyStudyRepository extends JpaRepository<DailyStudy, Integer> {

    @Query("select d from DailyStudy d where d.study.id = :studyId order by d.injection.id")
    List<DailyStudy> findInjectionIdsByStudyId(Integer studyId);

    @Query("select (count(d) > 0) from DailyStudy d where d.study.id = :studyId")
    boolean existsInDailyStudyBy(Integer studyId);

    @Query("select d from DailyStudy d where d.study.id = :studyId order by d.id DESC")
    DailyStudy findByStudyIdOrderByIdDesc(Integer studyId);
}