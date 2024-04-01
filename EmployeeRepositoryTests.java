package com.springboot.repository;

import com.springboot.model.Employee;
import lombok.AllArgsConstructor;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setFirstName("john");
        employee.setLastName("doe");
        employee.setEmail("john@gmail.com");
        employee.setLastName("doe");
    }


    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        //checking -> assertion
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {
        employeeRepository.save(employee);
        Employee employee2 = new Employee();
        employee2.setFirstName("john");
        employee2.setLastName("doe");
        employee2.setEmail("john@gmail.com");
        employee2.setLastName("doe");
        employeeRepository.save(employee2);

        List<Employee> employeeList = employeeRepository.findAll();

        //checking -> assertion
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }


    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        Employee savedEmployee = employeeRepository.save(employee);
        Employee employeeDb = employeeRepository.findById(savedEmployee.getId()).get();
        //checking -> assertion
        assertThat(employeeDb).isNotNull();
    }

    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        Employee savedEmployee = employeeRepository.save(employee);
        Employee employeeDb = employeeRepository.findById(savedEmployee.getId()).get();
        employeeDb.setEmail("john_doe@gmail.com");
        Employee updatedEmployee = employeeRepository.save(employeeDb);
        //checking -> assertion
        assertThat(updatedEmployee.getEmail()).isEqualTo("john_doe@gmail.com");
    }

    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        employeeRepository.deleteById(savedEmployee.getId());

        Optional<Employee> employeeOptional = employeeRepository.findById(savedEmployee.getId());
        assertThat(employeeOptional).isEmpty();
    }


}
