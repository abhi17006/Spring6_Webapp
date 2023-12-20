package com.Spring6webapp;

import com.Spring6webapp.depedencyInjection.DemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Spring6WebappApplicationTests {

	@Autowired
	DemoController demoController;
    @Test
	void testDemoController(){
		System.out.println(demoController.getDailyWorkout());
	}

	@Test
	void contextLoads() {
	}

}
