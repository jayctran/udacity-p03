package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public Set<Pet> findAllByIdIn(List<Long> petIds){
        return petRepository.findAll().stream().filter(
                pet -> petIds.contains(pet.getId())).collect(Collectors.toSet()
        );
    }

    public Long save(Pet pet){
        Long petId = petRepository.save(pet).getId();
        return petId;
    }

    public Pet findPetById(Long id){
        return petRepository.findById(id).get();
    }

    public List<Pet> findAll(){
        return petRepository.findAll();
    }

    public List<Pet> findAllPetsByCustomerId(Long id){
        return petRepository.findAllByCustomer_Id(id);
    }

    public List<Pet> findAllPetsById(List<Long> petIds){
        return petRepository.findAllById(petIds);
    }
}
