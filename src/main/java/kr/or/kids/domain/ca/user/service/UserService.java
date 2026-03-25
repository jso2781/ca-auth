package kr.or.kids.domain.ca.user.service;


import kr.or.kids.domain.ca.user.vo.UserDeleteReqVO;
import kr.or.kids.domain.ca.user.vo.UserIdReqVO;
import kr.or.kids.domain.ca.user.vo.UserUpdateReqVO;
import kr.or.kids.global.system.common.vo.ApiPrnDto;

/**
 * 사용자 서비스 인터페이스
 */
public interface UserService {

    /**
     * 사용자 목록
     * @return API 응답 DTO
     */
      ApiPrnDto list(int pageNum,int  pageSize);
    /**
     * 사용자 목록
     * @return API 응답 DTO
     */
      ApiPrnDto data(UserIdReqVO userIdReqVO);
    /**
     * 사용자 목록
     * @return API 응답 DTO
     */
      ApiPrnDto insert(UserUpdateReqVO userInsertReqVO);
    /**
     * 사용자 목록
     * @return API 응답 DTO
     */
      ApiPrnDto update(UserUpdateReqVO userUpdateReqVO);
    /**
     * 사용자 목록
     * @return API 응답 DTO
     */
      ApiPrnDto delete(UserDeleteReqVO userDeleteReqVO);

}
