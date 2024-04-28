package com.example.clear;


import com.example.clear.Dao.CustomerJpaRepository;
import com.example.clear.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;






import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CustomerJpaRepositoryTest {
  @Autowired
  private CustomerJpaRepository repository;

  @Test
  public void testFindAll() {
    List<Customer> employees = repository.findAll();
    assertNotNull(employees);
    assertEquals(1, employees.size());
  }


}
