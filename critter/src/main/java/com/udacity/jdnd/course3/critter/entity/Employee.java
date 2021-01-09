package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.entity.enums.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Data
public class Employee extends BaseEntity {
    private String name;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name="employee_skill", joinColumns = @JoinColumn(name="employee_id"))
    @Column(name = "skill")
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name="employee_dayofweek", joinColumns = @JoinColumn(name="employee_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.ORDINAL)
    private Set<DayOfWeek> daysAvailable;
}
