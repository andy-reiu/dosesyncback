package ee.bcs.dosesyncback.persistence.calculationsetting;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "calculation_settings", schema = "dosesync")
public class CalculationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "calculation_settings_id_gen", sequenceName = "calculation_settings_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "min_activity", nullable = false, precision = 8, scale = 2)
    private BigDecimal minActivity;

    @NotNull
    @Column(name = "max_activity", nullable = false, precision = 8, scale = 2)
    private BigDecimal maxActivity;

    @NotNull
    @Column(name = "min_volume", nullable = false, precision = 8, scale = 2)
    private BigDecimal minVolume;

    @NotNull
    @Column(name = "machine_volume_max", nullable = false, precision = 8, scale = 2)
    private BigDecimal machineVolumeMax;

    @NotNull
    @Column(name = "machine_volume_min", nullable = false, precision = 8, scale = 2)
    private BigDecimal machineVolumeMin;

    @NotNull
    @Column(name = "injection_interval", nullable = false)
    private LocalTime injectionInterval;

    @NotNull
    @Column(name = "default_patient_weight", nullable = false)
    private Double patientWeight;

    @NotNull
    @Column(name = "activity_per_kg", nullable = false, precision = 8, scale = 2)
    private BigDecimal activityPerKg;

}