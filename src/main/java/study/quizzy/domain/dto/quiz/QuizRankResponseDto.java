package study.quizzy.domain.dto.quiz;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuizRankResponseDto {
	private Long quizId;
	private String title;
	private String description;
	private String imageFile;
	
	private int score;
	private int durationMs;
	private LocalDateTime updatedAt;
}
