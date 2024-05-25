package kunuz.service;

import kunuz.dto.EmailHistoryDTO;
import kunuz.entity.EmailHistoryEntity;
import kunuz.exp.AppBadException;
import kunuz.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public List<EmailHistoryDTO> getAllByEmail(String email) {
        List<EmailHistoryDTO> dtoList = emailHistoryRepository.findAllByEmail(email)
                .stream()
                .map(this::toDTO)
                .toList();
        return dtoList;
    }

    public List<EmailHistoryDTO> getAllByGivenDate(LocalDate date) {
        List<EmailHistoryDTO> dtoList = emailHistoryRepository.findAllByCreatedDate(date)
                .stream()
                .map(this::toDTO)
                .toList();
        return dtoList;
    }

    public Page<EmailHistoryDTO> pagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.findAllBy(pageable);

        List<EmailHistoryDTO> dtoList = entityPage.getContent().stream()
                .map(this::toDTO)
                .toList();

        long totalElements = entityPage.getTotalElements();

        return new PageImpl<EmailHistoryDTO>(dtoList,pageable,totalElements);
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void checkEmailLimit(String email) { // 1 minute -3 attempt
        // 23/05/2024 19:01:13
        // 23/05/2024 19:01:23
        // 23/05/2024 19:01:33

        // 23/05/2024 19:00:55 -- (current -1)
        // 23/05/2024 19:01:55 -- current

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailHistoryRepository.countByEmailAndCreatedDateBetween(email, from, to);
        if (count >=3) {
            throw new AppBadException("Sms limit reached. Please try after some time");
        }
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email history not found");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }
}
