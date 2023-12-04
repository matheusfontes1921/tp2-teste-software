package com.seta.tis4.model.dtos.purchase;

import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.entities.produto.Produto;
import com.seta.tis4.model.entities.purchase.Purchase;
import com.seta.tis4.model.entities.user.customer.Customer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record CreatePurchaseDTO(
        @NotEmpty UUID customerId,
        @NotEmpty UUID employeeId,
        @NotEmpty List<UUID> produtoIDList,
        @NotEmpty String status
) {
    public Purchase toPurchase(List<Produto> produtoList, Customer customer, Employee employee) {
        Purchase purchase = new Purchase();

        purchase.setCustomer(customer);
        purchase.setEmployee(employee);
        purchase.getProducts().addAll(produtoList);

        LocalDateTime date = LocalDateTime.now();

        purchase.setDate(date);

        return purchase;
    }
}
