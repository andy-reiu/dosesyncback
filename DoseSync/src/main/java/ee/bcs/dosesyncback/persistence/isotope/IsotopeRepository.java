package ee.bcs.dosesyncback.persistence.isotope;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IsotopeRepository extends JpaRepository<Isotope, Integer> {

    @Query("select i from Isotope i")
    List<Isotope> findAll();

    @Query("select i from Isotope i where i.status = :status")
    List<Isotope> findIsotopesBy(String status);

    @Query("select (count(i) > 0) from Isotope i where i.name = :isotopeName")
    boolean isotopeExistsBy(String isotopeName);
}