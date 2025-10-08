package com.cloudsecure.backend;

import com.cloudsecure.backend.models.Question;
import com.cloudsecure.backend.models.Resource;
import com.cloudsecure.backend.repositories.QuestionRepository;
import com.cloudsecure.backend.repositories.ResourceRepository;
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

//	@Bean
//	public CommandLineRunner loadData(ResourceRepository repo) {
//		return args -> {
//			if (repo.count() == 0) { // si la table est vide
//				Resource r1 = new Resource();
//				r1.setTitle("Guide sécurité cloud");
//				r1.setLink("https://example.com/guide-securite-cloud");
//				r1.setType("article");
//				r1.setDescription("Bonnes pratiques pour sécuriser le cloud");
//
//				Resource r2 = new Resource();
//				r2.setTitle("Vidéo sensibilisation sécurité");
//				r2.setLink("https://youtube.com/vid-sensibilisation");
//				r2.setType("video");
//				r2.setDescription("Vidéo sur les risques liés au cloud");
//
//				repo.save(r1);
//				repo.save(r2);
//
//				System.out.println("Ressources initialisées");
//			}
//		};
//	}

    // Le bean CommandLineRunner est une méthode annotée @Bean, hors de la méthode main
//	@Bean
//	public CommandLineRunner testQuestionRepo(QuestionRepository questionRepo) {
//		return args -> {
//			List<Question> questions = questionRepo.findAll();
//			questions.forEach(q -> System.out.println("Question: " + q.getText()));
//		};
//	}
}
