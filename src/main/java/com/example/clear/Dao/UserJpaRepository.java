package com.example.clear.Dao;


import com.example.clear.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

  @Query("SELECT c FROM User c WHERE (c.birthdate >= :from and c.birthdate <=  :to)")
  List<User> getCustomersByBirthdateRange(@Param("from") Date from,
                                          @Param("to") Date to);
}

