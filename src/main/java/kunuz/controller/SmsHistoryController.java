package kunuz.controller;

import kunuz.dto.SmsHistoryDTO;
import kunuz.service.SmsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;
    @GetMapping("/ByPhone")
    public ResponseEntity<List<SmsHistoryDTO>> getAllByPhone(@RequestParam String phone) {
        List<SmsHistoryDTO> dtoList = smsHistoryService.getAllByPhone(phone);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/givenDate")
    public ResponseEntity<List<SmsHistoryDTO>> getAllByGivenDate(@RequestParam LocalDate date) {
        List<SmsHistoryDTO> dtoList = smsHistoryService.getAllByGivenDate(date);
        return ResponseEntity.ok(dtoList);
    }


}
