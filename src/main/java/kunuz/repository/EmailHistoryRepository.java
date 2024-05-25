package kunuz.repository;

import kunuz.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {
    List<EmailHistoryEntity> findAllByEmail(String email);
    List<EmailHistoryEntity> findAllByCreatedDate(LocalDate date);
    Page<EmailHistoryEntity> findAllBy(Pageable pageable);
    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);
    // select count(*) from email_history createdDate between :from and to

    Optional<EmailHistoryEntity> findByEmail(String email);
}
