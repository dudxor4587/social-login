package com.sociallogin.service;

import com.sociallogin.common.ConflictException;
import com.sociallogin.common.UnAuthorizedException;
import com.sociallogin.dto.request.UserLoginRequest;
import com.sociallogin.dto.request.UserSignUpRequest;
import com.sociallogin.entity.User;
import com.sociallogin.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String getUserNameById(Long userId) {
        return userRepository.findById(userId).get().getName();
    }

    @Transactional
    public void SignUp(UserSignUpRequest signUpRequest){
        if(userRepository.findUserByEmail(signUpRequest.email()).isPresent()){
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }
        User user = new User(
                signUpRequest.name(),
                signUpRequest.email(),
                signUpRequest.password()
        );
        userRepository.save(user);
    }

    @Transactional
    public Long Login(UserLoginRequest userLoginRequest){
        User user = userRepository.findUserByEmail(userLoginRequest.email()).orElseThrow(
                () -> new UnAuthorizedException("존재하지 않는 이메일입니다.")
        );
        user.login(userLoginRequest.password());
        return user.getId();
    }
}
