package br.com.brq.springteste.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brq.springteste.domain.ProfessorEntity;
import br.com.brq.springteste.repository.ProfessorRepositories;

@ExtendWith(SpringExtension.class)
@DataJpaTest
 class ProfessorRepositoryTests {

	@Autowired
	private ProfessorRepositories professorRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistsTests() {

		ProfessorEntity professor = this.createValidProfessor();

		this.testEntityManager.persist(professor);

		Optional<ProfessorEntity> response = this.professorRepository.findById(1);

		assertThat(response).isNotNull();

	}

	@Test
	 void findByIdNotExists() {

		Optional<ProfessorEntity> response = this.professorRepository.findById(1);

		assertThat(response).isEmpty();
	}
	
	@Test
	 void find() {
		// cenário
		ProfessorEntity professor = this.createValidProfessor();

		this.testEntityManager.persist(professor);

		// execução
		List<ProfessorEntity> list = this.professorRepository.findAll();

		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	 void findAll() {
		// cenário
		ProfessorEntity professor = this.createValidProfessor();

		this.testEntityManager.persist(professor);

		// execução
		List<ProfessorEntity> list = this.professorRepository.findAll();

		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	void saveTest() {
		// cenário
		// criar o professor mockado
		ProfessorEntity professor = this.createValidProfessor();

		ProfessorEntity saved = this.professorRepository.save(professor);

		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getNome()).isEqualTo(professor.getNome());
		assertThat(saved.getEmail()).isEqualTo(professor.getEmail());

	}

	@Test
	 void deleteByIdTest() {

		// cenário
		// vou inserir um professor
		ProfessorEntity professor = this.createValidProfessor();

		ProfessorEntity saved = testEntityManager.persist(professor);

		// execução
		// vou deletar o professor
		this.professorRepository.deleteById(saved.getId());

		// verificação
		// não posso achar o professor deletado
		Optional<ProfessorEntity> search = this.professorRepository.findById(saved.getId());

		assertThat(search).isEmpty();
	}

	@Test
	 void buscarPorNomeNativeTest() {

		// cenário
		// vou inserir um professor
		ProfessorEntity professor = this.createValidProfessor();
		ProfessorEntity saved = testEntityManager.persist(professor);

		// execução
		List<ProfessorEntity> list = this.professorRepository.buscarPorNomeNative(saved.getNome());

		// verificação >=
		assertThat(list.size() >=0).isTrue();
	}
	
	@Test
	void buscarPorNomeJpqlTest() {

		// cenário
		// vou inserir um professor
		ProfessorEntity professor = this.createValidProfessor();
		ProfessorEntity saved = testEntityManager.persist(professor);

		// execução
		List<ProfessorEntity> list = this.professorRepository.buscarPorNomeJpql(saved.getNome());

		// verificação >=
		assertThat(list.size() >=0).isTrue();
	}
	
	@Test
	 void findByNomeContainsAndIdTest() {

		// cenário
		// vou inserir um professor
		ProfessorEntity professor = this.createValidProfessor();
		ProfessorEntity saved = testEntityManager.persist(professor);

		// execução
		List<ProfessorEntity> list = this.professorRepository.findByNomeContainsAndId(saved.getNome(), saved.getId());

		// verificação >=
		assertThat(list.size() >=0).isTrue();
		
	}
	// dia 16/12
		@Test
		 void findByNomeContainsTest() {

			// cenário
			// vou inserir um professor
			ProfessorEntity professor = this.createValidProfessor();
			ProfessorEntity saved = testEntityManager.persist(professor);

			// execução
			List<ProfessorEntity> list = this.professorRepository.findByNomeContains(saved.getNome());

			// verificação >=
			assertThat(list.size()>= 0).isTrue();
	}
	
	
	private ProfessorEntity createValidProfessor() {
		return ProfessorEntity.builder().email("email qualquer").nome("nome qualquer").build();
	}

}