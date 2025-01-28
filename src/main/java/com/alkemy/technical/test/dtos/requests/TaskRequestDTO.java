package com.alkemy.technical.test.dtos.requests;

public record TaskRequestDTO(
        Long id,
        String name,
        String description
) {
}
