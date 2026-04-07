package kr.or.kids.domain.ca.crypto.service.impl;

import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import kr.or.kids.domain.ca.crypto.service.CryptoService;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptListPVO;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptPVO;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.softforum.xdbe.xCrypto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class CryptoServiceImpl implements CryptoService {

    @Value("${crypto.xdsp-pool-config:/app/xecuredb/conf/xdsp_pool.properties}")
    private String xdspPoolConfig;

    @Value("${crypto.mock-base64.enabled:false}")
    private boolean mockBase64Enabled;

    private void registerCryptoProfiles(boolean includeHash) {
        if (mockBase64Enabled) {
            return;
        }
        xCrypto.RegisterEx("normal", 2, xdspPoolConfig, "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
        if (includeHash) {
            xCrypto.RegisterEx("hash", 2, xdspPoolConfig, "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "hash");
        }
    }

    private String encryptValue(String profile, String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        if (mockBase64Enabled) {
            return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
        }
        return xCrypto.Encrypt(profile, value);
    }

    private String decryptValue(String profile, String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        if (mockBase64Enabled) {
            try {
                return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
            } catch (IllegalArgumentException e) {
                log.debug("Mock base64 decode skipped, returning plain text. profile={}, value={}", profile, value);
                return value;
            }
        }
        return xCrypto.Decrypt(profile, value);
    }

    /**
     * 암호화
     * @return
     */
    public ApiPrnDto encrypto(MbrEncryptPVO reqVO) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> resData = new HashMap<>();
        // 암호화 초기화 및 실행
        registerCryptoProfiles(true);

        try {
            String mbrFlnm = reqVO.getMbrFlnm();      // 회원명
            String mbrEmlNm = reqVO.getMbrEmlNm();    // 회원 이메일
            String mbrPswd = reqVO.getMbrPswd();      // 회원 비밀번호
            String mbrTelno = reqVO.getMbrTelno();    // 회원 전화번호
            String empTelno = reqVO.getMbrTelno();    // 직원전화번호
            String empEmlNm = reqVO.getMbrEmlNm();    // 직원이메일명
            String mngrPswd = reqVO.getMngrPswd();    // 관리자 비밀번호
            String cnstnMbcmtRrno = reqVO.getCnstnMbcmtRrno();    // 자문위원주민등록번호
            String cnstnMbcmtActno = reqVO.getCnstnMbcmtActno();  // 자문위원계좌번호
            String bfrPswd = reqVO.getBfrPswd();
            String cmntPswd = reqVO.getCmntPswd();
            String picTelno = reqVO.getPicTelno();
            String sttyAgtTelno = reqVO.getSttyAgtTelno();
            String exprtFlnm = reqVO.getExprtFlnm();
            String exprtInstEmlNm = reqVO.getExprtInstEmlNm();
            String wrtrFlnm = reqVO.getWrtrFlnm();
            String wrtrTelno = reqVO.getWrtrTelno();

            /**
             * BizProc
             */
            if (mbrFlnm != null && !mbrFlnm.isEmpty()) {
                String encryptMbrFlnm = encryptValue("normal", mbrFlnm);
                resData.put("encptMbrFlnm", encryptMbrFlnm);
                result.setMsg("회원명 암호화 완료");

            }else if (mbrEmlNm != null && !mbrEmlNm.isEmpty()) {
                String encryptMbrEmlNm = encryptValue("normal", mbrEmlNm);
                resData.put("encptMbrEmlNm", encryptMbrEmlNm);
                result.setMsg("회원이메일 암호화 완료");

            }else if (mbrTelno != null && !mbrTelno.isEmpty()) {
                String encryptMbrTelno = encryptValue("normal", mbrTelno);
                resData.put("encptMbrTelno", encryptMbrTelno);
                result.setMsg("회원 전화번호 암호화 완료");

            }else if (mbrPswd != null && !mbrPswd.isEmpty()) {
                String encrptMbrPswd = encryptValue("hash", mbrPswd);
                resData.put("encptMbrPswd", encrptMbrPswd);
                result.setMsg("사용자 정보 암호화 완료");

            }else if (empTelno != null && !empTelno.isEmpty()) {
                String encptempTelno = encryptValue("hash", empTelno);
                resData.put("encptempTelno", encptempTelno);
                result.setMsg("직원 전화번호 암호화 완료");

            }else if (empEmlNm != null && !empEmlNm.isEmpty()) {
                String encptEmpEmlNm = encryptValue("hash", empEmlNm);
                resData.put("encptEmpEmlNm", encptEmpEmlNm);
                result.setMsg("직원 이메일명 암호화 완료");

            }else if (cnstnMbcmtRrno != null && !cnstnMbcmtRrno.isEmpty()) {
                String encptCnstnMbcmtRrno = encryptValue("hash", cnstnMbcmtRrno);
                resData.put("encptCnstnMbcmtRrno", encptCnstnMbcmtRrno);
                result.setMsg("자문위원 정보 암호화 완료");

            }else if (cnstnMbcmtActno != null && !cnstnMbcmtActno.isEmpty()) {
                String encptCnstnMbcmtActno = encryptValue("hash", cnstnMbcmtActno);
                resData.put("encptCnstnMbcmtActno", encptCnstnMbcmtActno);
                result.setMsg("자문위원 계좌정보 암호화 완료");

            }else if (mngrPswd != null && !mngrPswd.isEmpty()) {
                String encptMngrPswd = encryptValue("hash", mngrPswd);
                resData.put("encptMngrPswd", encptMngrPswd);
                result.setMsg("관리자 비밀번호 암호화 완료");
                // --- 신규 항목 추가 ---
            }else if (bfrPswd != null && !bfrPswd.isEmpty()) {
                resData.put("encptBfrPswd", encryptValue("hash", bfrPswd));
                result.setMsg("이전비밀번호 암호화 완료");
            } else if (cmntPswd != null && !cmntPswd.isEmpty()) {
                resData.put("encptCmntPswd", encryptValue("hash", cmntPswd));
                result.setMsg("댓글비밀번호 암호화 완료");
            } else if (picTelno != null && !picTelno.isEmpty()) {
                resData.put("encptPicTelno", encryptValue("normal", picTelno));
                result.setMsg("담당자전화번호 암호화 완료");
            } else if (sttyAgtTelno != null && !sttyAgtTelno.isEmpty()) {
                resData.put("encptSttyAgtTelno", encryptValue("normal", sttyAgtTelno));
                result.setMsg("법정대리인전화번호 암호화 완료");
            } else if (exprtFlnm != null && !exprtFlnm.isEmpty()) {
                resData.put("encptExprtFlnm", encryptValue("normal", exprtFlnm));
                result.setMsg("전문가성명 암호화 완료");
            } else if (exprtInstEmlNm != null && !exprtInstEmlNm.isEmpty()) {
                resData.put("encptExprtInstEmlNm", encryptValue("normal", exprtInstEmlNm));
                result.setMsg("전문가기관이메일 암호화 완료");
            } else if (wrtrFlnm != null && !wrtrFlnm.isEmpty()) {
                resData.put("encptWrtrFlnm", encryptValue("normal", wrtrFlnm));
                result.setMsg("작성자성명 암호화 완료");
            } else if (wrtrTelno != null && !wrtrTelno.isEmpty()) {
                resData.put("encptWrtrTelno", encryptValue("normal", wrtrTelno));
                result.setMsg("작성자전화번호 암호화 완료");

            } else {
                result.setMsg("암호화할 데이터(항목)가 입력되지 않았습니다.");
            }

        } catch(Exception e) {
            log.error("사용자 정보 암호화 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("사용자 정보 암호화 중 오류가 발생했습니다: " + e.getMessage());
        }

        result.setData(resData);
        return result;
    }


    public ApiPrnDto encryptoList(MbrEncryptListPVO reqList) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        List<HashMap<String, Object>> resList = new ArrayList<>();

        List<MbrEncryptPVO> items = reqList.getListItems();
        try {
            // 암호화 초기화 — 목록 전체에 한 번만 실행
            registerCryptoProfiles(true);

            if(items.size() > 0) {
                for (MbrEncryptPVO reqVO : items) {
                    HashMap<String, Object> resData = new HashMap<>();

                    String mbrFlnm = reqVO.getMbrFlnm();
                    String mbrEmlNm = reqVO.getMbrEmlNm();
                    String mbrPswd = reqVO.getMbrPswd();
                    String mbrTelno = reqVO.getMbrTelno();
                    String empTelno = reqVO.getEmpTelno();   // VO 확인 필요
                    String empEmlNm = reqVO.getEmpEmlNm();   // VO 확인 필요
                    String mngrPswd = reqVO.getMngrPswd();
                    String cnstnMbcmtRrno = reqVO.getCnstnMbcmtRrno();
                    String cnstnMbcmtActno = reqVO.getCnstnMbcmtActno();

                    // ── normal 암호화 ─────────────────────────────────────────────────
                    if (mbrFlnm != null && !mbrFlnm.isEmpty()) {
                        resData.put("encptMbrFlnm", encryptValue("normal", mbrFlnm));
                    }
                    if (mbrEmlNm != null && !mbrEmlNm.isEmpty()) {
                        resData.put("encptMbrEmlNm", encryptValue("normal", mbrEmlNm));
                    }
                    if (mbrTelno != null && !mbrTelno.isEmpty()) {
                        resData.put("encptMbrTelno", encryptValue("normal", mbrTelno));
                    }
                    if (empTelno != null && !empTelno.isEmpty()) {
                        resData.put("encptEmpTelno", encryptValue("normal", empTelno));
                    }
                    if (empEmlNm != null && !empEmlNm.isEmpty()) {
                        resData.put("encptEmpEmlNm", encryptValue("normal", empEmlNm));
                    }
                    if (cnstnMbcmtRrno != null && !cnstnMbcmtRrno.isEmpty()) {
                        resData.put("encptCnstnMbcmtRrno", encryptValue("normal", cnstnMbcmtRrno));
                    }
                    if (cnstnMbcmtActno != null && !cnstnMbcmtActno.isEmpty()) {
                        resData.put("encptCnstnMbcmtActno", encryptValue("normal", cnstnMbcmtActno));
                    }
                    // ── hash 암호화 ───────────────────────────────────────────────────
                    if (mbrPswd != null && !mbrPswd.isEmpty()) {
                        resData.put("encptMbrPswd", encryptValue("hash", mbrPswd));
                    }
                    if (mngrPswd != null && !mngrPswd.isEmpty()) {
                        resData.put("encptMngrPswd", encryptValue("hash", mngrPswd));
                    }
                    // 추가 신규 필드 암호화
                    if (reqVO.getBfrPswd() != null) resData.put("encptBfrPswd", encryptValue("hash", reqVO.getBfrPswd()));
                    if (reqVO.getCmntPswd() != null) resData.put("encptCmntPswd", encryptValue("hash", reqVO.getCmntPswd()));
                    if (reqVO.getPicTelno() != null) resData.put("encptPicTelno", encryptValue("normal", reqVO.getPicTelno()));
                    if (reqVO.getSttyAgtTelno() != null) resData.put("encptSttyAgtTelno", encryptValue("normal", reqVO.getSttyAgtTelno()));
                    if (reqVO.getExprtFlnm() != null) resData.put("encptExprtFlnm", encryptValue("normal", reqVO.getExprtFlnm()));
                    if (reqVO.getExprtInstEmlNm() != null) resData.put("encptExprtInstEmlNm", encryptValue("normal", reqVO.getExprtInstEmlNm()));
                    if (reqVO.getWrtrFlnm() != null) resData.put("encptWrtrFlnm", encryptValue("normal", reqVO.getWrtrFlnm()));
                    if (reqVO.getWrtrTelno() != null) resData.put("encptWrtrTelno", encryptValue("normal", reqVO.getWrtrTelno()));
                    resList.add(resData);
                }

            }else{
                result.setMsg("암호화할 데이터(항목)가 입력되지 않았습니다.");
            }
            result.setMsg(resList.size() + "건 암호화 완료");

        } catch (Exception e) {
            log.error("사용자 정보 암호화 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("사용자 정보 암호화 중 오류가 발생했습니다: " + e.getMessage());
        }

        HashMap<String, Object> wrapper = new HashMap<>();
        wrapper.put("list",resList);
        result.setData(wrapper);
        return result;
    }

    /**
     *  단건복호화
     * @return
     */
    public ApiPrnDto decrypto(MbrEncryptPVO reqVO) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> resData = new HashMap<>();
        // 암호화 초기화 및 실행
        registerCryptoProfiles(false);
        try {
            /**
             * BizProc
             */

            log.info("reqVO.getEncptMbrFlnm()::::::::"+ reqVO.getEncptMbrFlnm());

            String encMbrFlnm = reqVO.getEncptMbrFlnm();                    // 회원명
            String encMbrEmlNm = reqVO.getEncptMbrEmlNm();                  // 회원 이메일
            String encMbrPswd = reqVO.getEncptMbrPswd();                    // 비밀번호
            String encMbrTelno = reqVO.getEncptMbrTelno();                  // 전화번호
            String encEmpTelno = reqVO.getMbrTelno();                       // 직원전화번호
            String encEmpEmlNm = reqVO.getMbrEmlNm();                       // 직원이메일명
            String encMngrPswd = reqVO.getMngrPswd();                       // 관리자비밀번호
            String encptCnstnMbcmtRrno = reqVO.getEncptCnstnMbcmtRrno();    // 자문위원주민등록번호
            String encptCnstnMbcmtActno = reqVO.getEncptCnstnMbcmtActno();  // 자문위원계좌번호
            // 포털
            String encptBfrPswd = reqVO.getEncptBfrPswd();
            String encptCmntPswd = reqVO.getEncptCmntPswd();
            String encptPicTelno = reqVO.getEncptPicTelno();
            String encptSttyAgtTelno = reqVO.getEncptSttyAgtTelno();
            String encptExprtFlnm = reqVO.getEncptExprtFlnm();
            String encptExprtInstEmlNm = reqVO.getEncptExprtInstEmlNm();
            String encptWrtrFlnm = reqVO.getEncptWrtrFlnm();
            String encptWrtrTelno = reqVO.getEncptWrtrTelno();

            if (encMbrPswd != null && !encMbrPswd.isEmpty()) {
                result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR); // 상황에 맞는 코드 사용
                result.setMsg("비밀번호는 복호화가 불가합니다.");
            }else if (encMngrPswd != null && !encMngrPswd.isEmpty()) {
                result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR); // 상황에 맞는 코드 사용
                result.setMsg("비밀번호는 복호화가 불가합니다.");
            }else if (encMbrPswd != null && !encMbrPswd.isEmpty()) {
                result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR);
                result.setMsg("비밀번호는 복호화가 불가합니다.");
            } else if (encMngrPswd != null && !encMngrPswd.isEmpty()) {
                result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR);
                result.setMsg("관리자 비밀번호는 복호화가 불가합니다.");
            } else if (encptBfrPswd != null && !encptBfrPswd.isEmpty()) {
                result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR);
                result.setMsg("이전비밀번호는 복호화가 불가합니다.");
            } else if (encptCmntPswd != null && !encptCmntPswd.isEmpty()) {
                result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR);
                result.setMsg("댓글비밀번호는 복호화가 불가합니다.");
            }else if (encMbrFlnm != null && !encMbrFlnm.isEmpty()) {

                log.info("encMbrFlnm::::::::"+ encMbrFlnm);

                String decryptMbrFlnm = decryptKo(encMbrFlnm);

                resData.put("decptMbrFlnm", decryptMbrFlnm);

                String decryptMbrEmlNm_test = decryptValue("normal", reqVO.getEncptMbrFlnm());

                log.info("decryptMbrEmlNm_test::::::::"+ decryptMbrEmlNm_test);

                result.setMsg("회원명 복호화 완료");
            }else if (encMbrEmlNm != null && !encMbrEmlNm.isEmpty()) {
                String decryptMbrEmlNm = decryptValue("normal", encMbrEmlNm);
                resData.put("decptMbrEmlNm", decryptMbrEmlNm);
                result.setMsg("회원 이메일 복호화 완료");
            }else if (encMbrTelno != null && !encMbrTelno.isEmpty()) {
                String decryptMbrTelno = decryptValue("normal", encMbrTelno);
                resData.put("decptMbrTelno", decryptMbrTelno);
                result.setMsg("회원 전화번호 복호화 완료");
            }else if (encEmpTelno != null && !encEmpTelno.isEmpty()) {
                String decptEncEmpTelno = decryptValue("normal", encEmpTelno);
                resData.put("decptEncEmpTelno", decptEncEmpTelno);
                result.setMsg("직원 전화번호 복호화 완료");
            }else if (encEmpEmlNm != null && !encEmpEmlNm.isEmpty()) {
                String decptEmpEmlNm = decryptValue("normal", encEmpEmlNm);
                resData.put("decptEmpEmlNm", decptEmpEmlNm);
                result.setMsg("직원 전화번호 복호화 완료");
            }else if (encptCnstnMbcmtRrno != null && !encptCnstnMbcmtRrno.isEmpty()) {
                String decptCnstnMbcmtRrno = decryptValue("normal", encptCnstnMbcmtRrno);
                resData.put("decptCnstnMbcmtRrno", decptCnstnMbcmtRrno);
                result.setMsg("자문위원 정보 복호화 완료");
            }else if (encptCnstnMbcmtActno != null && !encptCnstnMbcmtActno.isEmpty()) {
                String decptCnstnMbcmtActno = decryptValue("normal", encptCnstnMbcmtActno);
                resData.put("decptCnstnMbcmtActno", decptCnstnMbcmtActno);
                result.setMsg("자문위원 계좌정보 복호화 완료");
                // --- 복호화 불가 항목 (Hash) ---
            }else if (encptPicTelno != null && !encptPicTelno.isEmpty()) {
                resData.put("decptPicTelno", decryptValue("normal", encptPicTelno));
                result.setMsg("담당자전화번호 복호화 완료");
            } else if (encptSttyAgtTelno != null && !encptSttyAgtTelno.isEmpty()) {
                resData.put("decptSttyAgtTelno", decryptValue("normal", encptSttyAgtTelno));
                result.setMsg("법정대리인전화번호 복호화 완료");
            } else if (encptExprtFlnm != null && !encptExprtFlnm.isEmpty()) {
                resData.put("decptExprtFlnm", decryptValue("normal", encptExprtFlnm));
                result.setMsg("전문가성명 복호화 완료");
            } else if (encptExprtInstEmlNm != null && !encptExprtInstEmlNm.isEmpty()) {
                resData.put("decptExprtInstEmlNm", decryptValue("normal", encptExprtInstEmlNm));
                result.setMsg("전문가기관이메일 복호화 완료");
            } else if (encptWrtrFlnm != null && !encptWrtrFlnm.isEmpty()) {
                resData.put("decptWrtrFlnm", decryptValue("normal", encptWrtrFlnm));
                result.setMsg("작성자성명 복호화 완료");
            } else if (encptWrtrTelno != null && !encptWrtrTelno.isEmpty()) {
                resData.put("decptWrtrTelno", decryptValue("normal", encptWrtrTelno));
                result.setMsg("작성자전화번호 복호화 완료");
            }

            else {
                result.setMsg("복호화할 데이터(항목)가 입력되지 않았습니다.");
            }

        } catch(Exception e) {
            log.error("복호화 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("개인정보 복호화 중 오류가 발생했습니다: " + e.getMessage());
        }

        result.setData(resData);
        return result;
    }

    /**
     *  리스트복호화
     * @return
     */
    public ApiPrnDto decryptoList(MbrEncryptListPVO reqList) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        List<HashMap<String, Object>> resList = new ArrayList<>();

        List<MbrEncryptPVO> items = reqList.getListItems();
        try {
            // 암호화 초기화 — 목록 전체에 한 번만 실행
            registerCryptoProfiles(true);

            if(items.size() > 0) {
                for (MbrEncryptPVO reqVO : items) {
                    HashMap<String, Object> resData = new HashMap<>();

                    String encptCnstnMbcmtRrno = reqVO.getEncptCnstnMbcmtRrno();
                    String encptCnstnMbcmtActno = reqVO.getEncptCnstnMbcmtActno();
                    String encptMbrFlnm = reqVO.getEncptMbrFlnm();      // 회원명
                    String encMbrEmlNm = reqVO.getEncptMbrEmlNm();    // 회원 이메일
                    String encMbrPswd = reqVO.getEncptMbrPswd();      // 비밀번호
                    String encptMbrTelno = reqVO.getEncptMbrTelno();    // 전화번호
                    String encptEmpTelno = reqVO.getEncptEmpTelno();    // 직원전화번호
                    String encptEmpEmlNm = reqVO.getEncptEmpEmlNm();    // 직원이메일명
                    String encptMngrPswd = reqVO.getMngrPswd();         // 직원비밀번호

                    // ── hash 암호화 ───────────────────────────────────────────────────
                    if (encMbrPswd != null && !encMbrPswd.isEmpty()) {
                        result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR); // 상황에 맞는 코드 사용
                        result.setMsg("비밀번호는 복호화가 불가합니다.");
                    }
                    if (encptMngrPswd != null && !encptMngrPswd.isEmpty()) {
                        result = new ApiPrnDto(ApiResultCode.VALIDATION_ERROR); // 상황에 맞는 코드 사용
                        result.setMsg("비밀번호는 복호화가 불가합니다.");
                    }

                    // ── normal 복호화 ─────────────────────────────────────────────────
                    if (encptMbrFlnm != null && !encptMbrFlnm.isEmpty()) {
                        resData.put("decptMbrFlnm", decryptValue("normal", encptMbrFlnm));
                    }
                    if (encMbrEmlNm != null && !encMbrEmlNm.isEmpty()) {
                        resData.put("decptMbrEmlNm", decryptValue("normal", encMbrEmlNm));
                    }
                    if (encptMbrTelno != null && !encptMbrTelno.isEmpty()) {
                        resData.put("decptMbrTelno", decryptValue("normal", encptMbrTelno));
                    }
                    if (encptEmpTelno != null && !encptEmpTelno.isEmpty()) {
                        resData.put("decptEmpTelno", decryptValue("normal", encptEmpTelno));
                    }
                    if (encptEmpEmlNm != null && !encptEmpEmlNm.isEmpty()) {
                        resData.put("decptEmpEmlNm", decryptValue("normal", encptEmpEmlNm));
                    }
                    if (encptCnstnMbcmtRrno != null && !encptCnstnMbcmtRrno.isEmpty()) {
                        resData.put("decptCnstnMbcmtRrno", decryptValue("normal", encptCnstnMbcmtRrno));
                    }
                    if (encptCnstnMbcmtActno != null && !encptCnstnMbcmtActno.isEmpty()) {
                        resData.put("decptCnstnMbcmtActno", decryptValue("normal", encptCnstnMbcmtActno));
                    }
                    if (reqVO.getEncptPicTelno() != null) resData.put("decptPicTelno", decryptValue("normal", reqVO.getEncptPicTelno()));
                    if (reqVO.getEncptSttyAgtTelno() != null) resData.put("decptSttyAgtTelno", decryptValue("normal", reqVO.getEncptSttyAgtTelno()));
                    if (reqVO.getEncptExprtFlnm() != null) resData.put("decptExprtFlnm", decryptValue("normal", reqVO.getEncptExprtFlnm()));
                    if (reqVO.getEncptExprtInstEmlNm() != null) resData.put("decptExprtInstEmlNm", decryptValue("normal", reqVO.getEncptExprtInstEmlNm()));
                    if (reqVO.getEncptWrtrFlnm() != null) resData.put("decptWrtrFlnm", decryptValue("normal", reqVO.getEncptWrtrFlnm()));
                    if (reqVO.getEncptWrtrTelno() != null) resData.put("decptWrtrTelno", decryptValue("normal", reqVO.getEncptWrtrTelno()));
                    resList.add(resData);
                }

            }else{
                result.setMsg("복호화할 데이터(항목)가 입력되지 않았습니다.");
            }
            result.setMsg(resList.size() + "건 복호화 완료");

        } catch (Exception e) {
            log.error("사용자 정보 복호화 실패", e);
            result = new ApiPrnDto(ApiResultCode.SYSTEM_ERROR);
            result.setMsg("사용자 정보 복호화 중 오류가 발생했습니다: " + e.getMessage());
        }

        HashMap<String, Object> wrapper = new HashMap<>();
        wrapper.put("list",resList);
        result.setData(wrapper);
        return result;
    }

    /**
     * 복호화 후 한글 인코딩 보정
     * xCrypto.Decrypt() 가 ISO-8859-1로 반환하는 경우 UTF-8로 재변환
     */
    private String decryptKo(String encryptedValue) throws Exception {
        if (encryptedValue == null || encryptedValue.isEmpty()) return encryptedValue;

        String raw = decryptValue("normal", encryptedValue);
        if (mockBase64Enabled) {
            return raw;
        }
        return new String(raw.getBytes("EUC-KR"), StandardCharsets.UTF_8);
    }


}
