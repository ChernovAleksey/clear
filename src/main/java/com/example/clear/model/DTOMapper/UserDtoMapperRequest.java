package com.example.clear.model.DTOMapper;


import com.example.clear.model.DTO.UserDtoRequest;
import com.example.clear.model.User;
import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapperRequest extends DtoMapperFacade<User, UserDtoRequest> {

    public UserDtoMapperRequest() {
        super(User.class, UserDtoRequest.class);
    }

    @Override
    protected void decorateEntity(User entity, UserDtoRequest dto) {

    }
}
