package com.seta.tis4.model.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Option(
        UUID value,
        String label
) {}