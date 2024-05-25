package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.auth.LoginDTO;
import kunuz.dto.auth.RegistrationByPhoneDTO;
import kunuz.dto.auth.RegistrationDTO;
import kunuz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.registration(dto);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/registration/by_phone")
    public ResponseEntity<String> registrationByPhone(@Valid @RequestBody RegistrationByPhoneDTO dto) {
        String body = authService.registrationByPhone(dto);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verification(@PathVariable("userId") Integer userId) {
        String body = authService.authorizationVerification(userId);
        return ResponseEntity.ok().body(body);
    }
    @PostMapping("/verification")
    public ResponseEntity<String> verification(@RequestBody RegistrationByPhoneDTO code) {
        String body = authService.authorizationVerificationPhone(code);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {
        String body = authService.login(dto);
        return ResponseEntity.ok().body(body);
    }

}
