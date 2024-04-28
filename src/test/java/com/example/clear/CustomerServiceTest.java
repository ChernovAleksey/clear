package com.example.clear;


import com.example.clear.Dao.CustomerJpaRepository;
import com.example.clear.model.Customer;
import com.example.clear.service.CustomerService;
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
import static com.example.clear.PropSource.AGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

  @Mock
  private CustomerJpaRepository customerJpaRepository;


  @InjectMocks
  private CustomerService customerService;

  @Captor
  private ArgumentCaptor<Customer> customerArgumentCaptor;

  @Test
  public void getAllCustomers() {
    Customer customer = new Customer();
    when(customerJpaRepository.findAll())
            .thenReturn(List.of(customer));
    List<Customer> customers = customerService.getAllCustomers();

    assertEquals(customer, customers.get(0));
  }
  @Test
  public void createCustomer() throws ParseException, EntityNotFoundException, IllegalAccessException {
    Customer customerExpected = new Customer();
    customerExpected.setStringBirthdate("1999-01-01");
    when(customerJpaRepository.save(customerExpected))
            .thenReturn(customerExpected);
    Customer customerActual = customerService.createCustomer(customerExpected, 18);
    assertEquals(customerExpected, customerActual);

    verify(customerJpaRepository).save(customerArgumentCaptor.capture());
    Customer customerActualArgument = customerArgumentCaptor.getValue();
    assertEquals(customerExpected, customerActualArgument);
  }

  @Test
  public void updateCustomer() throws ParseException, EntityNotFoundException, IllegalAccessException {
    Customer customerExpected = new Customer();
    customerExpected.setId(1L);
    customerExpected.setStringBirthdate("1999-01-01");
    when(customerJpaRepository.save(customerExpected))
            .thenReturn(customerExpected);
    Customer customerActual = customerService.updateCustomer(customerExpected);
    assertEquals(customerExpected, customerActual);

    verify(customerJpaRepository).save(customerArgumentCaptor.capture());
    Customer customerActualArgument = customerArgumentCaptor.getValue();
    assertEquals(customerExpected, customerActualArgument);
  }

  @Test
  public void deleteCustomerById() throws ParseException, IllegalAccessException, EntityNotFoundException {
    Customer customerExpected = new Customer();
    customerExpected.setStringBirthdate("1999-01-01");
    when(customerJpaRepository.findById(1L))
            .thenReturn(Optional.of(customerExpected));
    customerService.deleteCustomerById(1L);

    verify(customerJpaRepository).delete(customerArgumentCaptor.capture());
    Customer customerActualArgument = customerArgumentCaptor.getValue();
    assertEquals(customerExpected, customerActualArgument);
  }
}
