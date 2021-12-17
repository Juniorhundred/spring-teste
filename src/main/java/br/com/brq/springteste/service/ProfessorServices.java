package br.com.brq.springteste.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brq.springteste.domain.ProfessorEntity;
import br.com.brq.springteste.repository.ProfessorRepositories;

@Service
public class ProfessorServices {

	@Autowired
	private ProfessorRepositories professorRepository;

	public List<ProfessorEntity> getAll() {
		return this.professorRepository.findAll();
	}

	public ProfessorEntity getOne(int id) {
		
		return this.professorRepository.findById(id).orElseThrow();
	}

	public ProfessorEntity save(ProfessorEntity professor) {
		return this.professorRepository.save(professor);
	}

	public ProfessorEntity update(int id, ProfessorEntity professor) {

		ProfessorEntity entity = this.getOne(id);

		entity.setNome(professor.getNome());
		entity.setEmail(professor.getEmail());

		return this.save(entity);
	}

	public void delete(int id) {
		this.professorRepository.deleteById(id);
	}

	public int retornaInteiro() {
		List<ProfessorEntity> list = this.professorRepository.findAll();

		return list.size() <= 1 ? 1 : 2;
	}

	public List<ProfessorEntity> metodoDoProfessor() {

		List<ProfessorEntity> list = professorRepository.findAll();

		for (ProfessorEntity professor : list) {
			professor.setEmail(professor.getEmail() + "_brq");
			professor.setNome(professor.getNome() + "_brq");
		}

		return professorRepository.saveAll(list);

	}
	
	public List<ProfessorEntity> findByNome(String nome) {
		return this.professorRepository.findByNomeContains(nome);
	}
	
}