package kunuz.controller;
import jakarta.validation.Valid;
import kunuz.dto.CategoryCreateDTO;
import kunuz.dto.CategoryDTO;
import kunuz.enums.LanguageEnum;
import kunuz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryDTO response = categoryService.create(dto);
        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                            @Valid @RequestBody CategoryCreateDTO dto) {
        Boolean result = categoryService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        Boolean result = categoryService.delete(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<CategoryDTO> categoryDTOList = categoryService.getAllByLang(lang);
        return ResponseEntity.ok().body(categoryDTOList);
    }



}
