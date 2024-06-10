package kunuz.util;

import jakarta.servlet.http.HttpServletRequest;
import kunuz.dto.auth.JwtDTO;
import kunuz.enums.ProfileRole;
import kunuz.exp.AppForbiddenException;

public class HttpRequestUtil {
        public static JwtDTO getJwtDTO(HttpServletRequest request) {
            Integer id = (Integer) request.getAttribute("id");
            ProfileRole role = (ProfileRole) request.getAttribute("role");
            String username = (String) request.getAttribute("username");
            JwtDTO dto = new JwtDTO(id, username,role);
            return dto;
        }
    public static JwtDTO getJwtDTO(HttpServletRequest request, ProfileRole requiredRole) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        String username = (String) request.getAttribute("username");
        JwtDTO dto = new JwtDTO(id, username, role);

        if (!dto.getRole().equals(requiredRole)) {
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }
}
