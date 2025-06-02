package ee.bcs.dosesyncback.persistence.userimage;

import ee.bcs.dosesyncback.persistence.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImage, Integer> {

  @Query("select u from UserImage u where u.profile.id = :profileId")
  Optional<UserImage> findUserImageBy(Integer profileId);

  @Transactional
  @Modifying
  @Query("delete from UserImage u where u.profile = :profile")
  void deleteUserImageBy(Profile profile);
}