package kunuz.service;

import kunuz.dto.CategoryByLangDTO;
import kunuz.dto.CategoryDTO;
import kunuz.dto.RegionByLangDTO;
import kunuz.dto.RegionDTO;
import kunuz.entity.CategoryEntity;
import kunuz.entity.RegionEntity;
import kunuz.repository.CategoryRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO create(CategoryDTO dto){
        CategoryEntity entity = new CategoryEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        CategoryEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setVisible(dto.getVisible());
        categoryRepository.save(entity);

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        return dto;
    }
    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Category not found"));
    }
    public Boolean delete(Integer id) {

        CategoryEntity entity = get(id);
        categoryRepository.delete(entity);
        return true;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAllByOrderNumber();
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity: iterable){
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setVisible(entity.getVisible());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<CategoryByLangDTO> getAllByLanguage(String lang) {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryByLangDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity: iterable){
            CategoryByLangDTO dto = new CategoryByLangDTO();
            dto.setId(entity.getId());

            if (lang.equals("uz")){
                dto.setName(entity.getNameUz());
            }else if (lang.equals("ru")){
                dto.setName(entity.getNameRu());
            }else {
                dto.setName(entity.getNameEn());
            }

            dto.setOrderNumber(entity.getOrderNumber());
            dtoList.add(dto);
        }
        return dtoList;
    }





}
