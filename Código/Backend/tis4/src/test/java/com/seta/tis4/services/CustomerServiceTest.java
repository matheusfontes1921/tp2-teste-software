package com.seta.tis4.services;

import com.seta.tis4.model.dtos.customer.CreateCustomerDTO;
import com.seta.tis4.model.dtos.customer.EditCustomerDTO;
import com.seta.tis4.model.entities.purchase.Purchase;
import com.seta.tis4.model.entities.user.customer.Customer;
import com.seta.tis4.model.entities.user.customer.CustomerType;
import com.seta.tis4.model.repositories.CustomerRepository;
import com.seta.tis4.model.repositories.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PurchaseRepository purchaseRepository;


    @Spy
    @InjectMocks
    private CustomerService customerService;
    private Customer customer;
    private CreateCustomerDTO createCustomerDTO;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .id(UUID.randomUUID())
                .nome("Cliente Teste")
                .telefone("123456789")
                .email("cliente@teste.com")
                .endereco("Endereço Teste")
                .cpfCnpj("57.047.475/0001-50")
                .customerType(CustomerType.PF)
                .build();

        createCustomerDTO = new CreateCustomerDTO(
                "Cliente Teste",
                "57.047.475/0001-50",
                "Endereço Teste",
                "319865474",
                "cliente@teste.com"
        );
    }


    @Test
    public void testGet() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        ResponseEntity<?> response = customerService.get();

        assertEquals(ResponseEntity.ok(Collections.singletonList(customer)), response);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testOptions() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        ResponseEntity<?> response = customerService.options();

        verify(customerRepository, times(1)).findAll();
    }


        @Test
        public void testCreate() {
            doReturn(true).when(customerService).isValidate(anyString());

            when(customerRepository.save(any(Customer.class))).thenReturn(customer);
            when(customerRepository.existsByEmail(anyString())).thenReturn(false);
            when(customerRepository.existsByCpfCnpj(anyString())).thenReturn(false);
            when(customerRepository.existsByTelefone(anyString())).thenReturn(false);

            ResponseEntity<?> response = customerService.create(createCustomerDTO);

            assertEquals(ResponseEntity.ok().body(customer), response);

            verify(customerRepository, times(1)).save(any(Customer.class));
        }

    @Test
    public void testCreateWithEmailAlreadyRegistered() {
        when(customerRepository.existsByEmail(createCustomerDTO.email())).thenReturn(true);

        ResponseEntity<?> response = customerService.create(createCustomerDTO);

        assertEquals(ResponseEntity.badRequest().body("Email já cadastrado"), response);
        verify(customerRepository, times(1)).existsByEmail(createCustomerDTO.email());
    }

    @Test
    public void testCreateWithCpfCnpjAlreadyRegistered() {
        when(customerRepository.existsByCpfCnpj(createCustomerDTO.cpfCnpj())).thenReturn(true);

        ResponseEntity<?> response = customerService.create(createCustomerDTO);

        assertEquals(ResponseEntity.badRequest().body("CNPJ já cadastrado"), response);
        verify(customerRepository, times(1)).existsByCpfCnpj(createCustomerDTO.cpfCnpj());
    }

    @Test
    public void testCreateWithTelefoneAlreadyRegistered() {
        when(customerRepository.existsByTelefone(createCustomerDTO.telefone())).thenReturn(true);

        ResponseEntity<?> response = customerService.create(createCustomerDTO);

        assertEquals(ResponseEntity.badRequest().body("Telefone já cadastrado"), response);
        verify(customerRepository, times(1)).existsByTelefone(createCustomerDTO.telefone());
    }
    @Test
    void testDeleteCustomer() {
        UUID customerId = UUID.randomUUID();

        Customer existingCustomer = Customer.builder()
                .id(customerId)
                .endereco("Endereço Cliente")
                .telefone("123456789")
                .email("cliente@teste.com")
                .cpfCnpj("57.047.475/0001-50")
                .customerType(CustomerType.PF)
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(purchaseRepository.findPurchaseByCustomer_Id(customerId)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = customerService.delete(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cliente deletada com sucesso", response.getBody());

        verify(customerRepository, times(1)).findById(customerId);
        verify(purchaseRepository, times(1)).findPurchaseByCustomer_Id(customerId);
        verify(purchaseRepository, times(0)).deleteById(any());
        verify(customerRepository, times(1)).deleteById(customerId);
    }
    @Test
    void testUpdateCustomer() {
        UUID customerId = UUID.randomUUID();
        EditCustomerDTO editCustomerDTO = new EditCustomerDTO(customerId, "Novo Endereço", "321987654", "novo@email.com");

        Customer existingCustomer = Customer.builder()
                .id(customerId)
                .endereco("Endereço Antigo")
                .telefone("123456789")
                .email("antigo@email.com")
                .cpfCnpj("57.047.475/0001-50")
                .customerType(CustomerType.PF)
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(existingCustomer);

        ResponseEntity<?> response = customerService.update(editCustomerDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).existsByEmail(editCustomerDTO.email());
        verify(customerRepository, times(1)).saveAndFlush(any(Customer.class));

        Customer updatedCustomer = (Customer) response.getBody();
        assertNotNull(updatedCustomer);

        assertEquals(editCustomerDTO.id(), updatedCustomer.getId());
        assertEquals(editCustomerDTO.endereco(), updatedCustomer.getEndereco());
        assertEquals(editCustomerDTO.telefone(), updatedCustomer.getTelefone());
        assertEquals(editCustomerDTO.email(), updatedCustomer.getEmail());
    }



        @Test
        void shouldReturnBadRequestWhenGetWithEmptyCustomerList() {
            when(customerRepository.findAll()).thenReturn(Collections.emptyList());

            ResponseEntity<?> response = customerService.get();

            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void shouldReturnBadRequestWhenOptionsWithEmptyCustomerList() {
            when(customerRepository.findAll()).thenReturn(Collections.emptyList());

            ResponseEntity<?> response = customerService.options();

            assertEquals(400, response.getStatusCodeValue());
            assertEquals("Não existe clientes cadastrados", response.getBody());
        }

        @Test
        void shouldReturnOptionsListWhenOptionsWithNonEmptyCustomerList() {
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID());
            customer.setNome("Cliente Teste");

            when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

            ResponseEntity<?> response = customerService.options();

            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
        }

        @Test
        void shouldReturnBadRequestWhenCreateWithInvalidCpfCnpj() {
            CreateCustomerDTO invalidDto = new CreateCustomerDTO("Nome", "CNPJ inválido", "Endereço", "Telefone", "email@test.com");

            ResponseEntity<?> response = customerService.create(invalidDto);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals("CPF ou CNPJ inválido", response.getBody());
        }


        @Test
        void shouldReturnBadRequestWhenUpdateWithNotFoundCustomer() {
            EditCustomerDTO nonExistingCustomerDto = new EditCustomerDTO(UUID.randomUUID(), "Endereço", "Telefone", "email@test.com");

            when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            ResponseEntity<?> response = customerService.update(nonExistingCustomerDto);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals("Cliente não encontrada", response.getBody());
        }


        @Test
        void shouldReturnBadRequestWhenDeleteWithNonExistingCustomer() {
            UUID nonExistingCustomerId = UUID.randomUUID();
            when(customerRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

            ResponseEntity<?> response = customerService.delete(nonExistingCustomerId);

            assertEquals(400, response.getStatusCodeValue());
            assertEquals("Não existe um cliente com esse id", response.getBody());
        }


    }
