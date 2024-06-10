package kunuz.repository;

import jakarta.transaction.Transactional;
import kunuz.entity.ProfileEntity;
import kunuz.enums.ProfileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Page<ProfileEntity> findAllByVisibleTrue(Pageable pageable);
    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);
    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id =?1")
    int updateStatus(String profileId, ProfileStatus status);

}
