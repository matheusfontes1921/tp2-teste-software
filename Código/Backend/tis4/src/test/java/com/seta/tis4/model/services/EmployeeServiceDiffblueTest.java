package com.seta.tis4.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.seta.tis4.model.dtos.employee.CreateEmployeeDTO;
import com.seta.tis4.model.dtos.employee.LoginDto;
import com.seta.tis4.model.dtos.employee.UpdateEmployeeDTO;
import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.repositories.EmployeeRepository;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeService.class})
@ExtendWith(SpringExtension.class)
class EmployeeServiceDiffblueTest {
    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testGetName() {
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualName = employeeService.getName();
        verify(employeeRepository).findAll();
        assertEquals(200, actualName.getStatusCodeValue());
        assertTrue(((Map<Object, Object>) actualName.getBody()).isEmpty());
        assertTrue(actualName.getHeaders().isEmpty());
    }
    @Test
    void testGetName2() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        ResponseEntity<?> actualName = employeeService.getName();
        verify(employeeRepository).findAll();
        assertEquals(1, ((Map<UUID, String>) actualName.getBody()).size());
        assertEquals(200, actualName.getStatusCodeValue());
        assertTrue(actualName.getHeaders().isEmpty());
    }

    @Test
    void testGetName3() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");

        Employee employee2 = new Employee();
        employee2.setEmail("john.smith@example.org");
        employee2.setId(UUID.randomUUID());
        employee2.setNome("42");
        employee2.setSenha("42");

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee2);
        employeeList.add(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        ResponseEntity<?> actualName = employeeService.getName();
        verify(employeeRepository).findAll();
        assertEquals(2, ((Map<UUID, String>) actualName.getBody()).size());
        assertEquals(200, actualName.getStatusCodeValue());
        assertTrue(actualName.getHeaders().isEmpty());
    }

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        ResponseEntity<?> actualCreateEmployeeResult = employeeService
                .createEmployee(new CreateEmployeeDTO(UUID.randomUUID(), "Nome", "jane.doe@example.org", "Senha"));
        verify(employeeRepository).findByEmail(Mockito.<String>any());
        assertEquals("Email já cadastrado", actualCreateEmployeeResult.getBody());
        assertEquals(400, actualCreateEmployeeResult.getStatusCodeValue());
        assertTrue(actualCreateEmployeeResult.getHeaders().isEmpty());
    }


    @Test
    void testCreateEmployee2() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(employee);
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        ResponseEntity<?> actualCreateEmployeeResult = employeeService
                .createEmployee(new CreateEmployeeDTO(UUID.randomUUID(), "Nome", "jane.doe@example.org", "Senha"));
        verify(employeeRepository).findByEmail(Mockito.<String>any());
        verify(employeeRepository).save(Mockito.<Employee>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals(200, actualCreateEmployeeResult.getStatusCodeValue());
        assertTrue(actualCreateEmployeeResult.hasBody());
        assertTrue(actualCreateEmployeeResult.getHeaders().isEmpty());
    }


    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");
        Optional<Employee> ofResult = Optional.of(employee);
        doNothing().when(employeeRepository).deleteById(Mockito.<UUID>any());
        when(employeeRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ResponseEntity<?> actualDeleteEmployeeResult = employeeService.deleteEmployee(UUID.randomUUID());
        verify(employeeRepository).deleteById(Mockito.<UUID>any());
        verify(employeeRepository).findById(Mockito.<UUID>any());
        assertEquals("Funcionário deletado com sucesso", actualDeleteEmployeeResult.getBody());
        assertEquals(200, actualDeleteEmployeeResult.getStatusCodeValue());
        assertTrue(actualDeleteEmployeeResult.getHeaders().isEmpty());
    }

    @Test
    void testDeleteEmployee2() {
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        ResponseEntity<?> actualDeleteEmployeeResult = employeeService.deleteEmployee(UUID.randomUUID());
        verify(employeeRepository).findById(Mockito.<UUID>any());
        assertEquals("Não existe um funcionário com esse id", actualDeleteEmployeeResult.getBody());
        assertEquals(400, actualDeleteEmployeeResult.getStatusCodeValue());
        assertTrue(actualDeleteEmployeeResult.getHeaders().isEmpty());
    }
    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee2 = new Employee();
        employee2.setEmail("jane.doe@example.org");
        employee2.setId(UUID.randomUUID());
        employee2.setNome("Nome");
        employee2.setSenha("Senha");
        when(employeeRepository.saveAndFlush(Mockito.<Employee>any())).thenReturn(employee2);
        when(employeeRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        ResponseEntity<?> actualUpdateEmployeeResult = employeeService
                .updateEmployee(new UpdateEmployeeDTO(UUID.randomUUID(), "Nome", "jane.doe@example.org", "Senha"));
        verify(employeeRepository).saveAndFlush(Mockito.<Employee>any());
        verify(employeeRepository).findById(Mockito.<UUID>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals(200, actualUpdateEmployeeResult.getStatusCodeValue());
        assertTrue(actualUpdateEmployeeResult.hasBody());
        assertTrue(actualUpdateEmployeeResult.getHeaders().isEmpty());
    }

    @Test
    void testUpdateEmployee2() {
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        ResponseEntity<?> actualUpdateEmployeeResult = employeeService
                .updateEmployee(new UpdateEmployeeDTO(UUID.randomUUID(), "Nome", "jane.doe@example.org", "Senha"));
        verify(employeeRepository).findById(Mockito.<UUID>any());
        assertEquals("Não existe um funcionário com esse id", actualUpdateEmployeeResult.getBody());
        assertEquals(400, actualUpdateEmployeeResult.getStatusCodeValue());
        assertTrue(actualUpdateEmployeeResult.getHeaders().isEmpty());
    }
    @Test
    void testLogin() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(true);
        ResponseEntity<?> actualLoginResult = employeeService.login(new LoginDto("jane.doe@example.org", "Senha"));
        verify(employeeRepository, atLeast(1)).findByEmail(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
        assertEquals(200, actualLoginResult.getStatusCodeValue());
        assertTrue(actualLoginResult.hasBody());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
    }

    @Test
    void testLogin2() {
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        ResponseEntity<?> actualLoginResult = employeeService.login(new LoginDto("jane.doe@example.org", "Senha"));
        verify(employeeRepository).findByEmail(Mockito.<String>any());
        assertEquals("Email incorreto", actualLoginResult.getBody());
        assertEquals(400, actualLoginResult.getStatusCodeValue());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
    }

    @Test
    void testLogin3() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setId(UUID.randomUUID());
        employee.setNome("Nome");
        employee.setSenha("Senha");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(false);
        ResponseEntity<?> actualLoginResult = employeeService.login(new LoginDto("jane.doe@example.org", "Senha"));
        verify(employeeRepository, atLeast(1)).findByEmail(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
        assertEquals("Senha incorreta", actualLoginResult.getBody());
        assertEquals(401, actualLoginResult.getStatusCodeValue());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
    }

    @Test
    void testGetEmployee() {
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualEmployee = employeeService.getEmployee();
        verify(employeeRepository).findAll();
        assertEquals(200, actualEmployee.getStatusCodeValue());
        assertTrue(actualEmployee.hasBody());
        assertTrue(actualEmployee.getHeaders().isEmpty());
    }
}

