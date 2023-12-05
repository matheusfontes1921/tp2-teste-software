package com.seta.tis4.services;

import com.seta.tis4.model.dtos.employee.CreateEmployeeDTO;
import com.seta.tis4.model.dtos.employee.LoginDto;
import com.seta.tis4.model.dtos.employee.UpdateEmployeeDTO;
import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        employeeService = new EmployeeService(employeeRepository, passwordEncoder);
    }

    @Test
    void testGetName() {
        List<Employee> employees = Arrays.asList(
                new Employee ("John","employee@email.com","123"),
                new Employee("Bolo","employee1@email.com","123")
        );
        when(employeeRepository.findAll()).thenReturn(employees);

        ResponseEntity<?> response = employeeService.getName();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateEmployeeWithUniqueEmail() {
        CreateEmployeeDTO dto = new CreateEmployeeDTO(null,"John", "john@example.com", "password");
        when(employeeRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.senha())).thenReturn("hashedPassword");

        ResponseEntity<?> response = employeeService.createEmployee(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testCreateEmployeeWithExistingEmail() {
        CreateEmployeeDTO dto = new CreateEmployeeDTO(null,"John", "john@example.com", "password");
        when(employeeRepository.findByEmail(dto.email())).thenReturn(Optional.of(new Employee()));

        ResponseEntity<?> response = employeeService.createEmployee(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email já cadastrado", response.getBody());
    }

    @Test
    void testDeleteEmployeeWithExistingId() {
        UUID existingId = UUID.randomUUID();
        when(employeeRepository.findById(existingId)).thenReturn(Optional.of(new Employee()));

        ResponseEntity<?> response = employeeService.deleteEmployee(existingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário deletado com sucesso", response.getBody());
    }

    @Test
    void testDeleteEmployeeWithNonExistingId() {
        UUID nonExistingId = UUID.randomUUID();
        when(employeeRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = employeeService.deleteEmployee(nonExistingId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Não existe um funcionário com esse id", response.getBody());
    }

    @Test
    void testUpdateEmployeeWithExistingId() {
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO(UUID.randomUUID(),"jhon","email@email.com", "newPassword");
        Employee existingEmployee = new Employee();
        when(employeeRepository.findById(dto.id())).thenReturn(Optional.of(existingEmployee));
        when(passwordEncoder.encode(dto.senha())).thenReturn("hashedPassword");

        ResponseEntity<?> response = employeeService.updateEmployee(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateEmployeeWithNonExistingId() {
        UUID nonExistingId = UUID.randomUUID();
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO(null,"jhon","email@email.com", "newPassword");
        when(employeeRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = employeeService.updateEmployee(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Não existe um funcionário com esse id", response.getBody());
    }

    @Test
    void testLoginWithCorrectCredentials() {
        LoginDto loginDto = new LoginDto("john@example.com", "password");
        Employee employee = new Employee();
        employee.setSenha(passwordEncoder.encode("password"));
        when(employeeRepository.findByEmail(loginDto.email())).thenReturn(Optional.of(employee));
        when(passwordEncoder.matches(loginDto.senha(), employee.getSenha())).thenReturn(true);

        ResponseEntity<?> response = employeeService.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testLoginWithIncorrectEmail() {
        LoginDto loginDto = new LoginDto("invalid@example.com", "password");
        when(employeeRepository.findByEmail(loginDto.email())).thenReturn(Optional.empty());

        ResponseEntity<?> response = employeeService.login(loginDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email incorreto", response.getBody());
    }

    @Test
    void testLoginWithIncorrectPassword() {
        LoginDto loginDto = new LoginDto("john@example.com", "wrongpassword");
        Employee employee = new Employee();
        employee.setSenha(passwordEncoder.encode("password"));
        when(employeeRepository.findByEmail(loginDto.email())).thenReturn(Optional.of(employee));
        when(passwordEncoder.matches(loginDto.senha(), employee.getSenha())).thenReturn(false);

        ResponseEntity<?> response = employeeService.login(loginDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Senha incorreta", response.getBody());
    }

    @Test
    void testGetEmployee() {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);

        ResponseEntity<?> response = employeeService.getEmployee();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


}
