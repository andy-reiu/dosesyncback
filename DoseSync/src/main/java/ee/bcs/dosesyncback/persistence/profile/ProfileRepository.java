package ee.bcs.dosesyncback.persistence.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("select p from Profile p where p.id = ?1")
    List<Profile> findAll(Integer id);

    @Query("select p from Profile p where p.user.id = :userId")
    Optional<Profile> findProfileBy(Integer userId);

    Optional<Profile> findByUserId(Integer userId);
}