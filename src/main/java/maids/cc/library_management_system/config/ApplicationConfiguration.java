package maids.cc.library_management_system.config;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.EmployeeRepo;
import maids.cc.library_management_system.security.EmailPasswordAuthenticationProvider;
import maids.cc.library_management_system.security.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final EmployeeRepo employeeRepo;


    @Bean
    UserDetailsService userDetailsService() {
        return username -> new UserInfo(employeeRepo.findEmployeeByEmail(username)
                .orElseThrow(() -> new RuntimeErrorCodedException(ErrorCode.USER_NOT_FOUND_EXCEPTION,"User not found"))) ;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        return new EmailPasswordAuthenticationProvider(userDetailsService(),passwordEncoder());
    }
}
