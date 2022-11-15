package com.soares.dev.helpee.backend;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soares.dev.helpee.backend.controller.QuestionController;
 
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultTest {
 
    @Autowired
    private QuestionController questionControllerA;
     
    @Test
    public void verifyQuestionControllerConfigured() {
    	/*
    	 * TO-DO: implement tests
    	 */
    	assertNotNull(new QuestionController());
    }
     
    @Configuration
    static class Config {
        @Bean
        QuestionController questionControllerA() {
            return new QuestionController();
        }
    }
}