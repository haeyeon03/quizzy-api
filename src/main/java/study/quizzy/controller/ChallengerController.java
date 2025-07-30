package study.quizzy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.response.CustomResponseEntity;
import study.quizzy.domain.dto.challenger.ChallengerAuthDto;
import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRankResponseDto;
import study.quizzy.service.ChallengerService;

import java.util.List;

@RestController
@RequestMapping("api/challengers")
public class ChallengerController {

    @Autowired
    ChallengerService challengerService;

	/**
	 * 도전자 정보 생성 API (회원가입)
	 *
	 *
	 * @param request *challengerId, *nickname, *password, provider, email (*필수)
	 * @return Total number of challengers added (0 or 1)
	 */
	@PostMapping("/")
	public ResponseEntity<ApiResponse<Long>> addChallenger(@RequestBody ChallengerRequestDto request) {
		Long added = challengerService.addChallenger(request);
		return CustomResponseEntity.success(added);
	}

    /**
     * 도전자 정보 목록 조회 API (관리자용)
     *
     * @param request nickname, *curPage, *pageSize (*필수)
     * @return 도전자 목록
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ChallengerResponseDto>>> getChallengerList(@ModelAttribute ChallengerRequestDto request) {
        List<ChallengerResponseDto> challengerList = challengerService.getChallengerList(request);
        return CustomResponseEntity.success(challengerList);
    }

	/**
	 * 특정 도전자 정보 조회 API (관리자용)
	 *
	 * @param challengerId 도전자 ID
	 * @return challengerList
	 */
	@GetMapping("/{challengerId}")
	public ResponseEntity<ApiResponse<ChallengerResponseDto>> getChallenger(@PathVariable String challengerId) {
		ChallengerResponseDto challenger = challengerService.getChallenger(challengerId);
		return CustomResponseEntity.success(challenger);
	}

	/**
	 * 도전자 정보 수정 API (관리자용)
	 *
	 * @param challengerId 도전자 ID
	 * @param request nickname, password
	 * @return Total number of challengers modified (0 or 1)
	 */
	@PutMapping("/{challengerId}")
	public ResponseEntity<ApiResponse<Long>> modifyChallenger(@PathVariable String challengerId, @RequestBody ChallengerRequestDto request) {
		Long modified = challengerService.modifyChallenger(challengerId, request);
		return CustomResponseEntity.success(modified);
	}

	/**
	 * 도전자 정보 삭제 API (관리자용)
	 *
	 * @param challengerId 도전자 ID
	 * @return Total number of challengers removed (0 or 1)
	 */
	@DeleteMapping("/{challengerId}")
	public ResponseEntity<ApiResponse<Long>> removeChallenger(@PathVariable String challengerId) {
		Long removed = challengerService.removeChallenger(challengerId);
		return CustomResponseEntity.success(removed);
	}

	/**
	 * 내 정보 조회 API
	 *
	 * @param my 로그인된 도전자 정보
	 * @return Total number of challengers modified (0 or 1)
	 */
	@GetMapping("/my")
	public ResponseEntity<ApiResponse<ChallengerResponseDto>> getMyInfo(@AuthenticationPrincipal ChallengerAuthDto my) {
		ChallengerResponseDto challenger = challengerService.getChallenger(my.getChallengerId());
		return CustomResponseEntity.success(challenger);
	}

	/**
	 * 내 정보 수정 API
	 *
	 * @param my 로그인된 도전자 정보
	 * @return Total number of challengers modified (0 or 1)
	 */
	@PutMapping("/my")
	public ResponseEntity<ApiResponse<Long>> modifyMyInfo(@AuthenticationPrincipal ChallengerAuthDto my, @RequestBody ChallengerRequestDto request) {
		Long modified = challengerService.modifyChallenger(my.getChallengerId(), request);
		return CustomResponseEntity.success(modified);
	}

	/**
	 * 내 정보 삭제 API
	 *
	 * @param my 로그인된 도전자 정보
	 * @return Total number of challengers modified (0 or 1)
	 */
	@DeleteMapping("/my")
	public ResponseEntity<ApiResponse<Long>> removeMyInfo(@AuthenticationPrincipal ChallengerAuthDto my) {
		Long removed = challengerService.removeChallenger(my.getChallengerId());
		return CustomResponseEntity.success(removed);
	}

	/**
	 * 내가 참여한 퀴즈 목록 조회 API
	 *
	 * @param my 로그인된 도전자 정보
	 * @return 내가 참여한 퀴즈 목록
	 */
	@GetMapping("/my/quizzes")
	public ResponseEntity<ApiResponse<List<QuizRankResponseDto>>> getMyQuizList(@AuthenticationPrincipal ChallengerAuthDto my) {
		List<QuizRankResponseDto> quizList = challengerService.getMyQuizList(my.getChallengerId());
		return CustomResponseEntity.success(quizList);
	}
}
