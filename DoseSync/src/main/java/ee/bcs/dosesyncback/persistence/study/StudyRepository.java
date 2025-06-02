package ee.bcs.dosesyncback.persistence.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Integer> {

    @Query("select s from Study s where s.id = :studyId")
    Optional<Study> findStudyBy(Integer studyId);

    @Query("select s from Study s")
    List<Study> findAll();

    @Query("select s from Study s order by s.id")
    List<Study> findStudiesByAsc();

}