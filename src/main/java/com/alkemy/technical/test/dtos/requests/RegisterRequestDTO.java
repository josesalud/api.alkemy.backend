package com.alkemy.technical.test.dtos.requests;

public record RegisterRequestDTO(
        String email,
        String password,
        String name
) {
}
