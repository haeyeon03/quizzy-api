package study.quizzy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import study.quizzy.service.QuizService;

@RestController
public class QuizController {
	
	@Autowired
	QuizService quizService;

}
