package com.soares.dev.helpee.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soares.dev.helpee.backend.exceptions.ResourceNotFoundException;
import com.soares.dev.helpee.backend.model.Answer;
import com.soares.dev.helpee.backend.repository.AnswerRepository;
import com.soares.dev.helpee.backend.repository.QuestionRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AnswerController {

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@GetMapping("/questions/{questionId}/answers")
	public ResponseEntity<List<Answer>> getAllAnswersByQuestionId(@PathVariable(value = "questionId") Long questionId) {
		if (!questionRepository.existsById(questionId)) {
			throw new ResourceNotFoundException("Not found Question with id = " + questionId);
		}

		List<Answer> answers = answerRepository.findByQuestionId(questionId);
		return new ResponseEntity<>(answers, HttpStatus.OK);
	}

	@GetMapping("/answers/{id}")
	public ResponseEntity<Answer> getAnswerById(@PathVariable("id") long id) {
		Optional<Answer> answerData = answerRepository.findById(id);

		if (answerData.isPresent()) {
			return new ResponseEntity<>(answerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/questions/{questionId}/answers")
	public ResponseEntity<Answer> createAnswer(@PathVariable(value = "questionId") Long questionId,
			@RequestBody Answer answerRequest) {
		Answer answer = questionRepository.findById(questionId).map(question -> {
			answerRequest.setQuestion(question);
			return answerRepository.save(answerRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Question with id = " + questionId));

		return new ResponseEntity<>(answer, HttpStatus.CREATED);
	}

	@GetMapping("/answers")
	public ResponseEntity<List<Answer>> getAllAnswers(@RequestParam(required = false) String description) {
		try {
			List<Answer> answers = new ArrayList<Answer>();

			if (description == null)
				answerRepository.findAll().forEach(answers::add);
			else
				answerRepository.findByDescriptionContaining(description).forEach(answers::add);

			if (answers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(answers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/answers/{id}")
	public ResponseEntity<Answer> updateanswer(@PathVariable("id") long id, @RequestBody Answer answer) {
		Optional<Answer> answerData = answerRepository.findById(id);

		if (answerData.isPresent()) {
			Answer _answer = answerData.get();
			_answer.setDescription(answer.getDescription());
			_answer.setPublished(answer.isPublished());
			return new ResponseEntity<>(answerRepository.save(_answer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/answers/{id}")
	public ResponseEntity<HttpStatus> deleteanswer(@PathVariable("id") long id) {
		try {
			answerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/answers")
	public ResponseEntity<HttpStatus> deleteAllanswers() {
		try {
			answerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/questions/{questionId}/answers")
	public ResponseEntity<List<Answer>> deleteAllAnswersOfQuestion(
			@PathVariable(value = "questionId") Long questionId) {
		if (!questionRepository.existsById(questionId)) {
			throw new ResourceNotFoundException("Not found Question with id = " + questionId);
		}

		answerRepository.deleteByQuestionId(questionId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/answers/published")
	public ResponseEntity<List<Answer>> findByPublished() {
		try {
			List<Answer> answers = answerRepository.findByPublished(true);

			if (answers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(answers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
