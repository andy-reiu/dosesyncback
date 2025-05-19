package ee.bcs.dosesyncback.persistence.isotope;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IsotopeRepository extends JpaRepository<Isotope, Integer> {

  @Query("select i from Isotope i")
  List<Isotope> findAll();
}