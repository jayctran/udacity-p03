package com.udacity.jdnd.course3.critter.entity;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class BaseEntity {
    @Id
    @GeneratedValue
    private long id;
}
