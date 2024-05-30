package kunuz.util;

import kunuz.dto.auth.JwtDTO;
import kunuz.enums.ProfileRole;
import kunuz.exp.AppForbiddenException;

import java.util.List;

public class SecurityUtil {
    public static JwtDTO getJwtDTO(String token) {
        String jwt = token.substring(7); // Bearer
        JwtDTO dto = JWTUtil.decode(jwt);
        return dto;
    }

    public static JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
        JwtDTO dto = getJwtDTO(token);
        if(!dto.getRole().equals(requiredRole)){
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }

    public static void getJwtDTO(String token, List<ProfileRole> requiredRole){
        JwtDTO dto = getJwtDTO(token);
        for (ProfileRole role:requiredRole){
            if(role==dto.getRole()){
                return;
            }
        }
        throw new AppForbiddenException("Method not allowed");

//        Optional<ProfileRole> any = requiredRole.stream().filter(role -> role == dto.getRole()).findAny();
//        if (any.isEmpty()){
//            throw new AppForbiddenException("Method not allowed");
//        }
//        return dto;
    }
}
