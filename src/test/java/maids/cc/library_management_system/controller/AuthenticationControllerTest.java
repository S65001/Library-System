package maids.cc.library_management_system.controller;

import maids.cc.library_management_system.dto.LoginResponse;
import maids.cc.library_management_system.dto.LoginUserDto;
import maids.cc.library_management_system.dto.RegisterUserDto;
import maids.cc.library_management_system.dto.RegisterUserResponse;
import maids.cc.library_management_system.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();

        when(authenticationService.signup(registerUserDto)).thenReturn(registerUserResponse);

        ResponseEntity<RegisterUserResponse> response = authenticationController.register(registerUserDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registerUserResponse, response.getBody());
        verify(authenticationService, times(1)).signup(registerUserDto);
    }

    @Test
    void testAuthenticate() {
        LoginUserDto loginUserDto = new LoginUserDto();
        LoginResponse loginResponse = new LoginResponse();

        when(authenticationService.authenticate(loginUserDto)).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginUserDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
        verify(authenticationService, times(1)).authenticate(loginUserDto);
    }
}
