package study.quizzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.quizzy.domain.dto.QuizRequestDto;
import study.quizzy.domain.dto.QuizResponseDto;
import study.quizzy.domain.entity.Quiz;
import study.quizzy.repository.QuizRepository;
import study.quizzy.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<QuizResponseDto> getQuizList(QuizRequestDto request) {
		List<Quiz> entityList = new ArrayList<>();
		List<QuizResponseDto> dtoList = new ArrayList<>();
		
		String title = request.getTitle();
		if (title == null || title.isBlank()) {
			entityList = quizRepository.findAll();
		}else {
			entityList = quizRepository.findAllByTitle(title);
		}
		
		for (Quiz quiz : entityList) {
			QuizResponseDto dto = modelMapper.map(quiz, QuizResponseDto.class);
			dtoList.add(dto);
		}
		return dtoList;
	}

}
