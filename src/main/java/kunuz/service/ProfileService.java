package kunuz.service;

import kunuz.dto.profile.*;
import kunuz.entity.ProfileEntity;
import kunuz.enums.ProfileRole;
import kunuz.enums.ProfileStatus;
import kunuz.exp.AppBadException;
import kunuz.repository.ProfileCustomRepository;
import kunuz.repository.ProfileRepository;
import kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository customRepository;

    public ProfileDTO create(ProfileCreateDTO dto) {
        if (dto.getRole().equals(ProfileRole.ROLE_MODERATOR) ||
                dto.getRole().equals(ProfileRole.ROLE_PUBLISHER)){
                ProfileEntity save = profileRepository.save(toEntity(dto));
            return toDTO(save);
        }
        throw new AppBadException("Profile role error");
    }

    public Page<ProfileDTO> getAll(int pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        Page<ProfileEntity> entityPage = profileRepository.findAllByVisibleTrue(pageable);
        List<ProfileDTO> list = entityPage.getContent().stream()
                .map(this::toDTO)
                .toList();
        Long totalElements = entityPage.getTotalElements();
        return new PageImpl<ProfileDTO>(list, pageable, totalElements);
    }

    public Boolean update(Integer id, ProfileCreateDTO profile) {
        ProfileEntity entity = get(id);
        entity.setName(profile.getName()!=null? profile.getName() : entity.getName());
        entity.setSurname(profile.getSurname()!=null?profile.getSurname(): entity.getSurname());
        entity.setEmail(profile.getEmail()!=null?profile.getEmail(): entity.getEmail());
        entity.setPhone(profile.getPhone()!=null?profile.getPhone(): entity.getPhone());
        entity.setPassword(profile.getPassword()!=null?(MD5Util.getMd5(profile.getPassword())): entity.getPassword());
        entity.setStatus(profile.getStatus()!=null?profile.getStatus(): entity.getStatus());
        entity.setRole(profile.getRole()!=null?profile.getRole(): entity.getRole());
        profileRepository.save(entity);
        return true;
    }

    public ProfileDTO updateUser(Integer id, ProfileUpdateDTO dto) {
        ProfileEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        ProfileEntity saved = profileRepository.save(entity);
        return toDTO(saved);
    }

    public Boolean delete(Integer id) {
        ProfileEntity entity = get(id);
        profileRepository.delete(entity);
        return true;
    }

    public Page<ProfileDTO> filter(ProfileFilterDTO dto, Integer pageNumber, Integer pageSize) {
        FilterResponseDTO<ProfileEntity> filterResponse = customRepository.filter(dto, pageNumber, pageSize);
        List<ProfileDTO> dtoList = filterResponse.getContent().stream()
                .map(this::toDTO)
                .toList();
        Long totalCount = filterResponse.getTotalCount();
        return new PageImpl<>(dtoList, PageRequest.of(pageNumber, pageSize), totalCount);
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("profile not found"));
    }

    private ProfileEntity toEntity(ProfileCreateDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        return entity;
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
