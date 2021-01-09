package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<List<Employee>> findByDaysAvailableInAndSkillsIn(Set<DayOfWeek> daysAvailable, Set<EmployeeSkill> skills);
}
