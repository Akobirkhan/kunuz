package kunuz.repository;

import kunuz.entity.EmailHistoryEntity;
import kunuz.entity.SmsHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer> {

    List<SmsHistoryEntity> findAllByPhone(String phone);

    @Query(value = "select * from sms_history  where created_date between :from and :to ", nativeQuery = true)
    List<SmsHistoryEntity> findAllByGivenDate(LocalDate from, LocalDate to);

    Page<SmsHistoryEntity> findAllBy(Pageable pageable);

    Optional<SmsHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime from, LocalDateTime to);
}
