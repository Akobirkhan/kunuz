package kunuz.service;

import kunuz.dto.RegionByLangDTO;
import kunuz.dto.RegionDTO;
import kunuz.dto.TypeByLangDTO;
import kunuz.dto.TypeDTO;
import kunuz.entity.RegionEntity;
import kunuz.entity.TypeEntity;
import kunuz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        regionRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public RegionDTO update(Integer id, RegionDTO dto) {
        RegionEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        regionRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Type not found"));
    }

    public Boolean delete(Integer id) {
        RegionEntity entity = get(id);
        regionRepository.delete(entity);
        return true;
    }

    public PageImpl<RegionDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("orderNumber"));
        Page<RegionEntity> pageObj = regionRepository.findAll(pageable);

        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity: pageObj.getContent()){
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setOrderNumber(entity.getOrderNumber());
            dtoList.add(dto);
        }
        long totalCount = pageObj.getTotalElements();

        return new PageImpl<>(dtoList, pageable, totalCount);

    }

    public List<RegionByLangDTO> getAllByLanguage(String lang) {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionByLangDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity: iterable){
            RegionByLangDTO dto = new RegionByLangDTO();
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
