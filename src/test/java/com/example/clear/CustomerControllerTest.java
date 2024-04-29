package com.example.clear;


import com.example.clear.Dao.CustomerJpaRepository;
import com.example.clear.controller.CustomerController;
import com.example.clear.model.Customer;
import com.example.clear.model.DTO.CustomerDtoRequest;
import com.example.clear.model.DTO.CustomerDtoResponse;
import com.example.clear.model.DTOMapper.CustomerDtoMapperRequest;
import com.example.clear.model.DTOMapper.CustomerDtoMapperResponse;
import com.example.clear.model.DTOMapper.CustomerDtoUpdateMapperRequest;
import com.example.clear.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.hamcrest.Matchers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
  private CustomerDtoUpdateMapperRequest customerDtoUpdateMapperRequest;
  @MockBean
  private CustomerDtoMapperResponse customerDtoMapperResponse;
  @MockBean
  private CustomerDtoMapperRequest customerDtoMapperRequest;

  @TestConfiguration
  static class TestConfig{
    @Bean
    public CustomerDtoMapperResponse customerDtoMapperResponse(){
      return new CustomerDtoMapperResponse();
    }
    @Bean
    public CustomerDtoMapperRequest customerDtoMapperRequest(){
      return new CustomerDtoMapperRequest();
    }
    @Bean
    public CustomerDtoUpdateMapperRequest customerDtoUpdateMapperRequest(){
      return new CustomerDtoUpdateMapperRequest();
    }

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

    when(customerService.getAllCustomers()).thenReturn(List.of(customer));
    when(customerDtoMapperResponse.convertToDto(customer)).thenReturn(customerDto);

    this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(42)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Kris")));
  }
@Test
public void getUserFullInfoTest() throws Exception {
  Customer customer = new Customer();

  CustomerDtoResponse customerDto = new CustomerDtoResponse();
  customerDto.setId(1L);
  customerDto.setName("Kris");
  customerDto.setSurname("Kris");
  customerDto.setStringBirthdate("1999-01-01");
  customerDto.setEmail("abc@abc");

  when(customerService.getCustomerFullInfo(1L))
          .thenReturn(customer);

  when(customerDtoMapperResponse.convertToDto(customer)).thenReturn(customerDto);
  this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")
                  .contentType("application/json"))
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
          .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Kris")));
}
  @Test
  public void deleteCustomer() throws Exception {
    Customer customer= new Customer();
    customer.setId(1L);
    customer.setName("Kris");
    customer.setSurname("Kris");
    customer.setStringBirthdate("1999-01-01");
    customer.setEmail("abc@abc");

    when(customerService.deleteCustomerById(1L)).thenReturn(true);


    this.mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
  }

}


