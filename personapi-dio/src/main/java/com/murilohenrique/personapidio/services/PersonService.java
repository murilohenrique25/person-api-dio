package com.murilohenrique.personapidio.services;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilohenrique.personapidio.dto.MessageResponseDTO;
import com.murilohenrique.personapidio.dto.PersonDTO;
import com.murilohenrique.personapidio.entity.Person;
import com.murilohenrique.personapidio.mapper.PersonMapper;
import com.murilohenrique.personapidio.repository.PersonRepository;


@Service
public class PersonService {
	
	@Autowired
	PersonRepository repo;
	
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	
	public MessageResponseDTO createPerson(PersonDTO personDTO) {
		
		Person personToSave = personMapper.toModel(personDTO);
		Person savedPerson = repo.save(personToSave);
		return createMessageResponse(savedPerson.getId(), "Created Person with ID: ");
	}
	
	public List<PersonDTO> listAll() {
		List<Person> allPeople = repo.findAll();
		
		return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
	}

	public PersonDTO findById(Long id) throws PersonNotFoundException {
			Person person = verifyIfExists(id); 
			//if(!person.isPresent()) {
			//	throw new PersonNotFoundException(id);
			//}
		return personMapper.toDTO(person);
	}

	public void delete(Long id) throws PersonNotFoundException {
		verifyIfExists(id);
		
		repo.deleteById(id);
		
	}
		

	public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
		
		verifyIfExists(id);
		
		Person personToUpdated = personMapper.toModel(personDTO);
		Person updatedPerson = repo.save(personToUpdated);
		return createMessageResponse(updatedPerson.getId(), "Updated Person with ID: ");
	}
	
	private Person verifyIfExists(Long id) throws PersonNotFoundException{
		return repo.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}
	
	private MessageResponseDTO createMessageResponse(Long id, String message) {
		return MessageResponseDTO.builder()
				.message(message + id )
				.build();
	}

}
