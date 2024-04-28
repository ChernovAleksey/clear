package com.example.clear.model.DTOMapper;

import com.example.clear.model.Customer;
import com.example.clear.model.DTO.CustomerDtoRequest;
import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class CustomerDtoMapperRequest extends DtoMapperFacade<Customer, CustomerDtoRequest> {

    public CustomerDtoMapperRequest() {
        super(Customer.class, CustomerDtoRequest.class);
    }

    @Override
    protected void decorateEntity(Customer entity, CustomerDtoRequest dto) {

    }
}
