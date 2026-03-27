package kr.or.kids.domain.ca.mbr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.kids.domain.ca.mbr.mapper.MbrInfoMapper;
import kr.or.kids.domain.ca.mbr.service.MbrInfoService;
import kr.or.kids.domain.ca.mbr.vo.MbrInfoDVO;
import kr.or.kids.domain.ca.mbr.vo.MbrInfoPVO;
import kr.or.kids.domain.ca.mbr.vo.MbrInfoRVO;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import org.springframework.web.reactive.function.client.WebClient;

import static kr.or.kids.global.util.DrugsafeUtil.markingFunc;

@Slf4j
@Service
public class MbrInfoServiceImpl implements MbrInfoService
{

    private final WebClient webClient;

    @Autowired
    private MbrInfoMapper mbrInfoMapper;

    private final String AUTH_SVC_URL = "http://192.168.2.147:30013/api/ca/crypto";

    public MbrInfoServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public ApiPrnDto list(int pageNum,int pageSize) {
        ApiPrnDto apiPrnDto = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> data = new HashMap<>();

        try {
            log.info("=== 페이징 요청: pageNum={}, pageSize={} ===", pageNum, pageSize);

            // ⭐ PageHelper는 반드시 쿼리 실행 전에 호출해야 함
            PageHelper.startPage(pageNum, pageSize);

            // UserMapper의 list() 메서드 호출 (PageHelper가 자동으로 페이징 쿼리 적용)
            List<MbrInfoRVO> mbrList = mbrInfoMapper.getMbrInfo();

            log.info("=== DB 조회 결과: userList.size()={} ===", mbrList.size());

            // PageInfo로 감싸서 페이징 정보 추출
            PageInfo<MbrInfoRVO> pageInfo = new PageInfo<>(mbrList);

            // 마스킹처리
            List<MbrInfoRVO> list = pageInfo.getList();

            for (MbrInfoRVO member : list) {
                if (member.getEncptMbrFlnm()!= null) {
                    String maskedName = markingFunc("name", member.getEncptMbrFlnm());
                    member.setEncptMbrFlnm(maskedName);
                }
                if (member.getEncptMbrTelno() != null) {
                    String maskedTel = markingFunc("tel", member.getEncptMbrTelno());
                    member.setEncptMbrTelno(maskedTel);
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
            if (mbrList.size() == 0) {
                apiPrnDto.setMsg("조회된 데이터가 없습니다.");
            } else {
                apiPrnDto.setMsg("회원 목록 조회 완료");
            }

            log.info("회원 목록 조회 완료: {} 건", mbrList.size());

        } catch (Exception e) {
            log.error("회원 목록 조회 실패", e);
            apiPrnDto = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            apiPrnDto.setMsg("회원 목록 조회 중 오류가 발생했습니다.");
        }

        apiPrnDto.setData(data);
        return apiPrnDto;
    }


    public ApiPrnDto checkMbrInfo(MbrInfoPVO mbrInfoPVO)
    {
        ApiPrnDto apiPrnDto = new ApiPrnDto(ApiResultCode.SUCCESS);

        int checkCnt = mbrInfoMapper.checkMbrInfo(mbrInfoPVO);
        HashMap<String, Object> bizData = new HashMap<>();
        bizData.put("checkCnt", checkCnt);

        apiPrnDto.setData(bizData);

        return apiPrnDto;
    }

    @Override
    public MbrInfoRVO getMbrInfo(MbrInfoPVO mbrInfoPVO)
    {
        return mbrInfoMapper.getMbrInfo(mbrInfoPVO);
    }

    @Override
    public int insertMbrInfo(MbrInfoPVO mbrInfoPVO)
    {
        return mbrInfoMapper.insertMbrInfo(mbrInfoPVO);
    }

    @Override
    public int updateMbrInfo(MbrInfoPVO mbrInfoPVO)
    {
        return mbrInfoMapper.updateMbrInfo(mbrInfoPVO);
    }

    @Override
    public int saveMbrInfo(MbrInfoPVO mbrInfoPVO)
    {
        return mbrInfoMapper.saveMbrInfo(mbrInfoPVO);
    }

    @Override
    public int deleteMbrInfo(MbrInfoDVO mbrInfoDVO)
    {
        return mbrInfoMapper.deleteMbrInfo(mbrInfoDVO);
    }


    /**
     * 암호화
     * @return
     */
    public ApiPrnDto encrypto(MbrTokenPVO reqVO) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> reqData = new HashMap<>();
        HashMap<String, Object> resData = new HashMap<>();

        try {

            //String mbrFlnm = reqVO.getMbrFlnm();      // 회원명
            //String MbrEmlNm = reqVO.getMbrEmlNm();    // 회원 이메일
            //String mbrPswd = reqVO.getMbrPswd();      // 비밀번호
            //String MbrTelno = reqVO.getMbrTelno();    // 전화번호

               String mbrFlnm = reqVO.getMbrFlnm();
               reqData.put("mbrFlnm",mbrFlnm);

            Map res = webClient.post()
                    .uri(AUTH_SVC_URL + "/encrypto")
                    .bodyValue(reqData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block(); // 대량 처리가 없다면 동기 방식으로 처리 가능

            log.info("res:::::" + res);

            resData.put("encryptMbrFlnm", res);
            result.setMsg("사용자 정보 암호화 완료");


        } catch(Exception e) {
            log.error("사용자 정보 암호화 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("사용자 정보 암호화 중 오류가 발생했습니다: " + e.getMessage());
        }

        result.setData(resData);
        return result;
    }


    /**
     * 비번암호화
     * @return
     */
    public ApiPrnDto decrypto(MbrTokenPVO reqVO) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> resData = new HashMap<>();
        HashMap<String, Object> reqData = new HashMap<>();
        // 암호화 초기화 및 실행
        // xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");

        try {
            /**
             * BizProc
             */


            result.setMsg("사용자 정보 복호화 완료");

            String encryptMbrFlnm = reqVO.getEncryptMbrFlnm();
            reqData.put("encryptMbrFlnm",encryptMbrFlnm);

            Map res = webClient.post()
                    .uri(AUTH_SVC_URL + "/decrypto")
                    .bodyValue(reqData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block(); // 대량 처리가 없다면 동기 방식으로 처리 가능

            log.info("res:::::" + res);

            resData.put("data", res);
            result.setMsg("사용자 정보 암호화 완료");

        } catch(Exception e) {
            log.error("복호화 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("개인정보 복호화 중 오류가 발생했습니다: " + e.getMessage());
        }

        result.setData(resData);
        return result;
    }






}
