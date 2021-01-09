package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.entity.enums.PetType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Pet extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private PetType type;
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Customer customer;

    private LocalDate birthDate;
    private String notes;

    public void setCustomer(Customer customer){
        this.customer = customer;
        if(!customer.getPets().contains(this)){
            customer.getPets().add(this);
        }
    }
}
