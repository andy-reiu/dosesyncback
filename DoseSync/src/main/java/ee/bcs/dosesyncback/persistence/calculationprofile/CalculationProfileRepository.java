package ee.bcs.dosesyncback.persistence.calculationprofile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CalculationProfileRepository extends JpaRepository<CalculationProfile, Integer> {


  @Query("select c from CalculationProfile c where c.study.id = :studyId")
  Optional<CalculationProfile> findCalculationProfileBy(Integer studyId);
}