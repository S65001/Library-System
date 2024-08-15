package maids.cc.library_management_system.security;

import lombok.AllArgsConstructor;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;



@AllArgsConstructor
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {
    UserDetailsService userDetailsService;
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String email = (String)authentication.getPrincipal();
        UserInfo userDetails;
        try {
            userDetails = (UserInfo) userDetailsService.loadUserByUsername(email);
        }
        catch (UsernameNotFoundException ex)
        {
            throw new RuntimeErrorCodedException(ErrorCode.AUTHENTICATION_EXCEPTION,"the email or password is incorrect");
        }


        if(!passwordEncoder.matches((String)authentication.getCredentials(), userDetails.getPassword()))
            throw new RuntimeErrorCodedException(ErrorCode.AUTHENTICATION_EXCEPTION,"the email or password is incorrect");

        if(!userDetails.isEnabled())
            throw new RuntimeErrorCodedException(ErrorCode.DEACTIVATED_ACCOUNT,"This employee's account is deactivated.");


        Authentication authenticationResult = new UsernamePasswordAuthenticationToken(userDetails.getEmployee(),null,null);

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
