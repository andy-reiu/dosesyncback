package ee.bcs.dosesyncback.persistence.dailystudy;

import ee.bcs.dosesyncback.persistence.injection.Injection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyStudyRepository extends JpaRepository<DailyStudy, Integer> {

    @Query("select d from DailyStudy d where d.study.id = :studyId order by d.injection.id")
    List<DailyStudy> getDailyStudiesBy(Integer studyId);

    @Query("select (count(d) > 0) from DailyStudy d where d.study.id = :studyId")
    boolean existsInDailyStudyBy(Integer studyId);

    @Query("select d from DailyStudy d where d.study.id = :studyId order by d.id DESC")
    List<DailyStudy> findDailyStudiesBy(Integer studyId);

    @Query("select d from DailyStudy d where d.injection.id = :injectionId")
    Optional<DailyStudy> findDailyStudyBy(Integer injectionId);

    @Transactional
    @Modifying
    @Query("delete from DailyStudy d where d.injection.id = :injectionId")
    void deleteByInjection(Integer injectionId);

    @Query("SELECT ds FROM DailyStudy ds WHERE ds.study.id = :studyId ORDER BY ds.injection.injectedTime ASC")
    List<DailyStudy> findDailyStudiesByStudyOrderedByInjectionTime(Integer studyId);
}