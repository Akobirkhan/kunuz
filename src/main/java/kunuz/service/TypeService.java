package kunuz.service;

import kunuz.dto.TypeByLangDTO;
import kunuz.dto.TypeDTO;
import kunuz.entity.TypeEntity;
import kunuz.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    public TypeDTO create(TypeDTO dto) {
        TypeEntity entity = new TypeEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());
        typeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public TypeDTO update(Integer id, TypeDTO dto) {
        TypeEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());
        typeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }

    public TypeEntity get(Integer id) {
        return typeRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Type not found"));
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


    public List<TypeByLangDTO> getAllByLanguage(String lang) {
        Iterable<TypeEntity> iterable = typeRepository.findAll();
        List<TypeByLangDTO> dtoList = new LinkedList<>();
        for (TypeEntity entity: iterable){
            TypeByLangDTO dto = new TypeByLangDTO();
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
