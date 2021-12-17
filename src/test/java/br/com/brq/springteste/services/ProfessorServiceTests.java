package br.com.brq.springteste.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brq.springteste.domain.ProfessorEntity;
import br.com.brq.springteste.repository.ProfessorRepositories;
import br.com.brq.springteste.service.ProfessorServices;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProfessorServiceTests {

	@Autowired
	private ProfessorServices professorService;

	@MockBean
	private ProfessorRepositories professorRepository;

	@Test
	@DisplayName("Deve retornar todos os professores")
	void getAllTest() {

		// given (cenário)
		List<ProfessorEntity> listMock = new ArrayList<>();

		ProfessorEntity professor = this.createValidProfessor();
		listMock.add(professor);

		when(professorRepository.findAll()).thenReturn(listMock);

		// execução
		List<ProfessorEntity> list = this.professorService.getAll();

		// verificação
		assertThat(list.size() >= 0).isTrue();
		verify(professorRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Deve retornar 1")
	void retornaInteiroTam1Test() {

		// cenário
		List<ProfessorEntity> listMock = new ArrayList<>();

		ProfessorEntity professor = this.createValidProfessor();
		listMock.add(professor);

		when(professorRepository.findAll()).thenReturn(listMock);

		// execução

		int response = this.professorService.retornaInteiro();

		// validação
		assertThat(response).isEqualTo(1);

	}

	@Test
	@DisplayName("Deve retornar 2")
	void retornaInteiroTam2Test() {

		// cenário
		List<ProfessorEntity> listMock = new ArrayList<>();

		ProfessorEntity professor = this.createValidProfessor();
		listMock.add(professor);
		listMock.add(professor);

		when(professorRepository.findAll()).thenReturn(listMock);

		// execução

		int response = this.professorService.retornaInteiro();

		// validação
		assertThat(response).isEqualTo(2);

	}

	@Test
	void getOneWhenFindObjTest() {
		// cenário - quando há o registro no banco de dados

		int id = 1;
		ProfessorEntity professor = this.createValidProfessor();
		Optional<ProfessorEntity> optional = Optional.of(professor);

		when(professorRepository.findById(id)).thenReturn(optional);

		// execução
		ProfessorEntity search = this.professorService.getOne(id);

		// validação

		assertThat(search.getEmail()).isEqualTo(professor.getEmail());
		assertThat(search.getNome()).isEqualTo(professor.getNome());
	}

//	public void ex(int id) {
//		this.professorService.getOne(id);
//	}
	@Test
	void getOneWhenNotFoundObj() {
		// cenário
		int id = 1;

		Optional<ProfessorEntity> optional = Optional.empty();
		when(professorRepository.findById(id)).thenReturn(optional);

		// execução
		// assertThrows(NoSuchElementException.class, ex(id) );
		assertThrows(NoSuchElementException.class, () -> this.professorService.getOne(id));

	}

	@Test
	void saveTest() {

		// 1-) cenário
		// mockar um professor
		ProfessorEntity professorMock = this.createValidProfessor();
		professorMock.setId(1);

		// when () -> then quando o professorRepository.save(mockado)

		when(professorRepository.save(professorMock)).thenReturn(professorMock);

		// 2-) execução
		// chamar o save do ProfessorService
		ProfessorEntity professor = this.professorService.save(professorMock);

		// 3-) validação

		// comparar nome e email do passo 1 com o objeto do passo 2
		// comparar se id do passo 2 é maior que 0

		assertThat(professor.getEmail()).isEqualTo(professorMock.getEmail());
		assertThat(professor.getNome()).isEqualTo(professorMock.getNome());
		assertThat(professor.getId() >= 0).isTrue();
		verify(professorRepository, times(1)).save(professorMock);
	}

	@Test
	void updateFoundRecordDb() {
		// cenário
		int idProfessor = 1;
		ProfessorEntity professor = this.createValidProfessor();
		professor.setId(idProfessor);

		when(professorRepository.findById(idProfessor)).thenReturn(Optional.of(professor));
		when(professorRepository.save(professor)).thenReturn(professor);
		// execução

		ProfessorEntity updated = this.professorService.update(idProfessor, professor);

		// validação
		assertThat(updated.getId()).isEqualTo(professor.getId());
		assertThat(updated.getNome()).isEqualTo(professor.getNome());
		assertThat(updated.getEmail()).isEqualTo(professor.getEmail());
		verify(professorRepository, times(1)).save(professor);
	}

	@Test
	void updateNotFoundRecordDb() {
		// cenário
		int idProfessor = 1;
		ProfessorEntity professor = this.createValidProfessor();
		professor.setId(idProfessor);

		when(professorRepository.findById(idProfessor)).thenReturn(Optional.empty());

		// execução
		assertThrows(NoSuchElementException.class, () -> this.professorService.update(idProfessor, professor));
	}

	@Test
	void deleteTest() {
		// cenário

		int idProfessor = 1;

		// when (professorRepository.deleteById(idProfessor) );

		assertDoesNotThrow(() -> professorService.delete(idProfessor));
		verify(professorRepository, times(1)).deleteById(idProfessor);
	}

	@Test
	void metodoDoProfessor() {

		// cenário

		// criando a lista a ser mockada pelo método findAll do repository
		List<ProfessorEntity> listAll = new ArrayList<>();
		ProfessorEntity professorListAll = this.createValidProfessor();
		listAll.add(professorListAll);

		// criando a lista (CÓPIA) a ser testada pelo retorno do método SAVE
		List<ProfessorEntity> listAllCopy = new ArrayList<ProfessorEntity>();
		ProfessorEntity professorListAllCopy = this.createValidProfessor();
		listAllCopy.add(professorListAllCopy);

		// Mockando o findAll
		when(professorRepository.findAll()).thenReturn(listAll);

		// criando a lista a ser mockada pelo SAVE do repository
		List<ProfessorEntity> listToSave = new ArrayList<>();
		ProfessorEntity professorToSave = this.createValidProfessor();
		professorToSave.setEmail(professorToSave.getEmail() + "_brq");
		professorToSave.setNome(professorToSave.getNome() + "_brq");
		listToSave.add(professorToSave);

		// mockando o SAVE do repository
		when(professorRepository.saveAll(listToSave)).thenReturn(listToSave);

		// execução - teste do método metodoDoProfessor()
		List<ProfessorEntity> saved = this.professorService.metodoDoProfessor();

		// validação - comparando cada obj salvo com o objeto original(listaCópia)
		// (TEM QUE TER O _brq adicionalmente a partir da listaCopia)
		for (int i = 0; i < saved.size(); i++) {
			assertThat(saved.get(i).getEmail()).isEqualTo(listAllCopy.get(i).getEmail() + "_brq");
			assertThat(saved.get(i).getNome()).isEqualTo(listAllCopy.get(i).getNome() + "_brq");
		}
	}

	@Test
	void findByNomeTest() {

		// criando lista 16/12
		String nome = "nome qualquer";
		List<ProfessorEntity> listfindByNome = new ArrayList<>();
		ProfessorEntity professor = this.createValidProfessor();
		listfindByNome.add(professor);

		// Mockando o metodo FindByNome
		when(professorRepository.findByNomeContains(nome)).thenReturn(listfindByNome);

		// execução
		List<ProfessorEntity> result = professorService.findByNome(nome);

		// validação

		assertThat(result.size() >= 0).isTrue();

	}

	private ProfessorEntity createValidProfessor() {
		return ProfessorEntity.builder().email("email qualquer").nome("nome qualquer").build();
	}

}