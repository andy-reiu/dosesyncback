package ee.bcs.dosesyncback.persistence.patient;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "patient", schema = "dosesync", uniqueConstraints = {
        @UniqueConstraint(name = "patient_ak_1", columnNames = {"patient_national_id"})})
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 11)
    @Column(name = "patient_national_id", length = 11)
    private String patientNationalId;
}