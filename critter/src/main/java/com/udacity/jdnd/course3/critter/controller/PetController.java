package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@AllArgsConstructor
public class PetController {

    private PetService petService;
    private UserService userService;
    private ModelMapper modelMapper;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Long petId = petService.save(convertPetDtoToPet(petDTO));
        return convertPetToPetDto(petService.findPetById(petId));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDto(petService.findPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOS = new ArrayList<>();
        petService.findAll().forEach(pet -> petDTOS.add(convertPetToPetDto(pet)));
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOS = new ArrayList<>();
        petService.findAllPetsByCustomerId(ownerId).forEach(pet -> petDTOS.add(convertPetToPetDto(pet)));
        return petDTOS;
    }

    private Pet convertPetDtoToPet(PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Customer customer = userService.findCustomerById(petDTO.getOwnerId());
        if(customer != null){
            pet.setCustomer(customer);
        }
        return pet;
    }

    private PetDTO convertPetToPetDto(Pet pet){
        PetDTO petDTO = modelMapper.map(pet, PetDTO.class);
        if(pet.getCustomer() != null){
            petDTO.setOwnerId(userService.findOwnerByPet(pet).getId());
        }
        return petDTO;
    }
}
