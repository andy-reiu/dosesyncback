package ee.bcs.dosesyncback.persistence.injection;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Size(max = 12)
    @NotNull
    @Column(name = "acc", nullable = false, length = 12)
    private String acc;

    @NotNull
    @Column(name = "weight", nullable = false, precision = 4, scale = 1)
    private BigDecimal weight;

    @NotNull
    @Column(name = "mbq_kg", nullable = false, precision = 7, scale = 3)
    private BigDecimal mbqKg;

    @NotNull
    @Column(name = "injected_time", nullable = false)
    private LocalTime injectedTime;

    @NotNull
    @Column(name = "injected_activity", nullable = false, precision = 8, scale = 2)
    private BigDecimal injectedActivity;
}