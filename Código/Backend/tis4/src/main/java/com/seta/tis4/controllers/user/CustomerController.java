package com.seta.tis4.controllers.user;

import com.seta.tis4.model.dtos.customer.EditCustomerDTO;
import com.seta.tis4.model.dtos.customer.CreateCustomerDTO;
import com.seta.tis4.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return customerService.get();
    }

    @GetMapping("/options")
    public ResponseEntity<?> getName() {
        return customerService.options();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCompany(@RequestBody CreateCustomerDTO dto) {
        return customerService.create(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCompany(@RequestBody EditCustomerDTO dto) {
        return customerService.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return customerService.delete(id);
    }
}
