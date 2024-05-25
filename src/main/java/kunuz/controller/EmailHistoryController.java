package kunuz.controller;


import kunuz.dto.EmailHistoryDTO;
import kunuz.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/ByEmail")
    public ResponseEntity<List<EmailHistoryDTO>> getAllByEmail(@RequestParam String email) {
        List<EmailHistoryDTO> dtoList = emailHistoryService.getAllByEmail(email);
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/givenDate")
    public ResponseEntity<List<EmailHistoryDTO>> getAllByGivenDate(@RequestParam LocalDate date) {
        List<EmailHistoryDTO> dtoList = emailHistoryService.getAllByGivenDate(date);
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/pagination")
    public ResponseEntity<Page<EmailHistoryDTO>> pagination (@RequestParam Integer pageNumber,
                                                             @RequestParam Integer pageSize) {
        Page<EmailHistoryDTO> page = emailHistoryService.pagination(pageNumber-1, pageSize);
        return ResponseEntity.ok(page);
    }

}
