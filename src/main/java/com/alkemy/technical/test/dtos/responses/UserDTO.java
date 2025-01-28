package com.alkemy.technical.test.dtos.responses;

public record UserDTO(
        Long id,
        String name,
        String email,
        Boolean status
) {
}
