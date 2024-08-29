package maids.cc.library_management_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import maids.cc.library_management_system.dto.LoginResponse;
import maids.cc.library_management_system.dto.LoginUserDto;
import maids.cc.library_management_system.dto.RegisterUserDto;
import maids.cc.library_management_system.dto.RegisterUserResponse;
import maids.cc.library_management_system.entity.Employee;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.EmployeeRepo;
import maids.cc.library_management_system.security.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthenticationServiceTest {

    @Mock
    private EmployeeRepo employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSignupSuccessfullyWhenEmailDoesNotExist() {
        // given
        RegisterUserDto input = new RegisterUserDto("Ahmed", "Ayman", "ahmed1977@gmail.com", "password", "1234567890");
        Employee employee = Employee.builder()
                .id(1L)
                .email(input.getEmail())
                .firstname(input.getFirstname())
                .lastname(input.getLastname())
                .password(passwordEncoder.encode(input.getPassword()))
                .phoneNumber(input.getPhoneNumber())
                .isEnabled(true)
                .build();

        when(employeeRepository.findEmployeeByEmail(input.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(input.getPassword())).thenReturn("encodedPassword");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // when
        RegisterUserResponse response = authenticationService.signup(input);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(input.getFirstname(), response.getFirstname());
        assertEquals(input.getLastname(), response.getLastname());
        assertEquals(input.getEmail(), response.getEmail());
        assertEquals(input.getPhoneNumber(), response.getPhoneNumber());
    }

    @Test
    public void shouldThrowErrorWhenEmailAlreadyExists() {
        // given
        RegisterUserDto input = new RegisterUserDto("Ahmed", "Ayman", "ahmed1977@gmail.com", "password", "1234567890");
        Employee existingEmployee = new Employee();
        existingEmployee.setEmail(input.getEmail());

        when(employeeRepository.findEmployeeByEmail(input.getEmail())).thenReturn(Optional.of(existingEmployee));

        // when & then
        RuntimeErrorCodedException exception = assertThrows(RuntimeErrorCodedException.class, () -> {
            authenticationService.signup(input);
        });

        assertEquals(ErrorCode.EMAIL_ALREADY_EXISTS, exception.getErrorCode());
        assertEquals("user is already exist", exception.getMessage());
    }

    @Test
    public void shouldAuthenticateSuccessfullyWithValidCredentials() {
        // given
        LoginUserDto input = new LoginUserDto("ali_tarek@gmail.com", "password");
        Employee employee = new Employee();
        employee.setId(1L);
        UserInfo userInfo = new UserInfo(employee);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(userInfo)).thenReturn("generatedToken");
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        // when
        LoginResponse response = authenticationService.authenticate(input);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("generatedToken", response.getToken());
        assertEquals(3600L, response.getExpiryDate());
    }

    @Test
    public void shouldThrowErrorWhenInvalidCredentials() {
        // given
        LoginUserDto input = new LoginUserDto("ahmed_youssef@gmail.com", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("Invalid username or password"));

        // when & then
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.authenticate(input);
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }
}
