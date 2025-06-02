package ee.bcs.dosesyncback.persistence.userimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserImageRepository extends JpaRepository<UserImage, Integer> {

  @Query("select u from UserImage u where u.profile.id = :profileId")
  UserImage findByProfileId( Integer profileId);
}