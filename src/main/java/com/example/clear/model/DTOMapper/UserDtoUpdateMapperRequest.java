package com.example.clear.model.DTOMapper;


import com.example.clear.model.DTO.UserDtoUpdateRequest;
import com.example.clear.model.User;
import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;




import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;

  @Service
  public class UserDtoUpdateMapperRequest extends DtoMapperFacade<User, UserDtoUpdateRequest> {

    public UserDtoUpdateMapperRequest() {
      super(User.class, UserDtoUpdateRequest.class);
    }

    @Override
    protected void decorateEntity(User entity, UserDtoUpdateRequest dto) {

    }
  }

