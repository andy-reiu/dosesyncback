package ee.bcs.dosesyncback.persistence.vial;

import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "vial", schema = "dosesync")
public class Vial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calculation_profiles_id", nullable = false)
    private CalculationProfile calculationProfiles;

    @NotNull
    @Column(name = "vial_activity_before", nullable = false, precision = 8, scale = 2)
    private BigDecimal vialActivityBefore;

    @NotNull
    @Column(name = "vial_activity_after", nullable = false, precision = 8, scale = 2)
    private BigDecimal vialActivityAfter;

    @NotNull
    @Column(name = "remaining_volume", nullable = false, precision = 8, scale = 2)
    private BigDecimal remainingVolume;

}