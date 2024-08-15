package maids.cc.library_management_system.service;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.dto.LoginResponse;
import maids.cc.library_management_system.dto.LoginUserDto;
import maids.cc.library_management_system.dto.RegisterUserDto;
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


    public Employee signup(RegisterUserDto input) {
        if(employeeRepository.findEmployeeByEmail(input.getEmail()).isEmpty()) {
            Employee employee = Employee.builder().email(input.getEmail())
                    .firstname(input.getFirstname())
                    .lastname(input.getLastname())
                    .password(passwordEncoder.encode(input.getPassword()))
                    .phoneNumber(input.getPhoneNumber())
                    .isEnabled(true)
                    .build();

            return employeeRepository.save(employee);
        }else{
            throw new RuntimeErrorCodedException(ErrorCode.EMAIL_ALREADY_EXISTS,"user is already exist");
        }
    }

    public LoginResponse authenticate(LoginUserDto input) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

            UserInfo user = (UserInfo) authentication.getPrincipal();

            return new LoginResponse(user.getEmployee().getId(),jwtService.generateToken(user),jwtService.getExpirationTime());



    }
}