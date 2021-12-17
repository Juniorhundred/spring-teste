package br.com.brq.springteste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.brq.springteste.domain.ProfessorEntity;

@Repository
public interface ProfessorRepositories extends JpaRepository<ProfessorEntity, Integer>{

	
	@Query(value = "SELECT * FROM professores WHERE nome = :nome", nativeQuery = true)
	List<ProfessorEntity> buscarPorNomeNative(@Param(value = "nome") String nome );
	
	@Query(value = "SELECT pe FROM ProfessorEntity pe WHERE pe.nome = :nome")
	List<ProfessorEntity> buscarPorNomeJpql(@Param(value = "nome") String nome );
	
	// SELECT * FROM professores where nome like '%a%'
	List<ProfessorEntity> findByNomeContainsAndId(String nome, int id);
	
	// dia 16/12 
	List<ProfessorEntity>findByNomeContains(String nome);
	
 
}