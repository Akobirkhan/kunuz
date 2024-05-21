package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.ProfileCreateDTO;
import kunuz.dto.ProfileDTO;
import kunuz.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO profile){
        ProfileDTO response = profileService.create(profile);
        return ResponseEntity.ok().body(response);

    }

}
