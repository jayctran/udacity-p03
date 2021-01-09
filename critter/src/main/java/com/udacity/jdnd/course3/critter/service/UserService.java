package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Data
@Service
@Transactional
public class UserService {
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public Long saveCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public Customer findCustomerById(Long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        return null;
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findOwnerByPet(Pet pet){
        Optional<Customer> optionalCustomer = customerRepository.findByPets(pet);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        return null;
    }

    public Long saveEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        }
        return null;
    }

    public List<Employee> findAllEmployeesById(List<Long> employeeIds) {
        if (employeeIds != null) {
            return employeeRepository.findAllById(employeeIds);
        }
        return null;
    }

    public List<Employee> findEmployeesByDateAndSkills(LocalDate date, Set<EmployeeSkill> skills) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Set<DayOfWeek> daySet = new HashSet<>();
        daySet.add(dayOfWeek);
        Optional<List<Employee>> employees = employeeRepository.findByDaysAvailableInAndSkillsIn(daySet, skills);
        List<Employee> employeeList = new ArrayList<>();
        if (employees.isPresent()) {
            for (Employee employee : employees.get()) {
                if (employee.getSkills().containsAll(skills)) {
                    employeeList.add(employee);
                }
            }
        }
        return employeeList;
    }
}
