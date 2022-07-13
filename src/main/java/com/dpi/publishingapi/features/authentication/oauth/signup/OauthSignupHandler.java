package com.dpi.publishingapi.features.authentication.oauth.signup;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.user.LoginMethod;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.auth.user.UserRepository;
import com.dpi.publishingapi.data.auth.user.role.Role;
import com.dpi.publishingapi.data.auth.user.role.RoleRepository;
import com.dpi.publishingapi.data.auth.user.role.Roles;
import com.dpi.publishingapi.features.authentication.oauth.OauthRedirectGenerator;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OauthSignupHandler implements Command.Handler<OauthSignupRequest, OauthSignupResponse> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final OauthRedirectGenerator oauthRedirectGenerator;

    @Autowired
    public OauthSignupHandler(RoleRepository roleRepository, UserRepository userRepository, OauthRedirectGenerator oauthRedirectGenerator) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.oauthRedirectGenerator = oauthRedirectGenerator;
    }

    @Override
    public OauthSignupResponse handle(OauthSignupRequest oauthSignupRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String provider = token.getAuthorizedClientRegistrationId(); // Google/Facebook

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName(Roles.ROLE_USER).orElseThrow(
                () -> new CustomException("Role does not exist", HttpStatus.NOT_FOUND)));


        User user = new User(oauthSignupRequest.email(), null, userRoles, null, true, LoginMethod.valueOf(provider.toUpperCase()));
        userRepository.save(user);

        String redirectUrl = oauthRedirectGenerator.generateUrl(user);

        return new OauthSignupResponse(redirectUrl);
    }
}
