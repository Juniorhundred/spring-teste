package br.com.brq.springteste.dto;

import org.modelmapper.ModelMapper;

import br.com.brq.springteste.domain.ProfessorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO {
	
	private Integer id;
	
	private String nome;
	
	private String email;

	public ProfessorEntity toEntity() {
		var mapper = new ModelMapper();
		return mapper.map(this, ProfessorEntity.class);		
	}
	
	
}