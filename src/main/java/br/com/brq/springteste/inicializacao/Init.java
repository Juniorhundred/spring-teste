package br.com.brq.springteste.inicializacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.brq.springteste.dto.ProfessorDTO;
import br.com.brq.springteste.service.ProfessorServices;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private ProfessorServices professorService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		for (int i = 0; i < 3; i++) {
			ProfessorDTO p = this.createValidProfessor(i);
			this.professorService.save(p.toEntity());
		}		
		
		for (int i = 0; i < 3; i++) {
			ProfessorDTO p = this.createValidProfessor2(i);
			this.professorService.save(p.toEntity());
		}		
		for (int i = 0; i < 3; i++) {
			ProfessorDTO p = this.createValidProfessor3(i);
			this.professorService.save(p.toEntity());
		}		
	}
	
	private ProfessorDTO createValidProfessor(int i) {
		
		return ProfessorDTO
				.builder()
				.nome("Fabrizio " + i)
				.email("fabrizio"+ i+ "@grandeporte.com.br")
				.build();
		
		
	}
	
private ProfessorDTO createValidProfessor2(int i) {
		
		return ProfessorDTO
				.builder()
				.nome("Sandro " + i)
				.email("Andre"+ i+ "@grandeporte2.com.br")
				.build();
		
		
		
	}

private ProfessorDTO createValidProfessor3(int i) {
	
	return ProfessorDTO
			.builder()
			.nome("Sandro Fabrizio" + i)
			.email("Andre"+ i+ "@grandeporte3.com.br")
			.build();
	
	
	
}

}