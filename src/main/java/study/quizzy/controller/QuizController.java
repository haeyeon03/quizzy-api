package study.quizzy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.response.CustomResponseEntity;
import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.service.QuizService;

@RestController
@RequestMapping("api/quizzes")
public class QuizController {

	@Autowired
	QuizService quizSerive;

	/**
	 * 퀴즈 목록 조회 API
	 * 
	 * @param request title
	 * 
	 * @return 조회 조건에 만족하는 퀴즈 리스트
	 */
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<QuizResponseDto>>> getQuizList(@ModelAttribute QuizRequestDto request) {
		List<QuizResponseDto> quizList = quizSerive.getQuizList(request);
		return CustomResponseEntity.success(quizList);
	}

	/**
	 * 퀴즈(문제 포함) 조회 API
	 * 
	 * @param quizId
	 * @return quizQuestionList
	 */
	@GetMapping("/{quizId}")
	public ResponseEntity<ApiResponse<QuizResponseDto>> getQuizById(@PathVariable Long quizId) {
		QuizResponseDto quiz = quizSerive.getQuizById(quizId);
		return CustomResponseEntity.success(quiz);
	}

	/**
	 * 퀴즈 문제의 정답 목록 조회 API
	 * 
	 * @param request *quizId, *quizQuestionId (*필수)
	 * @return 정답 목록
	 */
	@GetMapping("/{quizId}/{quizQuestionId}")
	public ResponseEntity<ApiResponse<List<QuizAnswerResponseDto>>> getAnswerListByQuizQuestionId(
			@PathVariable Long quizId, @PathVariable Long quizQuestionId) {
		List<QuizAnswerResponseDto> quizAnswerList = quizSerive.getAnswerListByQuizQuestionId(quizId, quizQuestionId);
		return CustomResponseEntity.success(quizAnswerList);
	}

	/**
	 * 퀴즈 등록 API (관리자용)
	 * 
	 * @param request *title, image, questionList, answerList
	 * @return Total number of challengers modified (0 or 1)
	 */
	@PostMapping("/")
	public ResponseEntity<ApiResponse<Long>> addQuiz(@RequestBody QuizRequestDto request) {
		Long added = quizSerive.addQuiz(request);
		return CustomResponseEntity.success(added);
	}

	/**
	 * 퀴즈 수정 API (관리자용)
	 * 
	 * @param request title, image, questionList, answerList
	 * @return Total number of challengers modified (0 or 1)
	 */
	@PutMapping("/{quizId}")
	public ResponseEntity<ApiResponse<Long>> modifyQuiz(@RequestBody QuizRequestDto request) {
		Long modified = quizSerive.modifyQuiz(request);
		return CustomResponseEntity.success(modified);
	}

	/**
	 * 퀴즈 삭제 API 관리자용
	 * 
	 * @param quizId
	 * @return
	 */
	@DeleteMapping("/admin")
	public ResponseEntity<ApiResponse<Long>> removeQuiz(@ModelAttribute QuizRequestDto request) {
		Long removed = quizSerive.removeQuiz(request);
		return CustomResponseEntity.success(removed);
	}

	/**
	 * 도전자 퀴즈 점수 등록 API
	 *
	 * @param quizId, challengerId, inputAnswer
	 * @return
	 */
	@PostMapping("/score")
	public ResponseEntity<ApiResponse<Long>> addScore(@RequestBody RankRequestDto request) {
		Long added = quizSerive.addScore(request);
		return CustomResponseEntity.success(added);
	}

	/**
	 * 퀴즈별 도전자 랭킹 조회 API
	 *
	 * @param quizId 퀴즈 ID
	 * @return 해당 퀴즈에 대한 도전자 랭킹 목록
	 */
	@GetMapping("/ranks/{quizId}")
	public ResponseEntity<ApiResponse<List<RankResponseDto>>> getRankListByQuiz(@PathVariable Long quizId) {
		List<RankResponseDto> rankList = quizSerive.getRankListByQuiz(quizId);
		return CustomResponseEntity.success(rankList);
	}
}
