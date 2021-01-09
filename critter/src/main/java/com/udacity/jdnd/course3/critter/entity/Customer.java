package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
public class Customer extends BaseEntity {
    private String name;
    private String phoneNumber;
    private String notes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Pet> pets = new HashSet<>();
}
