package study.quizzy.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import study.quizzy.domain.dto.quiz.QuizRankResponseDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.domain.entity.Rank;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Entity -> DTO 변환 시 중복 필드 무시
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        configureRankToDtoMap(modelMapper); // Custom RankResponseDTO
        configureQuizRankToDtoMap(modelMapper); // // Custom QuizRankResponseDto

        return modelMapper;
    }

    private void configureRankToDtoMap(ModelMapper modelMapper) {
        modelMapper.typeMap(Rank.class, RankResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getId().getQuizId(), RankResponseDto::setQuizId);
            mapper.map(src -> src.getId().getChallengerId(), RankResponseDto::setChallengerId);
            mapper.map(src -> src.getQuiz().getTitle(), RankResponseDto::setTitle);
            mapper.map(src -> src.getChallenger().getNickname(), RankResponseDto::setNickname);
        });
    }
    
    private void configureQuizRankToDtoMap(ModelMapper modelMapper) {
        modelMapper.typeMap(Rank.class, QuizRankResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getId().getQuizId(), QuizRankResponseDto::setQuizId);
            mapper.map(src -> src.getQuiz().getTitle(), QuizRankResponseDto::setTitle);
            mapper.map(src -> src.getQuiz().getDescription(), QuizRankResponseDto::setDescription);
            mapper.map(src -> src.getQuiz().getImageFile(), QuizRankResponseDto::setImageFile);
            mapper.map(src -> src.getScore(), QuizRankResponseDto::setScore);
            mapper.map(src -> src.getDurationMs(), QuizRankResponseDto::setDurationMs);
            mapper.map(src -> src.getUpdatedAt(), QuizRankResponseDto::setUpdatedAt);
        });
    }

}
