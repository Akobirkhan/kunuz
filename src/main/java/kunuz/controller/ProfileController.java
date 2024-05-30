package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.auth.JwtDTO;
import kunuz.dto.profile.ProfileCreateDTO;
import kunuz.dto.profile.ProfileDTO;
import kunuz.dto.profile.ProfileFilterDTO;
import kunuz.dto.profile.ProfileUpdateDTO;
import kunuz.enums.ProfileRole;
import kunuz.service.ProfileService;
import kunuz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "/create") //ADMIN
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO dto,
                                             @RequestHeader("Authorization") String token) {
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/all_with_pagination") //Admin
    ResponseEntity<Page<ProfileDTO>> getAll(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize,
                                            @RequestHeader("Authorization") String token) {
        SecurityUtil.getJwtDTO(token, List.of(ProfileRole.ROLE_ADMIN,ProfileRole.ROLE_MODERATOR,ProfileRole.ROLE_PUBLISH));
        Page<ProfileDTO> response = profileService.getAll(pageNumber - 1, pageSize);
        return ResponseEntity.ok(response);

    }
    //Admin
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @Valid @RequestBody ProfileCreateDTO profile) {
        profileService.update(id, profile);
        return ResponseEntity.ok().body(true);
    }


    // user
    // 3. Update Profile Detail (ANY) (Profile updates own details)
    @PutMapping("/current")
    public ResponseEntity<Boolean> updateUser(@Valid @RequestBody ProfileUpdateDTO profile,
                                              @RequestHeader("Authorization") String token) {
        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        profileService.updateUser(dto.getId(), profile);
        return ResponseEntity.ok().body(true);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Boolean response = profileService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/filter")
    ResponseEntity<Page<ProfileDTO>> filter(@RequestBody ProfileFilterDTO dto,
                                            @RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Page<ProfileDTO> response = profileService.filter(dto, pageNumber-1, pageSize);
        return ResponseEntity.ok(response);
    }


}
