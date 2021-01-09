package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Transactional
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private PetRepository petRepository;
    private EmployeeRepository employeeRepository;

    public Long save(Schedule schedule){
        System.out.println(schedule.toString());
        return scheduleRepository.save(schedule).getId();
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            return optionalSchedule.get();
        }
        return null;
    }

    public List<Schedule> findByPet(Pet pet) {
        Optional<List<Schedule>> optionalPets = scheduleRepository.findAllByPets(pet);
        if (optionalPets.isPresent()) {
            return optionalPets.get();
        }
        return null;
    }

    public List<Schedule> findByPetId(Long id){
        return findByPet(petRepository.findById(id).get());
    }

    public List<Schedule> findByEmployee(Employee employee) {
        Optional<List<Schedule>> optionalEmployees = scheduleRepository.findAllByEmployees(employee);
        if (optionalEmployees.isPresent()) {
            return optionalEmployees.get();
        }
        return null;
    }

    public List<Schedule> findByEmployeeId(Long id){
        return findByEmployee(employeeRepository.findById(id).get());
    }
}
