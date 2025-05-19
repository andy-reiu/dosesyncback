package ee.bcs.dosesyncback.persistence.study;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @ColumnDefault("nextval('dosesync.study_id_seq')")
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

    @Column(name = "calculation_machine_rinse_volume")
    private Integer calculationMachineRinseVolume;

    @Column(name = "calculation_machine_rinse_activity")
    private Integer calculationMachineRinseActivity;

}