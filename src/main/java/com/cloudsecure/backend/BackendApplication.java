package com.cloudsecure.backend;

import com.cloudsecure.backend.models.Question;
import com.cloudsecure.backend.repositories.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// Le bean CommandLineRunner est une méthode annotée @Bean, hors de la méthode main
//	@Bean
//	public CommandLineRunner testQuestionRepo(QuestionRepository questionRepo) {
//		return args -> {
//			List<Question> questions = questionRepo.findAll();
//			questions.forEach(q -> System.out.println("Question: " + q.getText()));
//		};
//	}
}
