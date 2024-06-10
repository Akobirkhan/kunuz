package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.types.TypesCreateDTO;
import kunuz.dto.types.TypesDTO;
import kunuz.enums.LanguageEnum;
import kunuz.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/types")
@RestController
public class TypeController {
    @Autowired
    TypesService typesService;

    @PostMapping("/adm/create")
    public ResponseEntity<TypesDTO> create(@Valid @RequestBody TypesCreateDTO type) {
        TypesDTO response = typesService.create(type);
        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @Valid @RequestBody TypesCreateDTO dto) {
        Boolean result = typesService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        Boolean result = typesService.delete(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<TypesDTO>> pageable(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<TypesDTO> typeDTOPage = typesService.pagination(page - 1, size);
        return ResponseEntity.ok().body(typeDTOPage);
    }

    @GetMapping("/lang")
    public ResponseEntity<List<TypesDTO>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<TypesDTO> typesDTOList = typesService.getAllByLang(lang);
        return ResponseEntity.ok().body(typesDTOList);
    }
}
