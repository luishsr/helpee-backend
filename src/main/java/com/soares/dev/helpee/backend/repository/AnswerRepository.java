package com.soares.dev.helpee.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.soares.dev.helpee.backend.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	List<Answer> findByPublished(boolean published);
	List<Answer> findByDescriptionContaining(String description);
	List<Answer> findByQuestionId(Long questionId);
	
	@Transactional
	void deleteByQuestionId(long questionId);
	
}
