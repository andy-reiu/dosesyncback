package ee.bcs.DoseSync.persistence.patient;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "patient", schema = "dosesync")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "acc", nullable = false)
    private String acc;

    @Size(max = 11)
    @Column(name = "patient_national_id", length = 11)
    private String patientNationalId;

    @NotNull
    @Column(name = "weight", nullable = false, precision = 4, scale = 1)
    private BigDecimal weight;

    @NotNull
    @Column(name = "mbq_kg", nullable = false, precision = 4, scale = 1)
    private BigDecimal mbqKg;

}