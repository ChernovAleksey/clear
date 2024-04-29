package com.example.clear.model.DTOMapper;


import com.example.clear.model.DTO.UserDtoResponse;
import com.example.clear.model.User;
import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapperResponse extends DtoMapperFacade<User, UserDtoResponse> {
    public UserDtoMapperResponse() {
        super(User.class, UserDtoResponse.class);
    }

    @Override
    protected void decorateDto(UserDtoResponse dto, User entity) {

    }
}
