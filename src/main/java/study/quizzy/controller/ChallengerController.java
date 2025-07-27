package study.quizzy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.response.CustomResponseEntity;
import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.service.ChallengerService;

import java.util.List;

@RestController
@RequestMapping("api/challengers")
public class ChallengerController {

    @Autowired
    ChallengerService challengerService;

    /**
     * 도전자 목록 조회 API
     *
     * @param request *quizId, nickname, *curPage, *pageSize (*필수)
     * @return challengerList
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ChallengerResponseDto>>> getChallengerList(@ModelAttribute ChallengerRequestDto request) {
        List<ChallengerResponseDto> challengerList = challengerService.getChallengerList(request);
        return CustomResponseEntity.success(challengerList);
    }

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
	 * 도전자 정보 수정 API
	 * 
	 * @param request nickname, password
	 * @return Total number of challengers modified (0 or 1)
	 */
	@PutMapping("/")
	public ResponseEntity<ApiResponse<Long>> modifyChallenger(@RequestBody ChallengerRequestDto request) {
		Long modified = challengerService.modifyChallenger(request);
		return CustomResponseEntity.success(modified);
	}

	/**
	 * 도전자 정보 삭제 API (탈퇴)
	 *
	 * @param request *challengerId (*필수)
	 * @return Total number of challengers removed (0 or 1)
	 */
	@DeleteMapping("/")
	public ResponseEntity<ApiResponse<Long>> removeChallenger(@ModelAttribute ChallengerRequestDto request) {
		Long removed = challengerService.removeChallenger(request);
		return CustomResponseEntity.success(removed);
	}


	/**
	 * 도전자가 참여한 퀴즈의 랭킹 등록
	 *
	 * @param request *quizId, *challengerId, *score, *durationMs
	 * @return 해당 도전자가 참여한 퀴즈별 랭킹 목록
	 */
	@PostMapping("/ranks")
	public ResponseEntity<ApiResponse<Long>> addRankByChallenger(@RequestBody RankRequestDto request) {
		Long added = challengerService.addRankByChallenger(request);
		return CustomResponseEntity.success(added);
	}

	/**
	 * 도전자가 참여한 퀴즈별 랭킹 조회
	 *
	 * @param challengerId 도전자 ID
	 * @return 해당 도전자가 참여한 퀴즈별 랭킹 목록
	 */
	@GetMapping("/ranks/{challengerId}")
	public ResponseEntity<ApiResponse<List<RankResponseDto>>> getRankListByChallenger(@PathVariable String challengerId) {
		List<RankResponseDto> rankList = challengerService.getRankListByChallenger(challengerId);
		return CustomResponseEntity.success(rankList);
	}
}
