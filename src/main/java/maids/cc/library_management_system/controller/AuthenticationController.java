package maids.cc.library_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Operations related to user authentication and registration")
public class AuthenticationController {


    private final AuthenticationService authenticationService;


    @Operation(summary = "Register a new user",
            description = "Allows a new user to register by providing email, password, and other details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully registered the user"),
                    @ApiResponse(responseCode = "400", description = "Email already exists")
            })
    @PostMapping("/signup")
    public ResponseEntity<Employee> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        Employee registeredEmployee = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredEmployee);
    }

    @Operation(summary = "Authenticate a user",
            description = "Allows an existing user to authenticate and obtain a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully authenticated and received JWT token"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            })

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
         LoginResponse loginResponse=authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}