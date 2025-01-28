package com.alkemy.technical.test.repository;

import com.alkemy.technical.test.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    @Override
    Optional<Users> findById(Long aLong);

    List<Users> findByStatus(Boolean status);
}
