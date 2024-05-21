package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.TypeCreateDTO;
import kunuz.dto.TypeDTO;
import kunuz.enums.LanguageEnum;
import kunuz.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/type")
@RestController
public class TypeController {
    @Autowired
    TypeService typeService;

    @PostMapping("/create")
    public ResponseEntity<TypeDTO> create(@Valid @RequestBody TypeCreateDTO type) {
        TypeDTO response = typeService.create(type);
        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @Valid @RequestBody TypeCreateDTO dto) {
        Boolean result = typeService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        Boolean result = typeService.delete(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<TypeDTO>> pageable(@RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<TypeDTO> typeDTOPage = typeService.pagination(page - 1, size);
        return ResponseEntity.ok().body(typeDTOPage);
    }

    @GetMapping("/lang")
    public ResponseEntity<List<TypeDTO>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<TypeDTO> typeDTOList = typeService.getAllByLang(lang);
        return ResponseEntity.ok().body(typeDTOList);
    }
}
