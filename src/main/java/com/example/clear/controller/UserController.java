package com.example.clear.controller;



import com.example.clear.Dao.UserJpaRepository;
import com.example.clear.exeptionHandlers.EntityNotFoundException;
import com.example.clear.model.DTO.UserDtoRequest;
import com.example.clear.model.DTO.UserDtoResponse;
import com.example.clear.model.DTO.UserDtoUpdateRequest;
import com.example.clear.model.DTOMapper.UserDtoMapperRequest;
import com.example.clear.model.DTOMapper.UserDtoMapperResponse;
import com.example.clear.model.DTOMapper.UserDtoUpdateMapperRequest;
import com.example.clear.model.User;
import com.example.clear.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService customerService;
    private final UserDtoMapperResponse customerDtoMapperResponse;
    private final UserDtoMapperRequest customerDtoMapperRequest;
    private final UserDtoUpdateMapperRequest customerDtoUpdateMapperRequest;

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
    public  ResponseEntity<?>  postNewUser(@RequestBody @Valid UserDtoRequest customerDtoRequest)
            throws EntityNotFoundException, IllegalAccessException, ParseException {
        User customer = customerDtoMapperRequest.convertToEntity(customerDtoRequest);

        return customerService.createCustomer(customer, AGE)!= null
                ? ResponseEntity.ok(customerService.createCustomer(customer, AGE))
                : ResponseEntity.badRequest().body("Customer couldn't be registered");
    }
    @PutMapping("/")
    public UserDtoResponse updateUser(@RequestBody @Valid UserDtoUpdateRequest customerDtoUpdateRequest)
            throws EntityNotFoundException, IllegalAccessException{
        User customer = customerDtoUpdateMapperRequest.convertToEntity(customerDtoUpdateRequest);
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
    private final UserJpaRepository customerJpaRepository ;
    @GetMapping("/search/{from}/{to}")
    public ResponseEntity<?> getCustomersByBirthdateRange(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
                                                          @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date  to)
            throws EntityNotFoundException, IllegalAccessException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu" , Locale.ROOT);
        LocalDate startDate = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(startDate);
        LocalDate endDate = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(endDate);
        if(startDate.isAfter(endDate)) {return ResponseEntity.badRequest().body("Date from is after date to!");}
        return ResponseEntity.ok(customerService.getCustomersByBirthdateRange(from,  to).stream()
                .map(customerDtoMapperResponse::convertToDto)
                .collect(Collectors.toList()));
}

}
