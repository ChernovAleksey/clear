package com.example.clear.controller;


import com.example.clear.Dao.CustomerJpaRepository;
import com.example.clear.exeptionHandlers.EntityNotFoundException;
import com.example.clear.model.DTO.CustomerDtoRequest;
import com.example.clear.model.DTO.CustomerDtoResponse;
import com.example.clear.model.DTO.CustomerDtoUpdateRequest;
import com.example.clear.model.DTOMapper.CustomerDtoMapperRequest;
import com.example.clear.model.DTOMapper.CustomerDtoMapperResponse;
import com.example.clear.model.DTOMapper.CustomerDtoUpdateMapperRequest;
import com.example.clear.service.CustomerService;
import com.example.clear.model.Customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.clear.config.PropSource.AGE;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDtoMapperResponse customerDtoMapperResponse;
    private final CustomerDtoMapperRequest customerDtoMapperRequest;
    private final CustomerDtoUpdateMapperRequest customerDtoUpdateMapperRequest;

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(customerService.getAllCustomers().stream()
                .map(customerDtoMapperResponse::convertToDto)
                .collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserFullInfo(@PathVariable Long id) throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(customerDtoMapperResponse.convertToDto(customerService.getCustomerFullInfo(id)));
    }
    @PostMapping("/")
    public  ResponseEntity<?>  postNewUser(@RequestBody @Valid CustomerDtoRequest customerDtoRequest)
            throws EntityNotFoundException, IllegalAccessException, ParseException {
        Customer customer = customerDtoMapperRequest.convertToEntity(customerDtoRequest);

        return customerService.createCustomer(customer, AGE)!= null
                ? ResponseEntity.ok(customerService.createCustomer(customer, AGE))
                : ResponseEntity.badRequest().body("Customer couldn't be registered");
    }
    @PutMapping("/")
    public CustomerDtoResponse updateUser(@RequestBody @Valid CustomerDtoUpdateRequest customerDtoUpdateRequest)
            throws EntityNotFoundException, IllegalAccessException{
        Customer customer = customerDtoUpdateMapperRequest.convertToEntity(customerDtoUpdateRequest);
        return customerDtoMapperResponse.convertToDto(customerService.updateCustomer(customer));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @Min(0) Long id, @RequestBody Map<Object, Object> fields)
            throws EntityNotFoundException, IllegalAccessException {
        customerService.updateFields(id, fields);
        return ResponseEntity.status(204).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id)
            throws EntityNotFoundException, IllegalAccessException{
        return customerService.deleteCustomerById(id) ? ResponseEntity.ok("Success") : ResponseEntity.badRequest().body("Customer not found");
    }
    private final CustomerJpaRepository customerJpaRepository ;
    @GetMapping("/search/{from}/{to}")
    public ResponseEntity<?> getCustomersByBirthdateRange(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
                                                          @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date  to)
            throws EntityNotFoundException, IllegalAccessException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu" , Locale.ROOT);
        LocalDate startDate = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(startDate.isAfter(endDate)) {return ResponseEntity.badRequest().body("Date from is after date to!");}
        return ResponseEntity.ok(customerService.getCustomersByBirthdateRange(from,  to).stream()
                .map(customerDtoMapperResponse::convertToDto)
                .collect(Collectors.toList()));
}

}
