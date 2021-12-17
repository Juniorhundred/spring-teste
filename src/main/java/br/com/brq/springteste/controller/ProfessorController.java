package br.com.brq.springteste.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brq.springteste.domain.ProfessorEntity;
import br.com.brq.springteste.dto.ProfessorDTO;
import br.com.brq.springteste.service.ProfessorServices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "professores")
public class ProfessorController {
	
	@Autowired
	private ProfessorServices professorService;
		
	@GetMapping
	public ResponseEntity<List<ProfessorDTO>> getAll(){
		
		List<ProfessorEntity> list = this.professorService.getAll();
	
		
		
		List<ProfessorDTO> listDTO = list.stream().map(ProfessorEntity::toDTO)
				.collect(Collectors.toList()); 
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/busca-nome/{nome}")
	public ResponseEntity<List<ProfessorDTO>>findByNome(@PathVariable("nome") String nome){
		
		List<ProfessorEntity> list = this.professorService.findByNome(nome);
		
		List<ProfessorDTO> listDTO = list.stream().map(ProfessorEntity::toDTO)
				.collect(Collectors.toList()); 
		
		return ResponseEntity.ok().body(listDTO);
		
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<ProfessorDTO> getOne(@PathVariable int id){
		
		ProfessorEntity entity = this.professorService.getOne(id);
		ProfessorDTO dto = entity.toDTO();
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ProfessorDTO> save(@RequestBody ProfessorDTO professor) {
		
		log.info(professor.toString());
		
		ProfessorEntity saved = this.professorService.save(professor.toEntity());
	
		ProfessorDTO dto = saved.toDTO();
		
		return ResponseEntity.ok().body(dto);
	}
	
	// professores/8
		@PutMapping(value = "{id}")
		public ResponseEntity<ProfessorDTO> update(
				@RequestBody ProfessorDTO professor,
				@PathVariable int id) {
			
			ProfessorEntity updated = this.professorService
					.update(id, professor.toEntity() );
			
			return ResponseEntity.ok().body( updated.toDTO() );
		}
	
	
		// professores/1
		@DeleteMapping(value = "{id}")
		public ResponseEntity<Void> delete(@PathVariable int id) {
			this.professorService.delete(id);
			return ResponseEntity.ok().build();
		}
	
	
}