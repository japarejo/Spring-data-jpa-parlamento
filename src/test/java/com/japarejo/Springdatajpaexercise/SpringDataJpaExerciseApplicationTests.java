package com.japarejo.Springdatajpaexercise;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes= {SpringDataJpaExerciseApplicationTests.class})
public class SpringDataJpaExerciseApplicationTests {

	@Test
	public void contextLoads() {
		assertTrue(true);
	}

}
