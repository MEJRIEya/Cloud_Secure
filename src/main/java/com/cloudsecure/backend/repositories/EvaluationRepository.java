package com.cloudsecure.backend.repositories;

import com.cloudsecure.backend.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findByUserId(Long userId);
}

