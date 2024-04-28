package com.example.clear.model.DTOMapper;

import com.example.clear.model.Customer;
import com.example.clear.model.DTO.CustomerDtoResponse;
import com.example.clear.service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class CustomerDtoMapperResponse extends DtoMapperFacade<Customer, CustomerDtoResponse> {
    public CustomerDtoMapperResponse() {
        super(Customer.class, CustomerDtoResponse.class);
    }

    @Override
    protected void decorateDto(CustomerDtoResponse dto, Customer entity) {

    }
}
