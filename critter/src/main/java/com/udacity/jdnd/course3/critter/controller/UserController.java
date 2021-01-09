package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PetService petService;
    private ModelMapper modelMapper;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Long customerId = userService.saveCustomer(convertCustomerDtoToCustomer(customerDTO));
        return convertCustomerToCustomerDto(userService.findCustomerById(customerId));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        userService.findAllCustomers().forEach(customer -> customerDTOS.add(convertCustomerToCustomerDto(customer)));
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.findPetById(petId);
        return convertCustomerToCustomerDto(userService.findOwnerByPet(pet));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Long employeeId = userService.saveEmployee(convertEmployeeDtoToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDto(userService.findEmployeeById(employeeId));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDto(userService.findEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = userService.findEmployeeById(employeeId);
        if(employee != null){
            employee.setDaysAvailable(daysAvailable);
        }
        userService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = userService.findEmployeesByDateAndSkills(employeeDTO.getDate(), employeeDTO.getSkills());
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeDTOS.add(convertEmployeeToEmployeeDto(employee)));
        return employeeDTOS;
    }

    private Customer convertCustomerDtoToCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        if (customerDTO.getPetIds() != null){
            customer.setPets(petService.findAllByIdIn(customerDTO.getPetIds()));
        }
        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDto(Customer customer){
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        List<Long> petIds = new ArrayList<>();
        customer.getPets().forEach(pet -> {
            petIds.add(pet.getId());
            System.out.println(pet.getId());
        });
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private Employee convertEmployeeDtoToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDto(Employee employee){
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return employeeDTO;
    }
}
