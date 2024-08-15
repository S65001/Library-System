package maids.cc.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
