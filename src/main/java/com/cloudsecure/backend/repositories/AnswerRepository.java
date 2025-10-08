package com.cloudsecure.backend.repositories;

import com.cloudsecure.backend.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}

