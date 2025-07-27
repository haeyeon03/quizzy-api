package study.quizzy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import study.quizzy.domain.dto.QuizRequestDto;
import study.quizzy.domain.dto.QuizResponseDto;
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
	public ResponseEntity<?> getQuizList(@ModelAttribute QuizRequestDto request) {
		List<QuizResponseDto> quizList = quizSerive.getQuizList(request);
		return ResponseEntity.ok(quizList);
	}

	/**
	 * 퀴즈 문제 리스트 조회 API
	 * 
	 * @param quizId
	 * @return quizQuestionList
	 */

	/**
	 * 퀴즈 조회 API 관리자용
	 * 
	 * @param quizId
	 * @return quiz
	 */
	
	
	/**
	 * 퀴즈 조회 API 관리자용
	 * 
	 * @param quizId
	 * @return quiz
	 */


	/**
	 * 퀴즈 정답 조회 API
	 * 
	 * @param quizQuestionId, answer
	 * @return isCorrect
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

}
