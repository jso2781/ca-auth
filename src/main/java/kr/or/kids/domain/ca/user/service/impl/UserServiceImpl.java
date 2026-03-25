package kr.or.kids.domain.ca.user.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.or.kids.domain.ca.user.vo.*;
import kr.or.kids.domain.ca.user.mapper.UserMapper;
import kr.or.kids.domain.ca.user.service.UserService;
import kr.or.kids.domain.ca.user.vo.UserDataResVO;
import kr.or.kids.domain.ca.user.vo.UserDeleteReqVO;
import kr.or.kids.domain.ca.user.vo.UserIdReqVO;
import kr.or.kids.domain.ca.user.vo.UserUpdateReqVO;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import kr.or.kids.global.util.DrugsafeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

import static kr.or.kids.global.util.DrugsafeUtil.markingFunc;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiPrnDto list(int pageNum,int pageSize) {
        ApiPrnDto apiPrnDto = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> data = new HashMap<>();
        
        try {
            log.info("=== 페이징 요청: pageNum={}, pageSize={} ===", pageNum, pageSize);

            // ⭐ PageHelper는 반드시 쿼리 실행 전에 호출해야 함
            PageHelper.startPage(pageNum, pageSize);

            // UserMapper의 list() 메서드 호출 (PageHelper가 자동으로 페이징 쿼리 적용)
            List<UserDataResVO> userList = userMapper.list();

            log.info("=== DB 조회 결과: userList.size()={} ===", userList.size());

            // PageInfo로 감싸서 페이징 정보 추출
            PageInfo<UserDataResVO> pageInfo = new PageInfo<>(userList);

            // 마스킹처리
            List<UserDataResVO> list = pageInfo.getList();

            for (UserDataResVO user : list) {
                if (user.getUserName() != null) {
                    String maskedName = markingFunc("name", user.getUserName());
                    user.setUserName(maskedName);
                }
                if (user.getUserTel() != null) {
                    String maskedTel = markingFunc("tel", user.getUserTel());
                    user.setUserTel(maskedTel);
                }
            }

            // 페이징 정보 및 데이터 설정
            data.put("pageNum", pageInfo.getPageNum());           // 현재 페이지 번호
            data.put("pageSize", pageInfo.getPageSize());         // 페이지당 개수
            data.put("totalPages", pageInfo.getPages());          // 총 페이지 수
            data.put("totalCount", pageInfo.getTotal());          // 전체 데이터 개수
            data.put("list", list);                               // 현재 페이지 데이터
            data.put("isFirstPage", pageInfo.isIsFirstPage());    // 첫 페이지 여부
            data.put("isLastPage", pageInfo.isIsLastPage());      // 마지막 페이지 여부
            data.put("hasPreviousPage", pageInfo.isHasPreviousPage()); // 이전 페이지 존재 여부
            data.put("hasNextPage", pageInfo.isHasNextPage());    // 다음 페이지 존재 여부
            // 데이터가 없어도 성공으로 처리 (HTTP 200)
            if (userList.size() == 0) {
                apiPrnDto.setMsg("조회된 데이터가 없습니다.");
            } else {
                apiPrnDto.setMsg("사용자 목록 조회 완료");
            }

            log.info("사용자 목록 조회 완료: {} 건", userList.size());
            
        } catch (Exception e) {
            log.error("사용자 목록 조회 실패", e);
            apiPrnDto = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            apiPrnDto.setMsg("사용자 목록 조회 중 오류가 발생했습니다.");
        }

        apiPrnDto.setData(data);
        return apiPrnDto;
    }


    /**
     * 사용자정보조회
     * @return
     */
    public ApiPrnDto data(UserIdReqVO userIdReqVO) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> resData = new HashMap<>();

        try {
            if(userIdReqVO.getUserId() == null) {
                throw new RuntimeException("userId 파라미터가 누락되었습니다.");
            }

            /**
             * BizProc
             */
            UserDataResVO data = userMapper.data(userIdReqVO);
            if(data == null) {
                result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
                result.setMsg("사용자정보가 존재하지 않습니다.");
            } else {
                // data가 null이 아닐 때 실행
                resData = new ObjectMapper().convertValue(data, new TypeReference<HashMap<String, Object>>() {});
                result.setMsg("사용자 단건 조회 완료");
            }
        } catch(Exception e) {
            log.error("사용자 조회 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("사용자 조회 중 오류가 발생했습니다: " + e.getMessage());
        }

        result.setData(resData);
        return result;
    }

    /**
     * 사용자정보등록
     * @param param
     * @return
     */
    @Transactional
    public ApiPrnDto insert(UserUpdateReqVO insertVO) {

        ApiPrnDto apiPrnDto             = new ApiPrnDto();
        HashMap<String, Object> bizData = new HashMap<>();

        try {
            if(!StringUtils.hasLength(insertVO.getUserLoginId())) {
                throw new RuntimeException("userLoginId 파라미터가 누락되었습니다.");
            }

            if(!StringUtils.hasLength(insertVO.getUserPw())) {
                throw new RuntimeException("userPw 파라미터가 누락되었습니다.");
            }

            /**
             * BizProc
             */
            Long userId = userMapper.nextUserId();

            String userPw = passwordEncoder.encode(insertVO.getUserPw());

            insertVO.setUserId(userId);
            insertVO.setInAdminId(1);
            insertVO.setDelYn("N");
            insertVO.setUserPw(userPw);

            userMapper.insert(insertVO);

            /**
             * Token 생성
             */

            apiPrnDto.setMsg("사용자정보가 등록되었습니다.");
            bizData.put("userId", userId);


        } catch(Exception e) {
            apiPrnDto = DrugsafeUtil.getApiPrnDto("-1", e.toString());
        }

        apiPrnDto.setData(bizData);
        return apiPrnDto;
    }

    /**
     * 사용자정보수정
     * @param param
     * @return
     */
    @Transactional
    public ApiPrnDto update(UserUpdateReqVO updateVO) {

        ApiPrnDto apiPrnDto = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> bizData = new HashMap<>();

        try {
            if(updateVO.getUserId() == null) {
                throw new RuntimeException("userId 파라미터가 누락되었습니다.");
            }

            /**
             * BizProc
             */
            if(StringUtils.hasLength(updateVO.getUserPw())) {
                String userPw = passwordEncoder.encode(updateVO.getUserPw());
                updateVO.setUserPw(userPw);
            }
            int rtn = userMapper.update(updateVO);
            if(rtn > 0) {
                apiPrnDto.setMsg("사용자정보가 수정되었습니다.");
                bizData.put("userId", updateVO.getUserId());

            } else {
                apiPrnDto = DrugsafeUtil.getApiPrnDto("1", "수정할사용자가 존재하지 않습니다.");
            }
        } catch(Exception e) {
            apiPrnDto = DrugsafeUtil.getApiPrnDto("-1", e.toString());
        }

        apiPrnDto.setData(bizData);
        return apiPrnDto;
    }

    /**
     * 코드 저장 (등록/수정)
     * @return
     */
    @Transactional
    public ApiPrnDto save(UserUpdateReqVO param) {
        ApiPrnDto apiPrnDto = new ApiPrnDto();
        HashMap<String, Object> bizData = new HashMap<>();

        try {
            if(!StringUtils.hasLength(String.valueOf(param.getUserId()))) {
                throw new RuntimeException("userId 파라미터가 누락되었습니다.");
            }
            if(!StringUtils.hasLength(param.getUserName())) {
                throw new RuntimeException("userNm 파라미터가 누락되었습니다.");
            }
            if(!StringUtils.hasLength(param.getUserLoginId())) {
                throw new RuntimeException("사용자로그인 ID 파라미터가 누락되었습니다.");
            }

            if(param.getUserId() != null && param.getUserId() > 0) {
                // 수정
                userMapper.update(param);

            } else {
                // 등록
                long nextUserId = userMapper.nextUserId();
                param.setUserId(nextUserId);
                userMapper.insert(param);
            }

            bizData.put("userId", param.getUserId());
        } catch(Exception e) {
            apiPrnDto = DrugsafeUtil.getApiPrnDto("-1", e.toString());
        }

        apiPrnDto.setData(bizData);
        return apiPrnDto;
    }

    /**
     * 사용자정보삭제
     * @param param
     * @return
     */
    @Transactional
    public ApiPrnDto delete(UserDeleteReqVO deleteVO) {

        ApiPrnDto apiPrnDto             = new ApiPrnDto();
        HashMap<String, Object> resData = new HashMap<>();

        try {
            if(deleteVO.getUserId() == null) {
                throw new RuntimeException("userId 파라미터가 누락되었습니다.");
            }
            /**
             * BizProc
             */
            int rtn = userMapper.delete(deleteVO);
            if(rtn > 0) {
                apiPrnDto.setMsg("사용자정보가 삭제되었습니다.");
                resData.put("userId", deleteVO.getUserId());
            } else {
                apiPrnDto = DrugsafeUtil.getApiPrnDto("1", "삭제할사용자가 존재하지 않습니다.");
            }
        } catch(Exception e) {
            apiPrnDto = DrugsafeUtil.getApiPrnDto("-1", e.toString());
        }

        apiPrnDto.setData(resData);
        return apiPrnDto;
    }


}
