package com.example.clear.model.DTOMapper;

import com.example.clear.model.Customer;
import com.example.clear.model.DTO.CustomerDtoRequest;
import com.example.clear.model.DTO.CustomerDtoUpdateRequest;
import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;



import com.example.clear.model.Customer;
import com.example.clear.model.DTO.CustomerDtoRequest;
import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;

  @Service
  public class CustomerDtoUpdateMapperRequest extends DtoMapperFacade<Customer, CustomerDtoUpdateRequest> {

    public CustomerDtoUpdateMapperRequest() {
      super(Customer.class, CustomerDtoUpdateRequest.class);
    }

    @Override
    protected void decorateEntity(Customer entity, CustomerDtoUpdateRequest dto) {

    }
  }

