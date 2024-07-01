package olama.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan

@SpringBootTest
@ComponentScan(lazyInit = true)
class OlamaApiApplicationTests {

	@Test
	fun contextLoads() {
	}

}
