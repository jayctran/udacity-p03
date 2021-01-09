package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final PetService petService;
    private ModelMapper modelMapper;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDtoToSchedule(scheduleDTO);
        List<Employee> employees = userService.findAllEmployeesById(scheduleDTO.getEmployeeIds());
        List<Pet> pets = petService.findAllPetsById(scheduleDTO.getPetIds());
        if (employees != null) {
            schedule.setEmployees(employees);
        }
        if (pets != null) {
            schedule.setPets(pets);
        }
        Long scheduleId = scheduleService.save(schedule);
        return convertScheduleToScheduleDto(scheduleService.findById(scheduleId));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.findAll().forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDto(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.findByPetId(petId).forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDto(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.findByEmployeeId(employeeId).forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDto(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = userService.findCustomerById(customerId);
        if (customer == null) {
            throw new UnsupportedOperationException("Customer for not found");
        }
        List<Pet> pets = petService.findAllPetsByCustomerId(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>();
        if (pets == null) {
            throw new UnsupportedOperationException("No pets found");
        }
        pets.forEach(pet -> schedules.addAll(scheduleService.findByPet(pet)));
        schedules.forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDto(schedule)));
        return scheduleDTOS;
    }

    private Schedule convertScheduleDtoToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDto(Schedule schedule){
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
        if (schedule.getPets() != null) {
            List<Long> petIds = new ArrayList<>();
            for (Pet pet : schedule.getPets()) {
                if (pet != null) {
                    petIds.add(pet.getId());
                }
            }
            scheduleDTO.setPetIds(petIds);
        }
        if (schedule.getEmployees() != null) {
            List<Long> employeeIds = new ArrayList<>();
            for (Employee emp : schedule.getEmployees()) {
                if (emp != null) {
                    employeeIds.add(emp.getId());
                }
            }
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        return scheduleDTO;
    }
}
