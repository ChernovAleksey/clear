package com.example.clear;


import com.example.clear.Dao.CustomerJpaRepository;
import com.example.clear.controller.CustomerController;
import com.example.clear.model.Customer;
import com.example.clear.model.DTO.CustomerDtoResponse;
import com.example.clear.model.DTOMapper.CustomerDtoMapperRequest;
import com.example.clear.model.DTOMapper.CustomerDtoMapperResponse;
import com.example.clear.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.hamcrest.Matchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;


  @MockBean
  private CustomerJpaRepository customerJpaRepository;


  @MockBean
  private CustomerDtoMapperResponse customerDtoMapperResponse;

  @TestConfiguration
  static class TestConfig{
  }

  @Test
  public void getAllUsers() throws Exception {
    Customer customer = new Customer();

    CustomerDtoResponse customerDto = new CustomerDtoResponse();
    customerDto.setId(42L);
    customerDto.setName("Kris");
    customerDto.setSurname("Kris");
    customerDto.setStringBirthdate("1999-01-01");
    customerDto.setEmail("abc@abc");

    System.out.println(customerDto);
    when(customerService.getAllCustomers()).thenReturn(List.of(customer));
    when(customerDtoMapperResponse.convertToDto(customer)).thenReturn(customerDto);

    this.mockMvc.perform(MockMvcRequestBuilders.get("/customers").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(42L)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Kris")));
  }

  @Test
  public void getAllUsersTest() throws Exception {
    Customer customerExpected = new Customer();
    customerExpected.setId(1L);
    customerExpected.setName("Ivan");
    customerExpected.setSurname("Petren");
    customerExpected.setStringBirthdate("1990-01-01");
    customerExpected.setEmail("abc@abc");
    when(customerService.getAllCustomers())
            .thenReturn(List.of(customerExpected));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Ivan")));

  }
}






