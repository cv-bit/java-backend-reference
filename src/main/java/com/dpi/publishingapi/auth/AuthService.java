package com.dpi.publishingapi.auth;

import com.dpi.publishingapi.auth.dtos.response.LoginResponse;
import com.dpi.publishingapi.auth.user.User;
import com.dpi.publishingapi.auth.user.UserRepository;
import com.dpi.publishingapi.exceptions.CustomException;
import com.dpi.publishingapi.mail.MailJetService;
import com.dpi.publishingapi.security.jwt.JwtUtils;
import com.dpi.publishingapi.security.jwt.refresh.RefreshToken;
import com.dpi.publishingapi.security.jwt.refresh.RefreshTokenService;
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
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private final Random random;
    private final MailJetService mailJetService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       RefreshTokenService refreshTokenService,
                       Random random,
                       MailJetService mailJetService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.random = random;
        this.mailJetService = mailJetService;
    }

    public void userSignup(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException("Email is already taken", HttpStatus.CONFLICT);
        }
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName(Roles.ROLE_USER).orElseThrow(
                () -> new CustomException("Role does not exist", HttpStatus.NOT_FOUND)));
        Long verificationCode = random.nextLong(999999); // generate 6 digit verification code
        User user = new User(email, passwordEncoder.encode(password), userRoles, verificationCode, false);
        userRepository.save(user);
        sendVerificationEmail(user, verificationCode);
    }

    public LoginResponse userLogin(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return new LoginResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), roles);
    }

    private void sendVerificationEmail(User user, Long verificationCode) {
        mailJetService.sendVerificationEmail(user.getEmail(), verificationCode);
    }

    public void verifyAccount(Long verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode).orElseThrow(
                () -> new CustomException("User does not exist", HttpStatus.NOT_FOUND));
        if (user.isEnabled()) {
            throw new CustomException("User is already verified", HttpStatus.CONFLICT);
        }
        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);
    }


}
