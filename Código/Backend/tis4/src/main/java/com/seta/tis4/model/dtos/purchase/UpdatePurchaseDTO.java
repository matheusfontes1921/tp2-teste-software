package com.seta.tis4.model.dtos.purchase;


import java.util.List;
import java.util.UUID;

public record UpdatePurchaseDTO(
        UUID id,
        List<UUID> produtoIDList
) {
}
