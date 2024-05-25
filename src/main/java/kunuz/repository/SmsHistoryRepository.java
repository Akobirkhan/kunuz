package kunuz.repository;

import kunuz.entity.EmailHistoryEntity;
import kunuz.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer> {
    List<SmsHistoryEntity> findAllByPhone(String phone);

    List<SmsHistoryEntity> findAllByCreatedDate(LocalDate date);
    @Query("from SmsHistoryEntity where phone =?1 order by createdDate desc limit 1")
    SmsHistoryEntity findByPhone(String phone);


}
