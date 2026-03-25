package kr.or.kids.domain.ca.user.mapper;

import kr.or.kids.domain.ca.user.vo.*;
import kr.or.kids.domain.ca.user.vo.UserDataResVO;
import kr.or.kids.domain.ca.user.vo.UserDeleteReqVO;
import kr.or.kids.domain.ca.user.vo.UserIdReqVO;
import kr.or.kids.domain.ca.user.vo.UserUpdateReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**  사용자 목록 조회 */
    List<UserDataResVO> list();
    /**  사용자 정보 단건 조회 */
    UserDataResVO data(UserIdReqVO param);
    /**  사용자  일련번호 조회 */
    public long nextUserId();
    /**  사용자 정보 단건 등록 */
    public int insert(UserUpdateReqVO param);
    /**  사용자 정보 단건 수정 */
    int update(UserUpdateReqVO param);
    /**  사용자 정보 단건 삭제 */
    int delete(UserDeleteReqVO param);

}
