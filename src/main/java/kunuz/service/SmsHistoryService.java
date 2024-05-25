package kunuz.service;

import kunuz.dto.EmailHistoryDTO;
import kunuz.dto.SmsHistoryDTO;
import kunuz.entity.SmsHistoryEntity;
import kunuz.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public List<SmsHistoryDTO> getAllByPhone(String phone) {
        List<SmsHistoryDTO> dtoList = smsHistoryRepository.findAllByPhone(phone).stream()
                .map(this::toDTO)
                .toList();
        return dtoList;

    }

    public List<SmsHistoryDTO> getAllByGivenDate(LocalDate date) {
        List<SmsHistoryDTO> dtoList = smsHistoryRepository.findAllByCreatedDate(date)
                .stream()
                .map(this::toDTO)
                .toList();
        return dtoList;
    }
    private SmsHistoryDTO toDTO(SmsHistoryEntity entity) {
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


}
