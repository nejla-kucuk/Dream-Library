package com.project.service.utils;

import com.project.entity.user.User;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.message.ErrorMessages;

import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;



@Component
@RequiredArgsConstructor
public class UserUtil{

    private final UserRepository userRepository;


    public User isUserExist(Long userId){

        return userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId)));
    }

    public boolean isBuiltIn(User user){

        if(Boolean.TRUE.equals(user.isBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        return false;

    }

    public User getAttributeUser(HttpServletRequest request){

        String email = (String) request.getAttribute("email");

        return userRepository.findByEmailEquals(email);
    }


}
