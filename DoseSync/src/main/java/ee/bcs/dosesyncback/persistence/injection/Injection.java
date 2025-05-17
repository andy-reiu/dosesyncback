package ee.bcs.dosesyncback.persistence.injection;

import ee.bcs.dosesyncback.persistence.vial.Vial;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "injection", schema = "dosesync")
public class Injection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vial_id", nullable = false)
    private Vial vial;

    @NotNull
    @Column(name = "injected_timestamp", nullable = false)
    private LocalTime injectedTimestamp;

    @NotNull
    @Column(name = "injected_activity", nullable = false, precision = 8, scale = 2)
    private BigDecimal injectedActivity;

    @NotNull
    @Column(name = "injection_volume", nullable = false, precision = 8, scale = 2)
    private BigDecimal injectionVolume;

}