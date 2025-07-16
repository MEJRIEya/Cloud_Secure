package com.cloudsecure.backend.repositories;

import com.cloudsecure.backend.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {}

