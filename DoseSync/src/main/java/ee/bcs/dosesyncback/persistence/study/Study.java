package ee.bcs.dosesyncback.persistence.study;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "study", schema = "dosesync")
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "nr_patients")
    private Integer nrPatients;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "total_activity", precision = 8, scale = 2)
    private BigDecimal totalActivity;

    @Size(max = 255)
    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    private String status;

    @Column(name = "calculation_machine_rinse_volume")
    private Integer calculationMachineRinseVolume;

    @Column(name = "calculation_machine_rinse_activity")
    private Integer calculationMachineRinseActivity;

}