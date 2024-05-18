package kunuz.controller;

import kunuz.dto.CategoryByLangDTO;
import kunuz.dto.CategoryDTO;
import kunuz.dto.RegionByLangDTO;
import kunuz.dto.RegionDTO;
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
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto) {
        CategoryDTO response = categoryService.create(dto);
        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id,
                                            @RequestBody CategoryDTO dto) {
        CategoryDTO response = categoryService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/all")
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/all/byLang")
    public List<CategoryByLangDTO> getAllByLanguage(@RequestParam("lang") String lang) {
        return categoryService.getAllByLanguage(lang);
    }

}
