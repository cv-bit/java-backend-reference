package com.dpi.publishingapi.features.authentication.local.signup;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.user.LoginMethod;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.auth.user.UserRepository;
import com.dpi.publishingapi.data.auth.user.role.Role;
import com.dpi.publishingapi.data.auth.user.role.RoleRepository;
import com.dpi.publishingapi.data.auth.user.role.Roles;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import com.dpi.publishingapi.infrastructure.mail.MailJetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class UserSignupHandler implements Command.Handler<UserSignupRequest, UserSignupResponse> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailJetService mailJetService;

    @Autowired
    public UserSignupHandler(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MailJetService mailJetService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailJetService = mailJetService;
    }

    @Override
    public UserSignupResponse handle(UserSignupRequest userSignupRequest) {

        String email = userSignupRequest.email();
        String password = userSignupRequest.password();

        if (userRepository.existsByEmail(email)) {
            throw new CustomException("Email is already taken", HttpStatus.CONFLICT);
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName(Roles.ROLE_USER).orElseThrow(
                () -> new CustomException("Role does not exist", HttpStatus.NOT_FOUND)));

        Long verificationCode = new Random().nextLong(999999);
        User user = new User(email, passwordEncoder.encode(password), userRoles, verificationCode, false, LoginMethod.LOCAL);
        userRepository.save(user);
        mailJetService.sendVerificationEmail(user.getEmail(), verificationCode);

        return new UserSignupResponse("User successfully created");
    }
}
