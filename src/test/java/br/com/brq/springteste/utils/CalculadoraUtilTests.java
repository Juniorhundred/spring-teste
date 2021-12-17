package br.com.brq.springteste.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
 class CalduladoraUtilTests {
	
	//inStanciar
	private CalculadoraUtil calculadoraUtil = new CalculadoraUtil();
	
	
	
	@Test
	void somarValidTest() {
	  int a = 0;
	  int b = 1;
	  
	  int atual = this.calculadoraUtil.somar(a, b);
	  
	  assertEquals(1, atual);
	   
	}
}