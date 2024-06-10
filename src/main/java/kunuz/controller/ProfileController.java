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

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "/adm/create") //ADMIN
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO dto) {
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }
    //Admin
    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @Valid @RequestBody ProfileCreateDTO profile) {
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

    @GetMapping(value = "/all_with_pagination") //Admin
    ResponseEntity<Page<ProfileDTO>> getAll(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize,
                                            @RequestHeader("Authorization") String token) {

        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        Page<ProfileDTO> response = profileService.getAll(pageNumber - 1, pageSize);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/adm/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id/*,
                                          @RequestHeader("Authorization") String token*/) {
//        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
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
