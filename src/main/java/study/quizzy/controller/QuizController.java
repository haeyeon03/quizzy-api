package study.quizzy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.response.CustomResponseEntity;
import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.service.QuizService;

@RestController
@RequestMapping("api/quizzes")
public class QuizController {

	@Autowired
	QuizService quizSerive;

	/**
	 * 퀴즈 리스트 조회 API
	 * 
	 * @param QuizRequestDto.title          사용자가 입력한 퀴즈 제목 검색 텍스트
	 * @param QuizRequestDto.super.curPage  사용자가 선택한 페이지 넘버
	 * @param QuizRequestDto.super.pageSize 사용자가 지정한 페이지 사이즈(한 페이지당 보여지는 퀴즈 수)
	 * 
	 * @return 조회 조건에 만족하는 퀴즈 리스트
	 */
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<QuizResponseDto>>> getQuizList(@ModelAttribute QuizRequestDto request) {
		List<QuizResponseDto> quizList = quizSerive.getQuizList(request);
		return CustomResponseEntity.success(quizList);
	}

	/**
	 * 퀴즈 문제 리스트 조회 API
	 * 
	 * @param quizId
	 * @return quizQuestionList
	 */
	@GetMapping("/questions")
	public ResponseEntity<ApiResponse<QuizResponseDto>> getQuizById(@ModelAttribute QuizRequestDto request){
		QuizResponseDto quiz = quizSerive.getQuizById(request);
		return CustomResponseEntity.success(quiz);
	}
	
	/**
	 * 퀴즈 정답 조회 API
	 * 
	 * @param request *quizId, *quizQuestionId (*필수)
	 * @return 정답 목록
	 */
	@GetMapping("/answers")
	public ResponseEntity<ApiResponse<List<QuizAnswerResponseDto>>> getAnswerListByQuestion(@ModelAttribute QuizRequestDto request){
		List<QuizAnswerResponseDto> quizAnswerList = quizSerive.getAnswerListByQuestion(request);
		return CustomResponseEntity.success(quizAnswerList);
	}
	
	/**
	 * 퀴즈 조회 API 관리자용
	 * 
	 * @param quizId
	 * @return quiz
	 */
	
	/**
	 * 퀴즈 등록 API 관리자용
	 * 
	 * @param title,image,questionList,answerList
	 * @return
	 */

	/**
	 * 퀴즈 수정 API 관리자용
	 * 
	 * @param title,image,questionList,answerList
	 * @return
	 */

	/**
	 * 퀴즈 삭제 API 관리자용
	 * 
	 * @param quizId
	 * @return
	 */
	
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
