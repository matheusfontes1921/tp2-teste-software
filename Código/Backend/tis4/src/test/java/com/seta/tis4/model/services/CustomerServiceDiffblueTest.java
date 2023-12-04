package com.seta.tis4.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.InvalidStateException;
import com.seta.tis4.model.dtos.customer.CreateCustomerDTO;
import com.seta.tis4.model.dtos.customer.EditCustomerDTO;
import com.seta.tis4.model.entities.purchase.Purchase;
import com.seta.tis4.model.entities.purchase.StatusType;
import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.entities.user.customer.Customer;
import com.seta.tis4.model.entities.user.customer.CustomerType;
import com.seta.tis4.model.repositories.CustomerRepository;
import com.seta.tis4.model.repositories.PurchaseRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceDiffblueTest {
//    @MockBean
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private CustomerService customerService;
//
//    @MockBean
//    private PurchaseRepository purchaseRepository;
//
//    @Test
//    void testGet() {
//        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualGetResult = customerService.get();
//        verify(customerRepository).findAll();
//        assertEquals(200, actualGetResult.getStatusCodeValue());
//        assertTrue(actualGetResult.hasBody());
//        assertTrue(actualGetResult.getHeaders().isEmpty());
//    }
//
//    @Test
//    void testGet2() {
//        when(customerRepository.findAll()).thenThrow(new InvalidStateException(mock(ValidationMessage.class)));
//        assertThrows(InvalidStateException.class, () -> customerService.get());
//        verify(customerRepository).findAll();
//    }
//
//
//    @Test
//    void testGetName() {
//        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualName = customerService.getName();
//        verify(customerRepository).findAll();
//        assertEquals("Não existe clientes cadastrados", actualName.getBody());
//        assertEquals(400, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link CustomerService#getName()}
//     */
//    @Test
//    void testGetName2() {
//        Customer customer = new Customer();
//        customer.setCpfCnpj("Não existe clientes cadastrados");
//        customer.setCustomerType(CustomerType.PF);
//        customer.setEmail("jane.doe@example.org");
//        customer.setEndereco("Não existe clientes cadastrados");
//        customer.setId(UUID.randomUUID());
//        customer.setNome("Não existe clientes cadastrados");
//        customer.setTelefone("Não existe clientes cadastrados");
//
//        ArrayList<Customer> customerList = new ArrayList<>();
//        customerList.add(customer);
//        when(customerRepository.findAll()).thenReturn(customerList);
//        ResponseEntity<?> actualName = customerService.getName();
//        verify(customerRepository).findAll();
//        assertEquals(1, ((Map<UUID, String>) actualName.getBody()).size());
//        assertEquals(200, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    @Test
//    void testCreate() {
//        ResponseEntity<?> actualCreateResult = customerService
//                .create(new CreateCustomerDTO("Nome", "Cpf Cnpj", "Endereco", "Telefone", "jane.doe@example.org"));
//        assertEquals("CPF ou CNPJ inválido", actualCreateResult.getBody());
//        assertEquals(400, actualCreateResult.getStatusCodeValue());
//        assertTrue(actualCreateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link CustomerService#create(CreateCustomerDTO)}
//     */
//    @Test
//    void testCreate2() {
//        ResponseEntity<?> actualCreateResult = customerService
//                .create(new CreateCustomerDTO("Nome", "U", "Endereco", "Telefone", "jane.doe@example.org"));
//        assertEquals("CPF ou CNPJ inválido", actualCreateResult.getBody());
//        assertEquals(400, actualCreateResult.getStatusCodeValue());
//        assertTrue(actualCreateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link CustomerService#create(CreateCustomerDTO)}
//     */
//    @Test
//    void testCreate3() {
//        ResponseEntity<?> actualCreateResult = customerService
//                .create(new CreateCustomerDTO("Nome", "99999999999", "Endereco", "Telefone", "jane.doe@example.org"));
//        assertEquals("CPF ou CNPJ inválido", actualCreateResult.getBody());
//        assertEquals(400, actualCreateResult.getStatusCodeValue());
//        assertTrue(actualCreateResult.getHeaders().isEmpty());
//    }
//
//    @Test
//    void testUpdate() {
//        Customer customer = new Customer();
//        customer.setCpfCnpj("Cpf Cnpj");
//        customer.setCustomerType(CustomerType.PF);
//        customer.setEmail("jane.doe@example.org");
//        customer.setEndereco("Endereco");
//        customer.setId(UUID.randomUUID());
//        customer.setNome("Nome");
//        customer.setTelefone("Telefone");
//        Optional<Customer> ofResult = Optional.of(customer);
//
//        Customer customer2 = new Customer();
//        customer2.setCpfCnpj("Cpf Cnpj");
//        customer2.setCustomerType(CustomerType.PF);
//        customer2.setEmail("jane.doe@example.org");
//        customer2.setEndereco("Endereco");
//        customer2.setId(UUID.randomUUID());
//        customer2.setNome("Nome");
//        customer2.setTelefone("Telefone");
//        when(customerRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);
//        when(customerRepository.saveAndFlush(Mockito.<Customer>any())).thenReturn(customer2);
//        when(customerRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        ResponseEntity<?> actualUpdateResult = customerService
//                .update(new EditCustomerDTO(UUID.randomUUID(), "Endereco", "Telefone", "jane.doe@example.org"));
//        verify(customerRepository).existsByEmail(Mockito.<String>any());
//        verify(customerRepository).saveAndFlush(Mockito.<Customer>any());
//        verify(customerRepository).findById(Mockito.<UUID>any());
//        assertEquals(200, actualUpdateResult.getStatusCodeValue());
//        assertTrue(actualUpdateResult.hasBody());
//        assertTrue(actualUpdateResult.getHeaders().isEmpty());
//    }
//
//    @Test
//    void testUpdate2() {
//        Customer customer = new Customer();
//        customer.setCpfCnpj("Cpf Cnpj");
//        customer.setCustomerType(CustomerType.PF);
//        customer.setEmail("jane.doe@example.org");
//        customer.setEndereco("Endereco");
//        customer.setId(UUID.randomUUID());
//        customer.setNome("Nome");
//        customer.setTelefone("Telefone");
//        Optional<Customer> ofResult = Optional.of(customer);
//        when(customerRepository.existsByEmail(Mockito.<String>any()))
//                .thenThrow(new InvalidStateException(mock(ValidationMessage.class)));
//        when(customerRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        assertThrows(InvalidStateException.class, () -> customerService
//                .update(new EditCustomerDTO(UUID.randomUUID(), "Endereco", "Telefone", "jane.doe@example.org")));
//        verify(customerRepository).existsByEmail(Mockito.<String>any());
//        verify(customerRepository).findById(Mockito.<UUID>any());
//    }
//
//
//
//    @Test
//    void testDelete2() {
//        Customer customer = new Customer();
//        customer.setCpfCnpj("Cpf Cnpj");
//        customer.setCustomerType(CustomerType.PF);
//        customer.setEmail("jane.doe@example.org");
//        customer.setEndereco("Endereco");
//        customer.setId(UUID.randomUUID());
//        customer.setNome("Nome");
//        customer.setTelefone("Telefone");
//        Optional<Customer> ofResult = Optional.of(customer);
//        when(customerRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        when(purchaseRepository.findPurchaseByCustomer_Id(Mockito.<UUID>any()))
//                .thenThrow(new InvalidStateException(mock(ValidationMessage.class)));
//        assertThrows(InvalidStateException.class, () -> customerService.delete(UUID.randomUUID()));
//        verify(purchaseRepository).findPurchaseByCustomer_Id(Mockito.<UUID>any());
//        verify(customerRepository).findById(Mockito.<UUID>any());
//    }
//
//    @Test
//    void testDelete3() {
//        Optional<Customer> emptyResult = Optional.empty();
//        when(customerRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
//        ResponseEntity<?> actualDeleteResult = customerService.delete(UUID.randomUUID());
//        verify(customerRepository).findById(Mockito.<UUID>any());
//        assertEquals("Não existe um cliente com esse id", actualDeleteResult.getBody());
//        assertEquals(400, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
//
//    @Test
//    void testDelete4() {
//        Customer customer = new Customer();
//        customer.setCpfCnpj("Cpf Cnpj");
//        customer.setCustomerType(CustomerType.PF);
//        customer.setEmail("jane.doe@example.org");
//        customer.setEndereco("Endereco");
//        customer.setId(UUID.randomUUID());
//        customer.setNome("Nome");
//        customer.setTelefone("Telefone");
//        Optional<Customer> ofResult = Optional.of(customer);
//        doNothing().when(customerRepository).deleteById(Mockito.<UUID>any());
//        when(customerRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//
//        Customer customer2 = new Customer();
//        customer2.setCpfCnpj("Cliente deletada com sucesso");
//        customer2.setCustomerType(CustomerType.PF);
//        customer2.setEmail("jane.doe@example.org");
//        customer2.setEndereco("Cliente deletada com sucesso");
//        customer2.setId(UUID.randomUUID());
//        customer2.setNome("Cliente deletada com sucesso");
//        customer2.setTelefone("Cliente deletada com sucesso");
//
//        Employee employee = new Employee();
//        employee.setEmail("jane.doe@example.org");
//        employee.setId(UUID.randomUUID());
//        employee.setNome("Cliente deletada com sucesso");
//        employee.setSenha("Cliente deletada com sucesso");
//
//        Purchase purchase = new Purchase();
//        purchase.setCustomer(customer2);
//        purchase.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
//        purchase.setEmployee(employee);
//        purchase.setId(UUID.randomUUID());
//        purchase.setStatus(StatusType.ATIVO);
//
//        ArrayList<Purchase> purchaseList = new ArrayList<>();
//        purchaseList.add(purchase);
//        doNothing().when(purchaseRepository).deleteById(Mockito.<UUID>any());
//        when(purchaseRepository.findPurchaseByCustomer_Id(Mockito.<UUID>any())).thenReturn(purchaseList);
//        ResponseEntity<?> actualDeleteResult = customerService.delete(UUID.randomUUID());
//        verify(purchaseRepository).findPurchaseByCustomer_Id(Mockito.<UUID>any());
//        verify(customerRepository).deleteById(Mockito.<UUID>any());
//        verify(purchaseRepository).deleteById(Mockito.<UUID>any());
//        verify(customerRepository).findById(Mockito.<UUID>any());
//        assertEquals("Cliente deletada com sucesso", actualDeleteResult.getBody());
//        assertEquals(200, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
//
//    @Test
//    void testDelete5() {
//        Customer customer = new Customer();
//        customer.setCpfCnpj("Cpf Cnpj");
//        customer.setCustomerType(CustomerType.PF);
//        customer.setEmail("jane.doe@example.org");
//        customer.setEndereco("Endereco");
//        customer.setId(UUID.randomUUID());
//        customer.setNome("Nome");
//        customer.setTelefone("Telefone");
//        Optional<Customer> ofResult = Optional.of(customer);
//        doNothing().when(customerRepository).deleteById(Mockito.<UUID>any());
//        when(customerRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//
//        Customer customer2 = new Customer();
//        customer2.setCpfCnpj("Cliente deletada com sucesso");
//        customer2.setCustomerType(CustomerType.PF);
//        customer2.setEmail("jane.doe@example.org");
//        customer2.setEndereco("Cliente deletada com sucesso");
//        customer2.setId(UUID.randomUUID());
//        customer2.setNome("Cliente deletada com sucesso");
//        customer2.setTelefone("Cliente deletada com sucesso");
//
//        Employee employee = new Employee();
//        employee.setEmail("jane.doe@example.org");
//        employee.setId(UUID.randomUUID());
//        employee.setNome("Cliente deletada com sucesso");
//        employee.setSenha("Cliente deletada com sucesso");
//
//        Purchase purchase = new Purchase();
//        purchase.setCustomer(customer2);
//        purchase.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
//        purchase.setEmployee(employee);
//        purchase.setId(UUID.randomUUID());
//        purchase.setStatus(StatusType.ATIVO);
//
//        Customer customer3 = new Customer();
//        customer3.setCpfCnpj("Cpf Cnpj");
//        customer3.setCustomerType(CustomerType.PJ);
//        customer3.setEmail("john.smith@example.org");
//        customer3.setEndereco("Endereco");
//        customer3.setId(UUID.randomUUID());
//        customer3.setNome("Nome");
//        customer3.setTelefone("Telefone");
//
//        Employee employee2 = new Employee();
//        employee2.setEmail("john.smith@example.org");
//        employee2.setId(UUID.randomUUID());
//        employee2.setNome("Nome");
//        employee2.setSenha("Senha");
//
//        Purchase purchase2 = new Purchase();
//        purchase2.setCustomer(customer3);
//        purchase2.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
//        purchase2.setEmployee(employee2);
//        purchase2.setId(UUID.randomUUID());
//        purchase2.setStatus(StatusType.INATIVO);
//
//        ArrayList<Purchase> purchaseList = new ArrayList<>();
//        purchaseList.add(purchase2);
//        purchaseList.add(purchase);
//        doNothing().when(purchaseRepository).deleteById(Mockito.<UUID>any());
//        when(purchaseRepository.findPurchaseByCustomer_Id(Mockito.<UUID>any())).thenReturn(purchaseList);
//        ResponseEntity<?> actualDeleteResult = customerService.delete(UUID.randomUUID());
//        verify(purchaseRepository).findPurchaseByCustomer_Id(Mockito.<UUID>any());
//        verify(customerRepository).deleteById(Mockito.<UUID>any());
//        verify(purchaseRepository, atLeast(1)).deleteById(Mockito.<UUID>any());
//        verify(customerRepository).findById(Mockito.<UUID>any());
//        assertEquals("Cliente deletada com sucesso", actualDeleteResult.getBody());
//        assertEquals(200, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
//
//    @Test
//    void testDelete6() {
//        Customer customer = new Customer();
//        customer.setCpfCnpj("Cpf Cnpj");
//        customer.setCustomerType(CustomerType.PF);
//        customer.setEmail("jane.doe@example.org");
//        customer.setEndereco("Endereco");
//        customer.setId(UUID.randomUUID());
//        customer.setNome("Nome");
//        customer.setTelefone("Telefone");
//        Optional<Customer> ofResult = Optional.of(customer);
//        doNothing().when(customerRepository).deleteById(Mockito.<UUID>any());
//        when(customerRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//
//        Customer customer2 = new Customer();
//        customer2.setCpfCnpj("Cliente deletada com sucesso");
//        customer2.setCustomerType(CustomerType.PF);
//        customer2.setEmail("jane.doe@example.org");
//        customer2.setEndereco("Cliente deletada com sucesso");
//        customer2.setId(UUID.randomUUID());
//        customer2.setNome("Cliente deletada com sucesso");
//        customer2.setTelefone("Cliente deletada com sucesso");
//
//        Employee employee = new Employee();
//        employee.setEmail("jane.doe@example.org");
//        employee.setId(UUID.randomUUID());
//        employee.setNome("Cliente deletada com sucesso");
//        employee.setSenha("Cliente deletada com sucesso");
//
//        Purchase purchase = new Purchase();
//        purchase.setCustomer(customer2);
//        purchase.setDate(LocalDate.of(1970, 1, 1).atStartOfDay());
//        purchase.setEmployee(employee);
//        purchase.setId(UUID.randomUUID());
//        purchase.setStatus(StatusType.ATIVO);
//
//        ArrayList<Purchase> purchaseList = new ArrayList<>();
//        purchaseList.add(purchase);
//        doThrow(new InvalidStateException(mock(ValidationMessage.class))).when(purchaseRepository)
//                .deleteById(Mockito.<UUID>any());
//        when(purchaseRepository.findPurchaseByCustomer_Id(Mockito.<UUID>any())).thenReturn(purchaseList);
//        assertThrows(InvalidStateException.class, () -> customerService.delete(UUID.randomUUID()));
//        verify(purchaseRepository).findPurchaseByCustomer_Id(Mockito.<UUID>any());
//        verify(purchaseRepository).deleteById(Mockito.<UUID>any());
//        verify(customerRepository).findById(Mockito.<UUID>any());
//    }
}

