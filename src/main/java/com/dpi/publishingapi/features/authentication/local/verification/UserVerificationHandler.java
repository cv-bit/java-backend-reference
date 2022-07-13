package com.dpi.publishingapi.features.authentication.local.verification;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.auth.user.UserRepository;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserVerificationHandler implements Command.Handler<UserVerificationRequest, UserVerificationResponse> {

    private final UserRepository userRepository;

    @Autowired
    public UserVerificationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserVerificationResponse handle(UserVerificationRequest userVerificationRequest) {

        User user = userRepository.findByVerificationCode(userVerificationRequest.verificationCode()).orElseThrow(
                () -> new CustomException("User does not exist", HttpStatus.NOT_FOUND));

        if (user.isEnabled()) {
            throw new CustomException("User is already verified", HttpStatus.CONFLICT);
        }
        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);

        return new UserVerificationResponse("User successfully verified");
    }
}
