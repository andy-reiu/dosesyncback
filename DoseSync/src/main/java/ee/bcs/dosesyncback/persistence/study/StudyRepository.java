package ee.bcs.dosesyncback.persistence.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Integer> {

    @Query("select s from Study s where s.status = :status")
    List<Study> findAllStudiesBy(String status);

    @Query("select s from Study s")
    List<Study> findAll();
}