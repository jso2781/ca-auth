package kr.or.kids.domain.ca.crypto.service.impl;

import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import kr.or.kids.domain.ca.crypto.service.CryptoService;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.softforum.xdbe.xCrypto;

import java.util.HashMap;

@Slf4j
@Service
public class CryptoServiceImpl implements CryptoService {

    /**
     * 암호화
     * @return
     */
    public ApiPrnDto encrypto(MbrTokenPVO reqVO) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> resData = new HashMap<>();

        try {

            String mbrFlnm = reqVO.getMbrFlnm();      // 회원명
            String MbrEmlNm = reqVO.getMbrEmlNm();    // 회원 이메일
            String mbrPswd = reqVO.getMbrPswd();      // 비밀번호
            String MbrTelno = reqVO.getMbrTelno();    // 전화번호

            // 암호화 초기화 및 실행
            xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
            xCrypto.RegisterEx("hash", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "hash");

            /**
             * BizProc
             */
                if (mbrFlnm != null && !mbrFlnm.isEmpty()) {
                    String encryptMbrFlnm = xCrypto.Encrypt("normal", mbrFlnm);
                    resData.put("encryptMbrFlnm", encryptMbrFlnm);
                    result.setMsg("사용자 정보 암호화 완료");

                }else if (MbrEmlNm != null && !MbrEmlNm.isEmpty()) {
                    String encryptMbrEmlNm = xCrypto.Encrypt("normal", MbrEmlNm);
                    resData.put("encryptMbrEmlNm", encryptMbrEmlNm);
                    result.setMsg("사용자 정보 암호화 완료");

                }else if (MbrTelno != null && !MbrTelno.isEmpty()) {
                    String encryptMbrTelno = xCrypto.Encrypt("normal", MbrTelno);
                    resData.put("encryptMbrTelno", encryptMbrTelno);
                    result.setMsg("사용자 정보 암호화 완료");

                }else if (mbrPswd != null && !mbrPswd.isEmpty()) {
                    String encrptMbrPswd = xCrypto.Encrypt("hash", mbrPswd);
                    resData.put("encrptMbrPswd", encrptMbrPswd);
                    result.setMsg("사용자 정보 암호화 완료");
                } else {
                    result.setMsg("암호화할 데이터(ID)가 입력되지 않았습니다.");
                }

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
        // 암호화 초기화 및 실행
        xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");

        try {
            /**
             * BizProc
             */
            String encMbrFlnm = reqVO.getEncryptMbrFlnm();      // 회원명
            String encMbrEmlNm = reqVO.getEncryptMbrEmlNm();    // 회원 이메일
            String encMbrPswd = reqVO.getEncryptMbrPswd();      // 비밀번호
            String encMbrTelno = reqVO.getEncryptMbrTelno();    // 전화번호

            if (encMbrFlnm != null && !encMbrFlnm.isEmpty()) {
                // ID 복호화 로직
                String decryptMbrFlnm = xCrypto.Decrypt("normal", encMbrFlnm);
                resData.put("decryptMbrFlnm", decryptMbrFlnm);
                result.setMsg("사용자 정보 복호화 완료");
            }else if (encMbrEmlNm != null && !encMbrEmlNm.isEmpty()) {
                // ID 복호화 로직
                String decryptMbrEmlNm = xCrypto.Decrypt("normal", encMbrEmlNm);
                resData.put("decryptMbrEmlNm", decryptMbrEmlNm);
                result.setMsg("사용자 정보 복호화 완료");
            }else if (encMbrPswd != null && !encMbrPswd.isEmpty()) {
                // ID 복호화 로직
                String decryptMbrTelno = xCrypto.Decrypt("normal", encMbrPswd);
                resData.put("decryptMbrTelno", decryptMbrTelno);
                result.setMsg("사용자 정보 복호화 완료");
            }else if (encMbrTelno != null && !encMbrTelno.isEmpty()) {
                // 비밀번호 체크 로직
                result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR); // 상황에 맞는 코드 사용
                result.setMsg("비밀번호는 복호화가 불가합니다.");
            }
            else {
                result.setMsg("복호화할 데이터(ID)가 입력되지 않았습니다.");
            }

        } catch(Exception e) {
            log.error("복호화 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("개인정보 복호화 중 오류가 발생했습니다: " + e.getMessage());
        }

        result.setData(resData);
        return result;
    }




}
