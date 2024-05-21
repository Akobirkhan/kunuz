package kunuz.service;

import kunuz.dto.ProfileCreateDTO;
import kunuz.dto.ProfileDTO;
import kunuz.entity.ProfileEntity;
import kunuz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    public ProfileDTO create(ProfileCreateDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());

        profileRepository.save(entity);
        return toDTO(entity);
    }

    public ProfileDTO toDTO(ProfileEntity entity){
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
