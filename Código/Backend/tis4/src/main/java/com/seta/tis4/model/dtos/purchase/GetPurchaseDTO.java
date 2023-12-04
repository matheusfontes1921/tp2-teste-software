package com.seta.tis4.model.dtos.purchase;

import com.seta.tis4.model.entities.produto.Produto;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GetPurchaseDTO(
        UUID id,
        String customerName,
        String employeeName,
        String status,
        String date
) {
}
