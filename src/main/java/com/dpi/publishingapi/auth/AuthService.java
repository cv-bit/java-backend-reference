package com.dpi.publishingapi.auth;

import com.dpi.publishingapi.auth.dtos.response.LoginResponse;
import com.dpi.publishingapi.auth.user.User;
import com.dpi.publishingapi.auth.user.UserRepository;
import com.dpi.publishingapi.exceptions.CustomException;
import com.dpi.publishingapi.security.jwt.JwtUtils;
import com.dpi.publishingapi.security.role.Role;
import com.dpi.publishingapi.security.role.RoleRepository;
import com.dpi.publishingapi.security.role.Roles;
import com.dpi.publishingapi.security.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public void userSignup(String email, String password) throws CustomException {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException("Email is already taken", HttpStatus.CONFLICT);
        }
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName(Roles.ROLE_USER).orElseThrow(
                () -> new CustomException("Role does not exist", HttpStatus.NOT_FOUND)));
        User user = new User(email, passwordEncoder.encode(password), userRoles);
        userRepository.save(user);
    }

    public LoginResponse userLogin(String email, String password) throws CustomException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new LoginResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
    }
}
