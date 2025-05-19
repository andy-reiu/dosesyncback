package ee.bcs.dosesyncback.persistence.dailystudy;

import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import ee.bcs.dosesyncback.persistence.patient.Patient;
import ee.bcs.dosesyncback.persistence.study.Study;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "daily_study", schema = "dosesync")
public class DailyStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_study_id_gen")
    @SequenceGenerator(name = "daily_study_id_gen", sequenceName = "daily_study_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "injection_id", nullable = false)
    private Injection injection;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machine_fill_id", nullable = false)
    private MachineFill machineFill;

    @NotNull
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    private String status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}