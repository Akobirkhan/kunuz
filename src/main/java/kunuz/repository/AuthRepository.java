package kunuz.repository;

import jakarta.transaction.Transactional;
import kunuz.entity.ProfileEntity;
import kunuz.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AuthRepository extends JpaRepository<ProfileEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id =?1")
    int updateStatus(Integer profileId, ProfileStatus status);
}
