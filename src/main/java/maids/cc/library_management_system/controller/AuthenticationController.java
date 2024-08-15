package maids.cc.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.dto.LoginResponse;
import maids.cc.library_management_system.dto.LoginUserDto;
import maids.cc.library_management_system.dto.RegisterUserDto;
import maids.cc.library_management_system.entity.Employee;
import maids.cc.library_management_system.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<Employee> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        Employee registeredEmployee = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredEmployee);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
         LoginResponse loginResponse=authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}