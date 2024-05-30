package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.SmsHistoryDTO;
import kunuz.dto.auth.*;
import kunuz.dto.profile.ProfileDTO;
import kunuz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registrWithEmail")
    public ResponseEntity<String> registrWithEmail(@Valid @RequestBody RegistrationByEmailDTO dto) {
        String body = authService.registrWithEmail(dto);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/registrWithPhone")
    public ResponseEntity<String> registrWithPhone(@Valid @RequestBody RegistrationByPhoneDTO dto) {
        String body = authService.registrWithPhone(dto);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/verifyWithEmail/{userId}")
    public ResponseEntity<String> verifyWithEmail(@PathVariable("userId") Integer userId) {
        String body = authService.verifyWithEmail(userId);
        return ResponseEntity.ok().body(body);
    }
    @PostMapping("/verifyWithSms")
    public ResponseEntity<String> verifyWithSms(@RequestBody SmsHistoryDTO code) {
        String body = authService.verifyWithSms(code);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/registration/resendEmail/{email}")
    public ResponseEntity<String> resendEmail(@PathVariable String email) {
        String body = authService.resendEmail(email);
        return ResponseEntity.ok().body(body);
    }
    @GetMapping("/registration/resendSms/{phone}")
    public ResponseEntity<String> resendSms(@PathVariable String phone) {
        String body = authService.resendSms(phone);
        return ResponseEntity.ok().body(body);
    }


    @PostMapping("/loginWithEmail")
    public ResponseEntity<ProfileDTO> loginWithEmail(@Valid @RequestBody LoginDTO dto) {
        ProfileDTO response = authService.loginWithEmail(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/loginWithPhone")
    public ResponseEntity<ProfileDTO> loginWithPhone(@Valid @RequestBody LoginByPhoneDTO dto) {
        ProfileDTO response = authService.loginWithPhone(dto);
        return ResponseEntity.ok().body(response);
    }

    // Login with email
    @PostMapping("/login_email")
    public ResponseEntity<ProfileDTO> loginWithEmail(@RequestBody AuthDTO authDTO) {
        ProfileDTO response = authService.loginWithEmail(authDTO);
        return ResponseEntity.ok().body(response);
    }

}
