package maids.cc.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private Long id;
    private String token;
    private Long expiryDate;

}
