package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.profile.ProfileCreateDTO;
import kunuz.dto.profile.ProfileDTO;
import kunuz.dto.profile.ProfileFilterDTO;
import kunuz.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO dto) {
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/all")
    ResponseEntity<Page<ProfileDTO>> getAll(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Page<ProfileDTO> response = profileService.getAll(pageNumber - 1, pageSize);
        return ResponseEntity.ok(response);

    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable Integer id,
                                             @RequestBody ProfileCreateDTO dto) {
        ProfileDTO response = profileService.update(id, dto);
        return ResponseEntity.ok(response);
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
