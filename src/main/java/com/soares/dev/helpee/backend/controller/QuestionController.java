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

import com.soares.dev.helpee.backend.model.Question;
import com.soares.dev.helpee.backend.repository.QuestionRepository;

@RestController
@RequestMapping("/api")
public class QuestionController {

	@Autowired
	QuestionRepository questionRepository;

	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllquestions(@RequestParam(required = false) String title) {
		try {
			List<Question> questions = new ArrayList<Question>();

			if (title == null)
				questionRepository.findAll().forEach(questions::add);
			else
				questionRepository.findByTitleContaining(title).forEach(questions::add);

			if (questions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(questions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/questions/{id}")
	public ResponseEntity<Question> getquestionById(@PathVariable("id") long id) {
		Optional<Question> questionData = questionRepository.findById(id);

		if (questionData.isPresent()) {
			return new ResponseEntity<>(questionData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/questions")
	public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
		try {
			Question _question = questionRepository
					.save(new Question(question.getTitle(), question.getDescription(), false, question.getUserId()));
			return new ResponseEntity<>(_question, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/questions/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable("id") long id, @RequestBody Question question) {
		Optional<Question> questionData = questionRepository.findById(id);

		if (questionData.isPresent()) {
			Question _question = questionData.get();
			_question.setTitle(question.getTitle());
			_question.setDescription(question.getDescription());
			_question.setPublished(question.isPublished());
			return new ResponseEntity<>(questionRepository.save(_question), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/questions/{id}")
	public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("id") long id) {
		try {
			questionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/questions")
	public ResponseEntity<HttpStatus> deleteAllQuestions() {
		try {
			questionRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/questions/published")
	public ResponseEntity<List<Question>> findByPublished() {
		try {
			List<Question> questions = questionRepository.findByPublished(true);

			if (questions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(questions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
