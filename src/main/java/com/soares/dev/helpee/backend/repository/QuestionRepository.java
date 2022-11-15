package com.soares.dev.helpee.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soares.dev.helpee.backend.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByPublished(boolean published);
	List<Question> findByTitleContaining(String title);
}
