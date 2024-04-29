package com.example.clear.service;

import com.example.clear.Dao.CustomerJpaRepository;

import com.example.clear.exeptionHandlers.EntityNotFoundException;
import com.example.clear.model.Customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

  private final CustomerJpaRepository customerJpaRepository;



  public List<Customer> getAllCustomers() {
    return customerJpaRepository.findAll();
  }

  public Customer createCustomer(Customer newCustomer, Integer counter)
          throws ParseException, EntityNotFoundException, IllegalAccessException{
    String dataString = ZonedDateTime.parse(newCustomer.getBirthdate().toString(),
            DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.ROOT)
    ).toString().substring(0, 10);
    LocalDate startDate = newCustomer.getBirthdate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    if (ChronoUnit.YEARS.between(startDate, LocalDate.now()) >= counter) {
      return customerJpaRepository.save(newCustomer);
    }
    return null;
  }

  public List<Customer> getCustomersByBirthdateRange(Date from, Date to)
          throws EntityNotFoundException, IllegalAccessException{
    return customerJpaRepository.getCustomersByBirthdateRange(from, to);
  }

  public Customer updateCustomer(Customer customer) throws EntityNotFoundException, IllegalAccessException{
    if (customer.getId() == null) {
      return null;
    }
    return customerJpaRepository.save(customer);
  }

  public Customer getCustomerFullInfo(Long id) throws EntityNotFoundException, IllegalAccessException{
    return customerJpaRepository.findById(id).get();
  }

  public boolean deleteCustomerById(Long id) throws EntityNotFoundException, IllegalAccessException{
    try{
      customerJpaRepository.delete(this.getCustomerFullInfo(id));
      return true;
    } catch (EntityNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Transactional
  public void updateFields(Long id, Map<Object, Object> fields) throws EntityNotFoundException, IllegalAccessException {
    Customer customer = customerJpaRepository.findById(id).get();
    if (customer.getId() != null) {
      fields.forEach((key, value) -> {
        if (Objects.equals(key, "birthdate")) {
          String dateString = (String) value;
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          try {
            Date date = dateFormat.parse(dateString);
            Field field = ReflectionUtils.findField(Customer.class, (String) key);
            if (field != null) {
              field.setAccessible(true);
              ReflectionUtils.setField(field, customer, date);
            }
          } catch (ParseException e) {
            e.printStackTrace();
          }
        } else {
          Field field = ReflectionUtils.findField(Customer.class, (String) key);
          if (field != null) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, customer, value);
          }
        }
      });
      customerJpaRepository.save(customer);
    } else {
      throw new EntityNotFoundException("User not found.");
    }
  }


}




