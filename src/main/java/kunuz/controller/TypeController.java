package kunuz.controller;

import kunuz.dto.TypeByLangDTO;
import kunuz.dto.TypeDTO;
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
    public ResponseEntity<TypeDTO> create(@RequestBody TypeDTO dto) {
        TypeDTO response = typeService.create(dto);
        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TypeDTO> update(@PathVariable("id") Integer id,
                                          @RequestBody TypeDTO dto) {
        TypeDTO response = typeService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        typeService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<TypeDTO>> pageable(@RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<TypeDTO> typeDTOPage = typeService.pagination(page - 1, size);
        return ResponseEntity.ok().body(typeDTOPage);
    }

    @GetMapping("/all")
    public List<TypeByLangDTO> getAllByLanguage(@RequestParam("lang") String lang) {
        return typeService.getAllByLanguage(lang);
    }
}
