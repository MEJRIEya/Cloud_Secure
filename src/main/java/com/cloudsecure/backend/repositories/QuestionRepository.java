package com.cloudsecure.backend.repositories;

import com.cloudsecure.backend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(String category);
    boolean existsByText(String text);
}

