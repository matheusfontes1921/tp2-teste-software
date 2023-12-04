package com.seta.tis4.controllers;

import com.seta.tis4.model.dtos.purchase.CreatePurchaseDTO;
import com.seta.tis4.model.dtos.purchase.UpdatePurchaseDTO;
import com.seta.tis4.model.services.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/venda/")
public class PurchaseController {
    public PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("get")
    public ResponseEntity<?> get() {
        return purchaseService.get();
    }

    @GetMapping("getCompany/{companyID}")
    public ResponseEntity<?> getByCompanyID(@PathVariable UUID companyID) {
        return purchaseService.getByCustomerID(companyID);
    }

    @GetMapping("getEmployee/{employeeID}")
    public ResponseEntity<?> getByEmployeeID(@PathVariable UUID employeeID) {
        return purchaseService.getByEmployeeID(employeeID);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CreatePurchaseDTO dto) {
        return purchaseService.create(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdatePurchaseDTO dto) {
        return purchaseService.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return purchaseService.delete(id);
    }

    @DeleteMapping("delete/{id}/{produtoId}")
    public ResponseEntity<?> deleteProduto(@PathVariable UUID id, @PathVariable UUID produtoId) {
        return purchaseService.deleteProduto(id, produtoId);
    }

    @GetMapping("generateReport/{purchaseId}")
    public ResponseEntity<?> gerarRelatorio(@PathVariable UUID purchaseId) {
        return this.purchaseService.generateReport(purchaseId);
    }
}
