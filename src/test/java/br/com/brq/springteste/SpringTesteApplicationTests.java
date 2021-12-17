package br.com.brq.springteste;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringTestApplicationTests {

	
  
	@Test
	void somarTest() {
		int a = 0;
		int b= 1;
		int c = a + b;
		
		assertEquals(1, c);
	}
	

	@Test
	void somarIvalidTest() {
		int a = 0;
		int b= 1;
		int c = a + b;
		
		assertThat(c > 0).isTrue();
	}
}