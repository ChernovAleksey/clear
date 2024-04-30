package com.example.clear;



import com.example.clear.Dao.UserJpaRepository;
import com.example.clear.controller.UserController;
import com.example.clear.model.DTO.UserDtoResponse;
import com.example.clear.model.DTOMapper.UserDtoMapperRequest;
import com.example.clear.model.DTOMapper.UserDtoMapperResponse;
import com.example.clear.model.DTOMapper.UserDtoUpdateMapperRequest;
import com.example.clear.model.User;
import com.example.clear.service.UserService;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService customerService;


  @MockBean
  private UserJpaRepository customerJpaRepository;
  @MockBean
  private UserDtoUpdateMapperRequest customerDtoUpdateMapperRequest;
  @MockBean
  private UserDtoMapperResponse customerDtoMapperResponse;
  @MockBean
  private UserDtoMapperRequest customerDtoMapperRequest;

  @TestConfiguration
  static class TestConfig{
    @Bean
    public UserDtoMapperResponse customerDtoMapperResponse(){
      return new UserDtoMapperResponse();
    }
    @Bean
    public UserDtoMapperRequest customerDtoMapperRequest(){
      return new UserDtoMapperRequest();
    }
    @Bean
    public UserDtoUpdateMapperRequest customerDtoUpdateMapperRequest(){
      return new UserDtoUpdateMapperRequest();
    }

  }

  @Test
  public void getAllUsers() throws Exception {
    User customer = new User();

    UserDtoResponse customerDto = new UserDtoResponse();
    customerDto.setId(42L);
    customerDto.setName("Kris");
    customerDto.setSurname("Kris");
    customerDto.setStringBirthdate("1999-01-01");
    customerDto.setEmail("abc@abc");

    when(customerService.getAllCustomers()).thenReturn(List.of(customer));
    when(customerDtoMapperResponse.convertToDto(customer)).thenReturn(customerDto);

    this.mockMvc.perform(MockMvcRequestBuilders.get("/users/").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(42)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Kris")));
  }
@Test
public void getUserFullInfoTest() throws Exception {
  User customer = new User();

  UserDtoResponse customerDto = new UserDtoResponse();
  customerDto.setId(1L);
  customerDto.setName("Kris");
  customerDto.setSurname("Kris");
  customerDto.setStringBirthdate("1999-01-01");
  customerDto.setEmail("abc@abc");

  when(customerService.getCustomerFullInfo(1L))
          .thenReturn(customer);

  when(customerDtoMapperResponse.convertToDto(customer)).thenReturn(customerDto);
  this.mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                  .contentType("application/json"))
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
          .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Kris")));
}
  @Test
  public void deleteCustomer() throws Exception {
    User customer= new User();
    customer.setId(1L);
    customer.setName("Kris");
    customer.setSurname("Kris");
    customer.setStringBirthdate("1999-01-01");
    customer.setEmail("abc@abc");

    when(customerService.deleteCustomerById(1L)).thenReturn(true);


    this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
  }

}


