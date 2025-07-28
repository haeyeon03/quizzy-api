package study.quizzy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizQuestionResponseDto;
import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.domain.entity.Quiz;
import study.quizzy.domain.entity.QuizAnswer;
import study.quizzy.domain.entity.QuizQuestion;
import study.quizzy.domain.entity.Rank;
import study.quizzy.repository.QuizQuestionRepository;
import study.quizzy.repository.QuizRepository;
import study.quizzy.repository.RankRepository;
import study.quizzy.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<QuizResponseDto> getQuizList(QuizRequestDto request) {
        List<Quiz> entityList = new ArrayList<>();
        List<QuizResponseDto> dtoList = new ArrayList<>();

        String title = request.getTitle();
        if (title == null || title.isBlank()) {
            entityList = quizRepository.findAll();
        } else {
            entityList = quizRepository.findAllByTitle(title);
        }

        for (Quiz quiz : entityList) {
            QuizResponseDto dto = modelMapper.map(quiz, QuizResponseDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public QuizResponseDto getQuizById(QuizRequestDto request) {
        Optional<Quiz> quiz = quizRepository.findById(request.getQuizId());
        QuizResponseDto dto = modelMapper.map(quiz, QuizResponseDto.class);
        return dto;
    }

    @Override
    public List<RankResponseDto> getRankListByQuiz(Long quizId) {
        List<Rank> rankList = rankRepository.findAllById_QuizIdOrderByScoreDescDurationMsAsc(quizId);

        AtomicInteger counter = new AtomicInteger(1);
        return rankList.stream()
                .map(rank -> {
                    RankResponseDto dto = modelMapper.map(rank, RankResponseDto.class);
                    dto.setRankNumber(counter.getAndIncrement());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizAnswerResponseDto> getAnswerListByQuestion(QuizRequestDto request) {
        Long quizId = request.getQuizId();
        Long quizQuestionId = request.getQuizQuestionId();

        QuizQuestion quizQuestion = quizQuestionRepository
                .findByQuizQuestionIdAndQuiz_QuizId(quizQuestionId, quizId)
                .orElseThrow(() -> new EntityNotFoundException("해당 퀴즈의 문제가 존재하지 않습니다."));

        return modelMapper.map(quizQuestion, QuizQuestionResponseDto.class).getQuizAnswerList();
    }
}
