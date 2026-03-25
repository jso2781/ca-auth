package kr.or.kids.domain.ca.user.controller;

import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.or.kids.domain.ca.user.service.UserService;
import kr.or.kids.domain.ca.user.vo.UserDeleteReqVO;
import kr.or.kids.domain.ca.user.vo.UserIdReqVO;
import kr.or.kids.domain.ca.user.vo.UserUpdateReqVO;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import kr.or.kids.global.system.common.vo.PageRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 사용자목록
     * @param  pageRequestDto 페이지용 데이터
     * @param request HTTP 요청
     * @return API 응답
     */
    @Operation( summary = "사용자 목록 조회", description = "사용자 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "사용자 조회 성공")
    @PostMapping("/list")
    public ResponseEntity<ApiPrnDto> userList(@RequestBody PageRequestDto pageRequestDto, HttpServletRequest request) {

        int pageNum = pageRequestDto != null ? pageRequestDto.getPageNum() : 1;
        int pageSize = pageRequestDto != null ? pageRequestDto.getPageSize() : 10;

        ApiPrnDto apiPrnDto = userService.list(pageNum, pageSize);
        ApiResultCode resultCode = ApiResultCode.fromCode(apiPrnDto.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(apiPrnDto);
    }

    /**
     * 사용자상세조회API
     * @param input
     * @return
     */
    @PostMapping("/data")
    public ResponseEntity<ApiPrnDto> data(@RequestBody UserIdReqVO input, HttpServletRequest request) {
        ApiPrnDto apiPrnDto = userService.data(input);
        ApiResultCode resultCode = ApiResultCode.fromCode(apiPrnDto.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(apiPrnDto);
    }


    /**
     * 사용자등록처리API
     * @param input
     * @return
     */
    @PostMapping("/insert")
    public ResponseEntity<ApiPrnDto> insert(@RequestBody UserUpdateReqVO input, HttpServletRequest request) {
        ApiPrnDto apiPrnDto = userService.insert(input);
        ApiResultCode resultCode = ApiResultCode.fromCode(apiPrnDto.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(apiPrnDto);
    }

    /**
     * 사용자수정처리API
     * @param input
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<ApiPrnDto> update(@RequestBody UserUpdateReqVO input, HttpServletRequest request) {
        ApiPrnDto apiPrnDto = userService.update(input);
        ApiResultCode resultCode = ApiResultCode.fromCode(apiPrnDto.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(apiPrnDto);
    }

    /**
     * 사용자수정삭제API
     * @param input
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<ApiPrnDto> delete(@RequestBody UserDeleteReqVO input, HttpServletRequest request) {
        ApiPrnDto apiPrnDto = userService.delete(input);
        ApiResultCode resultCode = ApiResultCode.fromCode(apiPrnDto.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(apiPrnDto);
     }


}
