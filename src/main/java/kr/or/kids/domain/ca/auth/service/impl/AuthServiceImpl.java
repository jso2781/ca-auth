package kr.or.kids.domain.ca.auth.service.impl;

import java.math.BigInteger;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.or.kids.domain.ca.auth.mapper.MbrTokenMapper;
import kr.or.kids.domain.ca.auth.service.ActiveTokenService;
import kr.or.kids.domain.ca.auth.service.AuthService;
import kr.or.kids.domain.ca.auth.service.IdleTokenService;
import kr.or.kids.domain.ca.auth.service.TokenBlacklistService;
import kr.or.kids.domain.ca.auth.vo.MbrTokenDVO;
import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import kr.or.kids.domain.ca.auth.vo.MbrTokenRVO;
import kr.or.kids.domain.ca.connecionlog.service.ConnectionLogService;
import kr.or.kids.domain.ca.connecionlog.vo.ConnectionLogInsertReqVO;
import kr.or.kids.domain.ca.mbr.mapper.MbrInfoMapper;
import kr.or.kids.domain.ca.mbr.vo.MbrInfoPVO;
import kr.or.kids.domain.ca.mbr.vo.MbrInfoRVO;
import kr.or.kids.global.config.util.MessageContextHolder;
import kr.or.kids.global.exception.ApplicationException;
import kr.or.kids.global.security.JwtTokenProvider;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import kr.or.kids.global.util.DrugsafeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService
{
    @Value("${jwt.refresh-token-expiration}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Value("${jwt.access-token-expiration}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.issuer}")
    private String ISSUER;

    private final MbrInfoMapper mbrInfoMapper;

//    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final MbrTokenMapper mbrTokenMapper;

    private final TokenBlacklistService tokenBlacklistService;

    private final ActiveTokenService activeTokenService;

    private final IdleTokenService idleTokenService;

    private final ConnectionLogService connectionLogService;

    @Override
    public MbrTokenRVO getMbrToken(MbrTokenPVO mbrTokenPVO)
    {
        return mbrTokenMapper.getMbrToken(mbrTokenPVO);
    }

    @Override
    public int insertMbrToken(MbrTokenPVO mbrTokenPVO)
    {
        return mbrTokenMapper.insertMbrToken(mbrTokenPVO);
    }

    @Override
    public int updateMbrToken(MbrTokenPVO mbrTokenPVO)
    {
        return mbrTokenMapper.updateMbrToken(mbrTokenPVO);
    }

    @Override
    public int saveMbrToken(MbrTokenPVO mbrTokenPVO)
    {
        return mbrTokenMapper.saveMbrToken(mbrTokenPVO);
    }

    @Override
    public int deleteMbrToken(MbrTokenDVO mbrTokenDVO)
    {
        return mbrTokenMapper.deleteMbrToken(mbrTokenDVO);
    }

    /**
     * 자체 로그인을 통한 로그인인 경우(lgnSeCd=1)
     */
    @Override
    public ApiPrnDto login(MbrTokenPVO loginVO, HttpServletRequest request){
        ApiPrnDto apiPrnDto = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> bizData = new HashMap<>();

        try{
            if(!StringUtils.hasLength(loginVO.getMbrId())){
                // 회원아이디 필수 파라메터가 누락되었습니다.
                throw new ApplicationException("error.param.required", new String[] {MessageContextHolder.getMessage("label.user.mbrId")});
            }

            if(!StringUtils.hasLength(loginVO.getEncptMbrPswd())){
                // 회원암호화비밀번호 필수 파라메터가 누락되었습니다.
                throw new ApplicationException("error.param.required", new String[] {MessageContextHolder.getMessage("label.user.mbrEnpswd")});
            }

            MbrInfoPVO mp = new MbrInfoPVO();
            mp.setMbrId(loginVO.getMbrId());

            // 회원정보기본 테이블에 회원정보가 존재하는 조회
            MbrInfoRVO userInfo = mbrInfoMapper.getMbrInfo(mp);

            if(userInfo == null) {
                // 로그인 정보가 일치하지 않습니다.
                apiPrnDto = DrugsafeUtil.getApiPrnDto("0", MessageContextHolder.getMessage("ui.msg.login.nofound"));

                log.debug("AuthServiceImpl login userInfo == null");
            }
            // 로그인 정보와 일치한 경우
            else{
                String mbrId = userInfo.getMbrId();

                // 로그인 구분코드(1 - 자체로그인, 2 - Any-ID 로그인)
                String lgnSeCd = "1";

//                if(!passwordEncoder.matches(loginVO.getMbrEnpswd(), userInfo.getMbrEnpswd())) {
                if(loginVO.getEncptMbrPswd() != null && !loginVO.getEncptMbrPswd().equals(userInfo.getEncptMbrPswd())){
                    apiPrnDto = DrugsafeUtil.getApiPrnDto("0", MessageContextHolder.getMessage("ui.msg.login.nofound"));

                    int pswdErrNmtm = userInfo.getPswdErrNmtm();
                    pswdErrNmtm = pswdErrNmtm + 1;

                    // 로그인 성공하변 회원정보기본에서 로그인 실패 횟수 + 1 증가
                    mp.setPswdErrNmtm(pswdErrNmtm);
                    mp.setMdfrId(mbrId);

                    mbrInfoMapper.updateMbrInfo(mp);

                    log.debug("AuthServiceImpl login userInfo != null, password is not same!! userInfo.getPswdErrNmtm()="+userInfo.getPswdErrNmtm());
                    HashMap<String, Object> failMap = new HashMap<String, Object>();

                    // 패스워드 불일치로 로그인 실패 횟수 정보 반환
                    failMap.put("pswdErrNmtm", pswdErrNmtm);

                    apiPrnDto.setData(failMap);
                }else{
                    BigInteger tokenSn = null;

                    // 기존 토큰 정보가 존재하면 없데이트, 없으면 토큰 생성후 토큰정보 입력,
                    MbrTokenRVO mtr = mbrTokenMapper.getMbrToken(loginVO);

                    if(mtr == null){
                        long newTokenSn = mbrTokenMapper.nextMbrTokenSn();
                        tokenSn = BigInteger.valueOf(newTokenSn);
                    }else{
                        tokenSn = mtr.getTokenSn();
                    }

                    // Refresh Token 생성(Access Token, Refresh Token)
                    String updtTokenCn = jwtTokenProvider.createUpdtTokenCn(ISSUER, mbrId, REFRESH_TOKEN_EXPIRE_TIME);

                    // ✅ acsTokenCn은 tokenSn/prgrmId claim 포함해서 생성
                    String acsTokenCn  = jwtTokenProvider.createAcsTokenCn(ISSUER, mbrId, ACCESS_TOKEN_EXPIRE_TIME, tokenSn.toString(), ISSUER);

                    if(mtr == null){

                        MbrTokenPVO tokenInsertVO = new MbrTokenPVO();
                        tokenInsertVO.setTokenSn(tokenSn);
                        tokenInsertVO.setPrgrmId(ISSUER);
                        tokenInsertVO.setMbrId(mbrId);
                        tokenInsertVO.setUpdtTokenCn(updtTokenCn);
                        tokenInsertVO.setAcsTokenCn(acsTokenCn);
                        tokenInsertVO.setRgtrId(mbrId);
                        tokenInsertVO.setMdfrId(mbrId);
                        mbrTokenMapper.insertMbrToken(tokenInsertVO);
                        userInfo.setLgnSeCd(lgnSeCd);
                        userInfo.setTokenSn(tokenSn);
                        userInfo.setAcsTokenCn(acsTokenCn);
                        userInfo.setUpdtTokenCn(updtTokenCn);
                        userInfo.setPrgrmId(ISSUER);
                        userInfo.setPswdErrNmtm(0);             // 로그인 성공했으므로 기존 로그인 실패 횟수를 0으로 초기화

                        log.info("############# userInfo::::::::::::" + userInfo);

                        // UI 에 전달한 사용자 정보(userInfo), 토큰 정보(lgnSeCd, tokenSn, acsTokenCn, updtTokenCn, pswdErrNmtm)
                        bizData.put("lgnSeCd", lgnSeCd);
                        bizData.put("tokenSn", tokenSn);
                        bizData.put("acsTokenCn", acsTokenCn);
                        bizData.put("updtTokenCn", updtTokenCn);
                        bizData.put("pswdErrNmtm", 0);
                        bizData.put("userInfo", userInfo);
                    }
                    // 기존 토큰 정보가 존재하면, 업데이트
                    else{
                        MbrTokenPVO tokenUpdateVO = new MbrTokenPVO();
                        tokenUpdateVO.setTokenSn(tokenSn);
                        tokenUpdateVO.setPrgrmId(ISSUER);
                        tokenUpdateVO.setMbrId(mbrId);
                        tokenUpdateVO.setUpdtTokenCn(updtTokenCn);
                        tokenUpdateVO.setAcsTokenCn(acsTokenCn);
                        tokenUpdateVO.setMdfrId(mbrId);

                        mbrTokenMapper.updateMbrToken(tokenUpdateVO);

                        userInfo.setLgnSeCd(lgnSeCd);
                        userInfo.setTokenSn(tokenSn);
                        userInfo.setAcsTokenCn(acsTokenCn);
                        userInfo.setUpdtTokenCn(updtTokenCn);
                        userInfo.setPswdErrNmtm(0);             // 로그인 성공했으므로 기존 로그인 실패 횟수를 0으로 초기화
                        userInfo.setPrgrmId(ISSUER);

                        // UI 에 전달한 사용자 정보(userInfo), 토큰 정보(lgnSeCd, tokenSn, acsTokenCn, updtTokenCn, pswdErrNmtm)
                        bizData.put("lgnSeCd", lgnSeCd);
                        bizData.put("tokenSn", tokenSn);
                        bizData.put("acsTokenCn", acsTokenCn);
                        bizData.put("updtTokenCn", updtTokenCn);
                        bizData.put("pswdErrNmtm", 0);
                        bizData.put("userInfo", userInfo);
                    }

                    // 로그인 성공하변 회원정보기본에서 인증토큰(acsTokenCn), 로그인 실패 횟수=0 지정
//                    mp.setCertToken(acsTokenCn); // acsTokenCn 입력시 character varying(40) 자료형에 너무 긴 자료를 담으려고 합니다.
                    mp.setPswdErrNmtm(0);
                    mp.setMdfrId(mbrId);

                    mbrInfoMapper.updateMbrInfo(mp);

                    // Redis Idle 키 생성(30분)
                    idleTokenService.touch(tokenSn.toString());

                    // Redis Active 키 생성(ACCESS_TOKEN_EXPIRE_TIME 만료시간 설정)
                    activeTokenService.markActive(mbrId, tokenSn.toString(), ACCESS_TOKEN_EXPIRE_TIME);

                    // 로그인되었습니다.
                    apiPrnDto.setMsg(MessageContextHolder.getMessage("ui.msg.login.success"));

                    apiPrnDto.setData(bizData);

                    /**************************************** 공통_세션정보시스템로그 Rest API 호출(tb_ca_l_sesn_log_info_mng 로그인 성공 기록) 시작 ************************************************/
                    DrugsafeUtil  util = new DrugsafeUtil();
                    String clientIp = util.getClientIp(request);

                    ConnectionLogInsertReqVO req = new ConnectionLogInsertReqVO();

                    // 로그인구분코드(1 : ID 로그인 , 2 : 애니아이디 로그인)
                    req.setLgnSeCd("1");

                    // 네트워크 구분코드(1 : 내부망, 2 : 외부망)
                    req.setNetSeCd(clientIp != null && clientIp.indexOf("192.168") > -1 ? "1" : "2");

                    // 서비스사용자 아이디
                    req.setSrvcUserId(mbrId);

                    // 요청자IP주소
                    req.setRqstrIpAddr(clientIp);

                    // 접속구분번호(1 : 로그인, 2:로그아웃) 
                    req.setCntnSeNo("1");

                    // 접속 상세 설명
                    req.setCntnDtlExpln("LoginIn");

                    // 인증토큰값(CI 값을 넣을지 확정 안됨.)
                    req.setCertTokenVl("");

                    // 서비스명
                    req.setSrvcNm("kids_pp");

                    // 업무구분코드
                    req.setTaskSeCd("PP");

                    // 등록자 아이디
                    req.setRgtrId(mbrId);

                    // 수정자 아이디
                    req.setMdfrId(mbrId);

                    connectionLogService.insert(req);
                    /**************************************** 공통_세션정보시스템로그 Rest API 호출(tb_ca_l_sesn_log_info_mng 로그인 성공 기록) 끝 ************************************************/

                    return apiPrnDto;
                }
            }
        }catch(Exception e){
            apiPrnDto = DrugsafeUtil.getApiPrnDto("-1", e.toString());
        }

        return apiPrnDto;
    }

    public ApiPrnDto refresh(BigInteger tokenSn, String updtTokenCn) {
        ApiPrnDto apiPrnDto = new ApiPrnDto(ApiResultCode.SUCCESS);

        // JWT 토큰 문자열에서 회원ID(mbrId) 가져옴.
        String mbrId = jwtTokenProvider.getSubject(updtTokenCn);

        MbrTokenPVO mbrTokenPVO = new MbrTokenPVO();
        mbrTokenPVO.setTokenSn(tokenSn);

        MbrTokenRVO mbrToken = mbrTokenMapper.getMbrToken(mbrTokenPVO);
        if(mbrToken == null)throw new RuntimeException("TOKEN_NOT_FOUND");
        if(!updtTokenCn.equals(mbrToken.getUpdtTokenCn()))throw new RuntimeException("TOKEN_MISMATCH");

        String newUpdtTokenCn = jwtTokenProvider.createUpdtTokenCn(ISSUER, mbrId, REFRESH_TOKEN_EXPIRE_TIME);
        String newAcsTokenCn = jwtTokenProvider.createAcsTokenCn(ISSUER, mbrId, ACCESS_TOKEN_EXPIRE_TIME, tokenSn.toString(), ISSUER);

        log.info("newAcsTokenCn:::::::::::" + newAcsTokenCn);

        MbrTokenPVO tokenInsertVO = new MbrTokenPVO();
        tokenInsertVO.setTokenSn(tokenSn);
        tokenInsertVO.setPrgrmId(ISSUER);
        tokenInsertVO.setMbrId(mbrId);
        tokenInsertVO.setUpdtTokenCn(newUpdtTokenCn);
        tokenInsertVO.setAcsTokenCn(newAcsTokenCn);
        tokenInsertVO.setMdfrId(mbrId);

        // DB에 기존 JWT 토큰 정보 업데이트
        mbrTokenMapper.updateMbrToken(tokenInsertVO);

        // Refresh도 사용자 활동이므로 Redis Idle Key 30분 리셋
        idleTokenService.touch(tokenSn.toString());

        // Redis Active Key도 갱신
        activeTokenService.markActive(mbrId, tokenSn.toString(), ACCESS_TOKEN_EXPIRE_TIME);

        MbrInfoPVO mp = new MbrInfoPVO();
        mp.setMbrId(mbrId);

        // 토큰과 연계된 사용자 정보 조회
        MbrInfoRVO userInfo = mbrInfoMapper.getMbrInfo(mp);
        String lgnSeCd = userInfo.getLgnSeCd();

        userInfo.setLgnSeCd(lgnSeCd);
        userInfo.setTokenSn(tokenSn);

        HashMap<String, Object> bizData = new HashMap<>();
        bizData.put("lgnSeCd", lgnSeCd);
        bizData.put("tokenSn", tokenSn);
        bizData.put("acsTokenCn", newAcsTokenCn);
        bizData.put("updtTokenCn", newUpdtTokenCn);
        bizData.put("pswdErrNmtm", 0);
        bizData.put("userInfo", userInfo);

        apiPrnDto.setData(bizData);

        return apiPrnDto;
    }

    /**
     * 로그아웃 처리
     */
    public ApiPrnDto logout(MbrTokenDVO mbrTokenDVO, String authorizationHeader, HttpServletRequest request){
        // 1) DB에서 refresh/access 정보 삭제(token_sn + mbr_id 조건)
        mbrTokenMapper.deleteMbrToken(mbrTokenDVO);

        // Request Header의 Authorization 항목의 값에서 token 부분만 추출
        String token = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
        }

        // 2) idle 키, active 키 삭제
        BigInteger tokenSn = mbrTokenDVO.getTokenSn();
        String mbrId = mbrTokenDVO.getMbrId();

        if(tokenSn != null && StringUtils.hasText(mbrId)){
            idleTokenService.delete(tokenSn.toString());
            activeTokenService.revoke(mbrId, tokenSn.toString());
        }

        // 3) access token 블랙리스트 등록 (로그아웃 즉시 무효화)
        if(token != null){
            long ttl = jwtTokenProvider.getRemainingMillis(token);
            tokenBlacklistService.blacklist(token, ttl);
        }

        /**************************************** 4.공통_세션정보시스템로그 Rest API 호출(tb_ca_l_sesn_log_info_mng 로그아웃 기록) 시작 ************************************************/
        DrugsafeUtil  util = new DrugsafeUtil();
        String clientIp = util.getClientIp(request);

        ConnectionLogInsertReqVO req = new ConnectionLogInsertReqVO();

        // 로그인구분코드(1 : ID 로그인 , 2 : 애니아이디 로그인)
        req.setLgnSeCd("1");

        // 네트워크 구분코드(1 : 내부망, 2 : 외부망)
        req.setNetSeCd(clientIp != null && clientIp.indexOf("192.168") > -1 ? "1" : "2");

        // 서비스사용자 아이디
        req.setSrvcUserId(mbrId);

        // 요청자IP주소
        req.setRqstrIpAddr(clientIp);

        // 접속구분번호(1 : 로그인, 2:로그아웃) 
        req.setCntnSeNo("2");

        // 접속 상세 설명
        req.setCntnDtlExpln("LogOut");

        // 인증토큰값(CI 값을 넣을지 확정 안됨.)
        req.setCertTokenVl("");

        // 서비스명
        req.setSrvcNm("kids_pp");

        // 업무구분코드
        req.setTaskSeCd("PP");

        // 등록자 아이디
        req.setRgtrId(mbrId);

        // 수정자 아이디
        req.setMdfrId(mbrId);

        connectionLogService.insert(req);
        /**************************************** 4.공통_세션정보시스템로그 Rest API 호출(tb_ca_l_sesn_log_info_mng 로그아웃 기록) 끝 ************************************************/

        return new ApiPrnDto(ApiResultCode.SUCCESS);
    }

    /**
     * Redis Idle 키 리셋
     */
    public ApiPrnDto extend(String authorizationHeader) {
        String token = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
        }

        if(token == null){
            return new ApiPrnDto(ApiResultCode.UNAUTHORIZED);
        }

        // AcsTokenCn에서 tokenSn claim 추출
        String tokenSn = jwtTokenProvider.getTokenSn(token);
        if (tokenSn == null || tokenSn.isBlank()) {
            return new ApiPrnDto(ApiResultCode.UNAUTHORIZED);
        }

        // Redis Idle 키가 이미 없으면(30분 idle 만료) 연장 불가 → 401 처리 권장
        if (!idleTokenService.exists(tokenSn)) {
            return new ApiPrnDto(ApiResultCode.UNAUTHORIZED);
        }

        // Redis Idle TTL 30분 리셋 (토큰 재발급 없음)
        idleTokenService.touch(tokenSn);

        return new ApiPrnDto(ApiResultCode.SUCCESS);
    }

    /**
     * Redis Idle 키 리셋
     */
    public ApiPrnDto isLoggedIn(String authorizationHeader) {
        String token = null;
        ApiPrnDto apiPrnDto = new ApiPrnDto(ApiResultCode.SUCCESS);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
        }

        if(token == null){
            return new ApiPrnDto(ApiResultCode.UNAUTHORIZED);
        }

        // AcsTokenCn에서 tokenSn claim 추출
        String tokenSn = jwtTokenProvider.getTokenSn(token);
        if (tokenSn == null || tokenSn.isBlank()) {
            return new ApiPrnDto(ApiResultCode.UNAUTHORIZED);
        }

        String mbrId = jwtTokenProvider.getSubject(token);
        // 회원정보기본 테이블에 회원정보가 존재하는 조회
        MbrInfoPVO mp = new MbrInfoPVO();
        mp.setMbrId(mbrId);
        MbrInfoRVO userInfo = mbrInfoMapper.getMbrInfo(mp);
        HashMap<String, Object> bizData = new HashMap<>();
        //  Redis Idle 키가 없을 시
        if (!idleTokenService.exists(tokenSn)) {
            bizData.put("loging", "false");
        }else{
            bizData.put("loging", "true");
            bizData.put("userInfo", userInfo);
            bizData.put("mbrId", mbrId);
            bizData.put("token", token);
        }

        apiPrnDto.setData(bizData);

        return apiPrnDto;
    }

}
