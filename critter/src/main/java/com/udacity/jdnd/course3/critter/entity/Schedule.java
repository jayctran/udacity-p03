package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Schedule extends BaseEntity {
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="schedule_employee",
            joinColumns = @JoinColumn(name="schdeule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(name="schdeule_pet",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;
}
