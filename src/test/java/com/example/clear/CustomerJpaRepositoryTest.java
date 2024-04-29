package com.example.clear;


import com.example.clear.Dao.CustomerJpaRepository;
import com.example.clear.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CustomerJpaRepositoryTest {
  @Autowired
  private CustomerJpaRepository repository;

  @Test
  public void testFindAll() {
    List<Customer> customers = repository.findAll();
    assertNotNull(customers);
    assertEquals(1, customers.size());
  }

  @Test
  public void getCustomersByBirthdateRange() throws ParseException {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
    Date date1 =  format.parse("1976-01-01");
    Date date2 =  format.parse("1976-12-12");
    List<Customer> customers = repository.getCustomersByBirthdateRange(date1, date2);
    assertNotNull(customers);
    assertEquals(1, customers.size());
  }
}
