package com.alkemy.technical.test.dtos.responses;

public record TaskResponseDTO(
        Long id,
        String name,
        String description,
        Boolean status
) {
}
