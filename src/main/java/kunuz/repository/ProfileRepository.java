package kunuz.repository;

import jakarta.transaction.Transactional;
import kunuz.entity.ProfileEntity;
import kunuz.enums.ProfileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    Page<ProfileEntity> findAllByVisibleTrue(Pageable pageable);
    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);
    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = ?2 where id=?1")
    void updateStatus(Integer userId, ProfileStatus profileStatus);

    ProfileEntity findByPhone(String phone);

    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);
}
