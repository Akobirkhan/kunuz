package kunuz.controller;

import kunuz.dto.RegionByLangDTO;
import kunuz.dto.RegionDTO;
import kunuz.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/region")
@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;
    @PostMapping("/create")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto) {
        RegionDTO response = regionService.create(dto);
        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable("id") Integer id,
                                          @RequestBody RegionDTO dto) {
        RegionDTO response = regionService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/all")
    public List<RegionDTO> getAll() {
        return regionService.getAll();
    }

    @GetMapping("/all/byLang")
    public List<RegionByLangDTO> getAllByLanguage(@RequestParam("lang") String lang) {
        return regionService.getAllByLanguage(lang);
    }

}
