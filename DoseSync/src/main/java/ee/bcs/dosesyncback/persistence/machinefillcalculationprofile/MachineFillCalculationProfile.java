package ee.bcs.dosesyncback.persistence.machinefillcalculationprofile;

import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "machine_fill_calculation_profile", schema = "dosesync")
public class MachineFillCalculationProfile {
    @Id
    @ColumnDefault("nextval('dosesync.machine_fill_calculation_profile_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machine_fill_id", nullable = false)
    private MachineFill machineFill;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calculation_profile_id", nullable = false)
    private CalculationProfile calculationProfile;

}