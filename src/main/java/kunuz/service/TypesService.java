package kunuz.service;

import kunuz.dto.types.TypesCreateDTO;
import kunuz.dto.types.TypesDTO;
import kunuz.entity.TypesEntity;
import kunuz.enums.LanguageEnum;
import kunuz.exp.AppBadException;
import kunuz.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class TypesService {
    @Autowired
    private TypesRepository typesRepository;
    public TypesDTO create(TypesCreateDTO dto) {
        TypesEntity entity = new TypesEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        typesRepository.save(entity);
        return toDTO(entity);
    }

    public Boolean update(Integer id, TypesCreateDTO dto) {
        TypesEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setVisible(dto.getVisible());
        typesRepository.save(entity);
        return true;

    }

    public TypesEntity get(Integer id) {
        return typesRepository.findById(id).orElseThrow(()
                -> new AppBadException("Type not found"));
    }

    public Boolean delete(Integer id) {
        TypesEntity entity = get(id);
        typesRepository.delete(entity);
        return true;
    }

    public PageImpl<TypesDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("orderNumber"));
        Page<TypesEntity> pageObj = typesRepository.findAll(pageable);

        List<TypesDTO> dtoList = new LinkedList<>();
        for (TypesEntity entity: pageObj.getContent()){
            TypesDTO dto = new TypesDTO();
            dto.setId(entity.getId());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        long totalCount = pageObj.getTotalElements();

        return new PageImpl<>(dtoList, pageable, totalCount);

    }


    public List<TypesDTO> getAllByLang(LanguageEnum lang) {
        Iterable<TypesEntity> iterable = typesRepository.findAll();
        List<TypesDTO> dtoList = new LinkedList<>();
        for (TypesEntity entity: iterable){
            TypesDTO dto = new TypesDTO();
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

    public TypesDTO toDTO(TypesEntity entity){
        TypesDTO dto = new TypesDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
