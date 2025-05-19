package ee.bcs.dosesyncback.persistence.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO for {@link IsotopeInfo}
 */
@Getter
@Setter
@Entity
@Table(name = "isotope", schema = "dosesync")
public class Isotope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @NotNull
    @Column(name = "half_life_hr", nullable = false, precision = 8, scale = 3)
    private BigDecimal halfLifeHr;

    @Size(max = 10)
    @NotNull
    @Column(name = "unit", nullable = false, length = 10)
    private String unit;

}