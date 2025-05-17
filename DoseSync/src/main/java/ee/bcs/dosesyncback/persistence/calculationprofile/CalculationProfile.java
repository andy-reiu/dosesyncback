package ee.bcs.DoseSync.persistence.calculationprofile;

import ee.bcs.DoseSync.persistence.isotope.Isotope;
import ee.bcs.DoseSync.persistence.machine.Machine;
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
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "isotope_id", nullable = false)
    private Isotope isotope;

    @NotNull
    @Column(name = "activity", nullable = false, precision = 8, scale = 2)
    private BigDecimal activity;

    @NotNull
    @Column(name = "calibration_time", nullable = false)
    private LocalTime calibrationTime;

    @NotNull
    @Column(name = "administration_time", nullable = false)
    private LocalTime administrationTime;

    @NotNull
    @Column(name = "activity_before_first", nullable = false)
    private Integer activityBeforeFirst;

    @NotNull
    @Column(name = "vial_volume", nullable = false)
    private Integer vialVolume;

}