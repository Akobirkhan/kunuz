package kunuz.service;

import kunuz.dto.auth.LoginDTO;
import kunuz.dto.auth.RegistrationByPhoneDTO;
import kunuz.dto.auth.RegistrationDTO;
import kunuz.entity.ProfileEntity;
import kunuz.entity.SmsHistoryEntity;
import kunuz.enums.ProfileRole;
import kunuz.enums.ProfileStatus;
import kunuz.exp.AppBadException;
import kunuz.repository.ProfileRepository;
import kunuz.repository.SmsHistoryRepository;
import kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private SmsSenderService smsSenderService;
    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        // send email
        String url = "http://localhost:8080/auth/verification/" + entity.getId();
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please click button  below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        emailSenderService.send(dto.getEmail(), "Complete registration", text);
        return "To complete your registration please verify your email.";

    }

    public String registrationByPhone(RegistrationByPhoneDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            throw new AppBadException("Phone already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setEmail(dto.getPhone());
        entity.setPassword(dto.getCode());

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        // send sms

        String url = "http://localhost:8080/auth/verification/" + entity.getId();
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please click button  below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        emailSenderService.send(dto.getPhone(), "Complete registration", text);
        return "To complete your registration please verify your email.";

    }

    public String authorizationVerification(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }

    public String login(LoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if(entity.getStatus().equals(ProfileStatus.NOT_ACTIVE)||entity.getStatus().equals(ProfileStatus.REGISTRATION)){
            throw new AppBadException("User should be  active");
        }
        return "Success";
    }

    public String authorizationVerificationPhone(RegistrationByPhoneDTO request) {
        SmsHistoryEntity optional = smsHistoryRepository.findByPhone(request.getPhone());
        if (optional==null) {
            throw new AppBadException("Code jonatilmagan not found");
        }
        if (LocalDateTime.now().minusMinutes(3).isBefore(optional.getCreatedDate())){
            ProfileEntity byPhone = profileRepository.findByPhone(request.getPhone());
            if (byPhone!=null){
                profileRepository.updateStatus(byPhone.getId(), ProfileStatus.ACTIVE);
                return "Success";
            }
        }
        return "Fail";

    }
}
