package kunuz.service;

import kunuz.dto.type.TypeCreateDTO;
import kunuz.dto.type.TypeDTO;
import kunuz.entity.TypeEntity;
import kunuz.enums.LanguageEnum;
import kunuz.exp.AppBadException;
import kunuz.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    public TypeDTO create(TypeCreateDTO dto) {
        TypeEntity entity = new TypeEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        typeRepository.save(entity);
        return toDTO(entity);
    }

    public Boolean update(Integer id, TypeCreateDTO dto) {
        TypeEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        typeRepository.save(entity);
        return true;

    }

    public TypeEntity get(Integer id) {
        return typeRepository.findById(id).orElseThrow(()
                -> new AppBadException("Type not found"));
    }

    public Boolean delete(Integer id) {
        TypeEntity entity = get(id);
        typeRepository.delete(entity);
        return true;
    }

    public PageImpl<TypeDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("orderNumber"));
        Page<TypeEntity> pageObj = typeRepository.findAll(pageable);

        List<TypeDTO> dtoList = new LinkedList<>();
        for (TypeEntity entity: pageObj.getContent()){
            TypeDTO dto = new TypeDTO();
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


    public List<TypeDTO> getAllByLang(LanguageEnum lang) {
        Iterable<TypeEntity> iterable = typeRepository.findAll();
        List<TypeDTO> dtoList = new LinkedList<>();
        for (TypeEntity entity: iterable){
            TypeDTO dto = new TypeDTO();
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

    public TypeDTO toDTO(TypeEntity entity){
        TypeDTO dto = new TypeDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
