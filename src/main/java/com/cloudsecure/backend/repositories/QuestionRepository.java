package com.cloudsecure.backend.repositories;

import com.cloudsecure.backend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {}

