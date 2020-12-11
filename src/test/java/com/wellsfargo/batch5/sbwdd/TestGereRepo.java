package com.wellsfargo.batch5.sbwdd;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.wellsfargo.batch5.sbwdd.entity.GenreEntity;
import com.wellsfargo.batch5.sbwdd.repo.GenreRepo;

@DataJpaTest
public class TestGereRepo {

	
		
		@Autowired
		
		private TestEntityManager entityManager;//used to verify effects on DB
		
		@Autowired
		
		private GenreRepo genreRepo;//to be tested
		
		
		private GenreEntity[] testData;
		
		@BeforeEach
		 void beforeEachTest() {
			testData= new GenreEntity[] {
					
					new GenreEntity(101,"Fiction"),
					new GenreEntity(102,"Science"),
					new GenreEntity(102,"Computers")
			};
			
			for (GenreEntity ge:testData) {
				
				entityManager.persistAndFlush(ge);
			}
			
		}
		

		@AfterEach
		 void afterEachTest() {
			
			
			for (GenreEntity ge:testData) {
				
				entityManager.remove(ge);
			}
			entityManager.flush();
		}
		
		
		@Test
		
		void testFindAll() {
			
			List<GenreEntity> genresList= genreRepo.findAll();
			
			GenreEntity[] actualData= genresList.toArray(new GenreEntity[] {});
			
			assertArrayEquals(actualData,testData);
	
			
		}
	}

		
	

