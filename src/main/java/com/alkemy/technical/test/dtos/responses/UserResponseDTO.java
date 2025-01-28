package com.alkemy.technical.test.dtos.responses;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        Boolean status

) {
}
