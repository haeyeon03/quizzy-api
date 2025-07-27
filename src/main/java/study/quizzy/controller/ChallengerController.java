package study.quizzy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.response.CustomResponseEntity;
import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
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
     * @param request nickname, curPage, pageSize
     * @return challengerList
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ChallengerResponseDto>>> getChallengerList(ChallengerRequestDto request) {
        List<ChallengerResponseDto> challengerList = challengerService.getChallengerList(request);
        return CustomResponseEntity.success(challengerList);
    }

	/**
	 * 도전자 정보 수정 API
	 * 
	 * @param challenger_id,nickname,password
	 * @return -
	 */

	/**
	 * 도전자 순위 조회 API
	 * 
	 * @param challenger_id,page,pageSize
	 * @return score,rank,quizTitle
	 */
}
