package ee.bcs.dosesyncback.controller.isotope.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "isotope_info")
public class IsotopeInfo {
    @Id
    @Column(name = "id", nullable = false)
    private Integer isotopeId;

    @Column(name = "name")
    private String isotopeName;

}