package com.example.clear;



import com.example.clear.Dao.UserJpaRepository;
import com.example.clear.exeptionHandlers.EntityNotFoundException;
import com.example.clear.model.User;
import com.example.clear.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserJpaRepository customerJpaRepository;


  @InjectMocks
  private UserService customerService;

  @Captor
  private ArgumentCaptor<User> customerArgumentCaptor;

  @Test
  public void getAllCustomers() {
    User customer = new User();
    when(customerJpaRepository.findAll())
            .thenReturn(List.of(customer));
    List<User> customers = customerService.getAllCustomers();

    assertEquals(customer, customers.get(0));
  }
  @Test
  public void createCustomer() throws ParseException, EntityNotFoundException, IllegalAccessException {
    User customerExpected = new User();
    customerExpected.setStringBirthdate("1999-01-01");
    when(customerJpaRepository.save(customerExpected))
            .thenReturn(customerExpected);
    User customerActual = customerService.createCustomer(customerExpected, 18);
    assertEquals(customerExpected, customerActual);

    verify(customerJpaRepository).save(customerArgumentCaptor.capture());
    User customerActualArgument = customerArgumentCaptor.getValue();
    assertEquals(customerExpected, customerActualArgument);
  }

  @Test
  public void updateCustomer() throws ParseException, EntityNotFoundException, IllegalAccessException {
    User customerExpected = new User();
    customerExpected.setId(1L);
    customerExpected.setStringBirthdate("1999-01-01");
    when(customerJpaRepository.save(customerExpected))
            .thenReturn(customerExpected);
    User customerActual = customerService.updateCustomer(customerExpected);
    assertEquals(customerExpected, customerActual);

    verify(customerJpaRepository).save(customerArgumentCaptor.capture());
    User customerActualArgument = customerArgumentCaptor.getValue();
    assertEquals(customerExpected, customerActualArgument);
  }

  @Test
  public void deleteCustomerById() throws ParseException, IllegalAccessException, EntityNotFoundException {
    User customerExpected = new User();
    customerExpected.setStringBirthdate("1999-01-01");
    when(customerJpaRepository.findById(1L))
            .thenReturn(Optional.of(customerExpected));
    customerService.deleteCustomerById(1L);

    verify(customerJpaRepository).delete(customerArgumentCaptor.capture());
    User customerActualArgument = customerArgumentCaptor.getValue();
    assertEquals(customerExpected, customerActualArgument);
  }
}
