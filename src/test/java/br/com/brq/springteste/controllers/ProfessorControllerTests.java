package br.com.brq.springteste.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.brq.springteste.dto.ProfessorDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
 class ProfessorControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/*
	 * {
    	"nome" : "Fabrizio 4",
    	"email": "fabrizio@grandeporte.com.br"
	   }
	   
	   "{ \"nome\" : \"Fabrizio 4"\}"
	 * */
	
	@Test
	 void saveTest() throws JsonProcessingException, Exception {
		ProfessorDTO dto = this.createValidProfessor();
		
		// criando a requisição POST para enviar para o endpoint a ser testado
		ResultActions response = mockMvc
				.perform(
						post("/professores")
						.content( objectMapper.writeValueAsString(dto) )
						.contentType("application/json")
				);
		//executando a requisição acima e armanzenando o resultado da API
		MvcResult result = response.andReturn();
		
		// pegando o corpo da resposta em String 
		String objStr = result.getResponse().getContentAsString();
		
		// convertendo a resposta String para ProfessorDTO objeto
		ProfessorDTO dtoResult = objectMapper.readValue(objStr, ProfessorDTO.class);
		
		assertThat( dtoResult.getId() ).isPositive();
		assertThat( dtoResult.getNome() ).isEqualTo(dto.getNome());
		assertThat( dtoResult.getEmail() ).isEqualTo(dto.getEmail());
	}
	
	@Test
	void getAllTest() throws Exception {		
		
		ResultActions response = mockMvc
							.perform(
									get("/professores")
									.contentType("application/json")
							);
		MvcResult result = response.andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		
		System.out.println(resultStr);
		
		ProfessorDTO[] list = objectMapper.readValue(resultStr, ProfessorDTO[].class );
		
		System.out.println(list.length);
		
		// apenas comparando o status da resposta
		assertThat( result.getResponse().getStatus() ).isEqualTo(HttpStatus.OK.value());
		assertThat ( list.length >=0 ).isTrue();
	}
	
	@Test
	void findByNomeTest() throws Exception {



	ResultActions response = mockMvc.perform(get("/professores/busca-nome/Fabrizio").contentType("application/json"));
	MvcResult result = response.andReturn();

	String resultStr = result.getResponse().getContentAsString();

	ProfessorDTO[] list = objectMapper.readValue(resultStr, ProfessorDTO[].class);

	// apenas comparando o status da resposta
	assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	assertThat(list.length >= 0).isTrue();
	}
	
	
	@Test
	 void getOneTest() throws Exception {
		
		ResultActions response = 
					mockMvc.perform(
							get("/professores/1")
							.contentType("application/json")
					);
		
		MvcResult result = response.andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		
		ProfessorDTO professorDTO = objectMapper.readValue(resultStr, ProfessorDTO.class);
		System.out.println(professorDTO);
		
		// apenas comparando o status da resposta
		assertThat( result.getResponse().getStatus() ).isEqualTo(HttpStatus.OK.value());
		assertThat( professorDTO.getId() ).isEqualTo(1);
		
	}
	
	@Test
	@DisplayName("Deve alterar o professor")
 void updateTest() throws JsonProcessingException, Exception {
		ProfessorDTO dto = this.createValidProfessor();
		
		int idProfessor = 1;
		
		ResultActions response = mockMvc
				.perform(
						put("/professores/"+idProfessor)
						.content(objectMapper.writeValueAsString(dto))
						.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		
		ProfessorDTO updated = objectMapper.readValue(resultStr, ProfessorDTO.class);
		
		assertThat( updated.getId()  ).isEqualTo(idProfessor);
		assertThat( updated.getNome() ).isEqualTo(dto.getNome());
		assertThat( updated.getEmail() ).isEqualTo(dto.getEmail());
		assertThat( result.getResponse().getStatus() ).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("Deve deletar um professor")
	 void deleteTest() throws Exception {
		
		int idProfessor = 1;
		
		ResultActions response = mockMvc
				.perform(
						delete("/professores/"+idProfessor)
						.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		
		assertThat( result.getResponse().getStatus() ).isEqualTo(HttpStatus.OK.value());
	}
	
	private ProfessorDTO createValidProfessor() {
		
//		ProfessorDTO dto = new ProfessorDTO();
//		dto.setNome("Fabrizio");
//		dto.setEmail("fabrizio@grandeporte.com.br");
		
		ProfessorDTO dto = ProfessorDTO
								.builder()
								.nome("Fabrizio")
								.email("fabrizio@grandeporte.com.br")
								.build();
		
		return dto;
	}
	

}