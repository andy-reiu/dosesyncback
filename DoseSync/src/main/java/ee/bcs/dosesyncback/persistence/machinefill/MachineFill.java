package ee.bcs.dosesyncback.persistence.machinefill;

import ee.bcs.dosesyncback.persistence.injection.Injection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "machine_fill", schema = "dosesync")
public class MachineFill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "injection_id", nullable = false)
    private Injection injection;

    @Column(name = "vial_activity_before_injection", precision = 8, scale = 2)
    private BigDecimal vialActivityBeforeInjection;

    @Column(name = "vial_activity_after_injection", precision = 8, scale = 2)
    private BigDecimal vialActivityAfterInjection;

    @Column(name = "injected_volume", precision = 8, scale = 2)
    private BigDecimal injectedVolume;

    @Column(name = "remaining_volume", precision = 8, scale = 2)
    private BigDecimal remainingVolume;
}