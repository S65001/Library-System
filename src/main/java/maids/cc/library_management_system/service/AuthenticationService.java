package maids.cc.library_management_system.service;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.dto.LoginResponse;
import maids.cc.library_management_system.dto.LoginUserDto;
import maids.cc.library_management_system.dto.RegisterUserDto;
import maids.cc.library_management_system.dto.RegisterUserResponse;
import maids.cc.library_management_system.entity.Employee;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.EmployeeRepo;
import maids.cc.library_management_system.security.UserInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EmployeeRepo employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public RegisterUserResponse signup(RegisterUserDto input) {
        if(employeeRepository.findEmployeeByEmail(input.getEmail()).isEmpty()) {
            Employee employee = Employee.builder().email(input.getEmail())
                    .firstname(input.getFirstname())
                    .lastname(input.getLastname())
                    .password(passwordEncoder.encode(input.getPassword()))
                    .phoneNumber(input.getPhoneNumber())
                    .isEnabled(true)
                    .build();

             employee=employeeRepository.save(employee);
             return new RegisterUserResponse(employee.getId(), employee.getFirstname(), employee.getLastname(), employee.getEmail(), employee.getPhoneNumber());
        }else{
            throw new RuntimeErrorCodedException(ErrorCode.EMAIL_ALREADY_EXISTS,"user is already exist");
        }
    }

    public LoginResponse authenticate(LoginUserDto input) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        String token =jwtService.generateToken(userInfo);
        Long expiryTime=jwtService.getExpirationTime();

            return new LoginResponse(userInfo.getEmployee().getId(),token,expiryTime);



    }
}