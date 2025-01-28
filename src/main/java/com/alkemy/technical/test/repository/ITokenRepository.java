package com.alkemy.technical.test.repository;

import com.alkemy.technical.test.entities.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITokenRepository extends CrudRepository<Token,Long> {
    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long id);
}
