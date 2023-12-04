package com.seta.tis4.model.services;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.seta.tis4.model.dtos.Option;
import com.seta.tis4.model.dtos.customer.EditCustomerDTO;
import com.seta.tis4.model.dtos.customer.CreateCustomerDTO;
import com.seta.tis4.model.entities.purchase.Purchase;
import com.seta.tis4.model.entities.user.customer.Customer;
import com.seta.tis4.model.entities.user.customer.CustomerType;
import com.seta.tis4.model.repositories.CustomerRepository;
import com.seta.tis4.model.repositories.PurchaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    public CustomerService(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public ResponseEntity<?> get() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    public ResponseEntity<?> options() {
        List<Customer> customerList = this.customerRepository.findAll();
        if(customerList.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe clientes cadastrados");
        }

        List<Option> options = new ArrayList<>();

        customerList.forEach(customer -> options.add(Option.builder()
                        .value(customer.getId())
                        .label(customer.getNome())
                .build()));

        return ResponseEntity.ok(options);
    }

    public ResponseEntity<?> create(CreateCustomerDTO dto) {
        if (!isValidate(dto.cpfCnpj())) {
            return ResponseEntity.badRequest().body("CPF ou CNPJ inválido");
        }
        if (existsByEmail(dto.email())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        if(existsByCpfCnpj(dto.cpfCnpj())){
            return ResponseEntity.badRequest().body("CNPJ já cadastrado");
        }
        if(existsByTelefone(dto.telefone())){
            return ResponseEntity.badRequest().body("Telefone já cadastrado");
        }

        CustomerType customerType;

        if(dto.cpfCnpj().replaceAll("[^0-9]", "").length() > 11) {
            customerType = CustomerType.PJ;
        } else {
            customerType = CustomerType.PF;
        }

        Customer customer = Customer.builder()
                .cpfCnpj(dto.cpfCnpj())
                .customerType(customerType)
                .email(dto.email())
                .endereco(dto.endereco())
                .nome(dto.nome())
                .telefone(dto.telefone())
                .build();

        return ResponseEntity.ok().body(customerRepository.save(customer));
    }


    public ResponseEntity<?> update(EditCustomerDTO dto) {
        Optional<Customer> customerOptional = customerRepository.findById(dto.id());

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (existsByEmail(dto.email()) && !customer.getEmail().equals(dto.email()))
                return ResponseEntity.badRequest().body("Email já cadastrado");
            customer.setEndereco(dto.endereco());
            customer.setTelefone(dto.telefone());
            customer.setEmail(dto.email());

            return ResponseEntity.ok().body(customerRepository.saveAndFlush(customer));
        }
        return ResponseEntity.badRequest().body("Cliente não encontrada");
    }

    public ResponseEntity<?> delete(UUID id) {

        if (customerRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um cliente com esse id");
        } else {
            List<Purchase> purchaseList = purchaseRepository.findPurchaseByCustomer_Id(id);
            purchaseList.forEach(purchase -> {
                purchaseRepository.deleteById(purchase.getId());
            });

            customerRepository.deleteById(id);
            return ResponseEntity.ok().body("Cliente deletada com sucesso");
        }
    }

    //region Private Methods

    /**
     * @param cpf_cnpj CPF ou CNPJ no formato:
     *                 CPF: 000.000.000-00 ou 00000000000
     *                 CNPJ: 00.000.000/0000-00 ou 00000000000000
     * @return boolean true if the string is a valid CPF ou CNPJ, false otherwise
     */
    private boolean isValidate(String cpf_cnpj) {
        cpf_cnpj = cpf_cnpj.replaceAll("[^0-9]", "");
        if (cpf_cnpj.length() > 11) {
            CNPJValidator validator = new CNPJValidator();
            try {
                validator.assertValid(cpf_cnpj);
                return true;
            } catch (InvalidStateException e) {
                return false;
            }
        } else {
            CPFValidator validator = new CPFValidator();
            try {
                validator.assertValid(cpf_cnpj);
                return true;
            } catch (InvalidStateException e) {
                return false;
            }
        }


    }

    private Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
    private Boolean existsByCpfCnpj(String cpfCnpj) {
        return customerRepository.existsByCpfCnpj(cpfCnpj);
    }
    private boolean existsByTelefone(String telefone) {
        return customerRepository.existsByTelefone(telefone);
    }
}