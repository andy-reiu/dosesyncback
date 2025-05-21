package ee.bcs.dosesyncback.persistence.calculationprofile;

import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.study.Study;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "calculation_profile", schema = "dosesync")
public class CalculationProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "isotope_id", nullable = false)
    private Isotope isotope;

    @NotNull
    @Column(name = "calibrated_activity", nullable = false, precision = 8, scale = 2)
    private BigDecimal calibratedActivity;

    @NotNull
    @Column(name = "calibration_time", nullable = false)
    private LocalTime calibrationTime;

    @NotNull
    @Column(name = "administration_time", nullable = false)
    private LocalTime administrationTime;

//    @NotNull
//    @Column(name = "activity_before_first", nullable = false)
//    private Integer activityBeforeFirst;

    @NotNull
    @Column(name = "fill_volume", nullable = false)
    private Integer fillVolume;
}