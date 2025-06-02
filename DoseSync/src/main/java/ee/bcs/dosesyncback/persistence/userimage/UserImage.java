package ee.bcs.dosesyncback.persistence.userimage;

import ee.bcs.dosesyncback.persistence.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_image", schema = "dosesync")
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;


    @NotNull
    @Column(name = "data", nullable = false)
    private byte[] data;
}