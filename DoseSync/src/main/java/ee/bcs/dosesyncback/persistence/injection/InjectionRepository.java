package ee.bcs.dosesyncback.persistence.injection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionRepository extends JpaRepository<Injection, Integer> {
}