package com.project.service.validation;


import com.project.entity.user.User;
import com.project.exception.ConflictException;
import com.project.payload.message.ErrorMessages;
import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private  final UserRepository userRepository;

    // Daha önce kayıtlı mı kontrolü?
    public void checkDuplicate( String email, String phone){

        if(userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }

        if(userRepository.existsByPhone(phone)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE, phone));
        }


    }

    // Unique mi kontrolü?

    public void checkUniqueProperties(User user, BaseUserRequest userRequest){

        String updatedEmail = "";

        String updatedPhone = "";

        boolean isChanged = false;

        if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())){

            updatedEmail= userRequest.getEmail();

            isChanged = true;

        }

        if (!user.getPhone().equalsIgnoreCase(userRequest.getPhone())){

            updatedPhone = userRequest.getPhone();

            isChanged = true;
        }


        if (isChanged){

            checkDuplicate(updatedEmail, updatedPhone);
        }
    }


}
