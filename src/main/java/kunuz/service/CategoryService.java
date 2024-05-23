package kunuz.service;

import kunuz.dto.category.CategoryCreateDTO;
import kunuz.dto.category.CategoryDTO;
import kunuz.entity.CategoryEntity;
import kunuz.enums.LanguageEnum;
import kunuz.exp.AppBadException;
import kunuz.mapper.CategoryMapper;
import kunuz.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO create(CategoryCreateDTO dto){
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        categoryRepository.save(entity);
        return toDto(entity);
    }

    public Boolean update(Integer id, CategoryCreateDTO dto) {
        CategoryEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        categoryRepository.save(entity);

        return true;
    }
    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(()
                -> new AppBadException("Category not found"));
    }
    public Boolean delete(Integer id) {
        CategoryEntity entity = get(id);
        categoryRepository.delete(entity);
        return true;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity: iterable){
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public List<CategoryDTO> getAllByLang(LanguageEnum lang) {
        Iterable<CategoryEntity> iterable = categoryRepository.findAllByVisibleTrueOrderByOrderNumberDesc();
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity: iterable){
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());

            switch (lang) {
                case EN -> dto.setName(entity.getNameEn());
                case UZ -> dto.setName(entity.getNameUz());
                case RU -> dto.setName(entity.getNameRu());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public CategoryDTO toDto(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }

    public List<CategoryDTO> getAllByLang2(LanguageEnum lang) {
        List<CategoryMapper> mapperList = categoryRepository.findAll(lang.name());
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryMapper entity : mapperList) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }





}
