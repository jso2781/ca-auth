package kr.or.kids.domain.ca.crypto.service.impl;

import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import kr.or.kids.domain.ca.crypto.service.CryptoService;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptListPVO;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptPVO;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.softforum.xdbe.xCrypto;

import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class CryptoServiceImpl implements CryptoService {

    /**
     * 암호화
     * @return
     */
    public ApiPrnDto encrypto(MbrEncryptPVO reqVO) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        HashMap<String, Object> resData = new HashMap<>();
        // 암호화 초기화 및 실행
        xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
        xCrypto.RegisterEx("hash", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "hash");

        try {
            String mbrFlnm = reqVO.getMbrFlnm();      // 회원명
            String mbrEmlNm = reqVO.getMbrEmlNm();    // 회원 이메일
            String mbrPswd = reqVO.getMbrPswd();      // 회원 비밀번호
            String mbrTelno = reqVO.getMbrTelno();    // 회원 전화번호
            String empTelno = reqVO.getEmpTelno();    // 직원전화번호
            String empEmlNm = reqVO.getEmpEmlNm();    // 직원이메일명
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

                    String encryptMbrFlnm = xCrypto.Encrypt("normal", mbrFlnm,"UTF-8");
                    resData.put("encptMbrFlnm", encryptMbrFlnm);
                    result.setMsg("회원명 암호화 완료");

                }else if (mbrEmlNm != null && !mbrEmlNm.isEmpty()) {
                    String encryptMbrEmlNm = xCrypto.Encrypt("normal", mbrEmlNm,"UTF-8");
                    resData.put("encptMbrEmlNm", encryptMbrEmlNm);
                    result.setMsg("회원이메일 암호화 완료");

                }else if (mbrTelno != null && !mbrTelno.isEmpty()) {
                    String encryptMbrTelno = xCrypto.Encrypt("normal", mbrTelno,"UTF-8");
                    resData.put("encptMbrTelno", encryptMbrTelno);
                    result.setMsg("회원 전화번호 암호화 완료");

                }else if (mbrPswd != null && !mbrPswd.isEmpty()) {
                    String encrptMbrPswd = xCrypto.Encrypt("hash", mbrPswd,"UTF-8");
                    resData.put("encptMbrPswd", encrptMbrPswd);
                    result.setMsg("사용자 정보 암호화 완료");

                }else if (empTelno != null && !empTelno.isEmpty()) {
                    String encptempTelno = xCrypto.Encrypt("hash", empTelno,"UTF-8");
                    resData.put("encptempTelno", encptempTelno);
                    result.setMsg("직원 전화번호 암호화 완료");

                }else if (empEmlNm != null && !empEmlNm.isEmpty()) {
                    String encptEmpEmlNm = xCrypto.Encrypt("hash", empEmlNm,"UTF-8");
                    resData.put("encptEmpEmlNm", encptEmpEmlNm);
                    result.setMsg("직원 이메일명 암호화 완료");

                }else if (cnstnMbcmtRrno != null && !cnstnMbcmtRrno.isEmpty()) {
                    String encptCnstnMbcmtRrno = xCrypto.Encrypt("hash", cnstnMbcmtRrno,"UTF-8");
                    resData.put("encptCnstnMbcmtRrno", encptCnstnMbcmtRrno);
                    result.setMsg("자문위원 정보 암호화 완료");

                }else if (cnstnMbcmtActno != null && !cnstnMbcmtActno.isEmpty()) {
                    String encptCnstnMbcmtActno = xCrypto.Encrypt("hash", cnstnMbcmtActno,"UTF-8");
                    resData.put("encptCnstnMbcmtActno", encptCnstnMbcmtActno);
                    result.setMsg("자문위원 계좌정보 암호화 완료");

                }else if (mngrPswd != null && !mngrPswd.isEmpty()) {
                    String encptMngrPswd = xCrypto.Encrypt("hash", mngrPswd,"UTF-8");
                    resData.put("encptMngrPswd", encptMngrPswd);
                    result.setMsg("관리자 비밀번호 암호화 완료");
                    // --- 신규 항목 추가 ---
                }else if (bfrPswd != null && !bfrPswd.isEmpty()) {
                    resData.put("encptBfrPswd", xCrypto.Encrypt("hash", bfrPswd,"UTF-8"));
                    result.setMsg("이전비밀번호 암호화 완료");
                } else if (cmntPswd != null && !cmntPswd.isEmpty()) {
                    resData.put("encptCmntPswd", xCrypto.Encrypt("hash", cmntPswd,"UTF-8"));
                    result.setMsg("댓글비밀번호 암호화 완료");
                } else if (picTelno != null && !picTelno.isEmpty()) {
                    resData.put("encptPicTelno", xCrypto.Encrypt("normal", picTelno,"UTF-8"));
                    result.setMsg("담당자전화번호 암호화 완료");
                } else if (sttyAgtTelno != null && !sttyAgtTelno.isEmpty()) {
                    resData.put("encptSttyAgtTelno", xCrypto.Encrypt("normal", sttyAgtTelno,"UTF-8"));
                    result.setMsg("법정대리인전화번호 암호화 완료");
                } else if (exprtFlnm != null && !exprtFlnm.isEmpty()) {
                    resData.put("encptExprtFlnm", xCrypto.Encrypt("normal", exprtFlnm,"UTF-8"));
                    result.setMsg("전문가성명 암호화 완료");
                } else if (exprtInstEmlNm != null && !exprtInstEmlNm.isEmpty()) {
                    resData.put("encptExprtInstEmlNm", xCrypto.Encrypt("normal", exprtInstEmlNm,"UTF-8"));
                    result.setMsg("전문가기관이메일 암호화 완료");
                } else if (wrtrFlnm != null && !wrtrFlnm.isEmpty()) {
                    resData.put("encptWrtrFlnm", xCrypto.Encrypt("normal", wrtrFlnm,"UTF-8"));
                    result.setMsg("작성자성명 암호화 완료");
                } else if (wrtrTelno != null && !wrtrTelno.isEmpty()) {
                    resData.put("encptWrtrTelno", xCrypto.Encrypt("normal", wrtrTelno,"UTF-8"));
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
            xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
            xCrypto.RegisterEx("hash",   2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "hash");

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
                        resData.put("encptMbrFlnm", xCrypto.Encrypt("normal", mbrFlnm,"UTF-8"));
                    }
                    if (mbrEmlNm != null && !mbrEmlNm.isEmpty()) {
                        resData.put("encptMbrEmlNm", xCrypto.Encrypt("normal", mbrEmlNm,"UTF-8"));
                    }
                    if (mbrTelno != null && !mbrTelno.isEmpty()) {
                        resData.put("encptMbrTelno", xCrypto.Encrypt("normal", mbrTelno,"UTF-8"));
                    }
                    if (empTelno != null && !empTelno.isEmpty()) {
                        resData.put("encptEmpTelno", xCrypto.Encrypt("normal", empTelno,"UTF-8"));
                    }
                    if (empEmlNm != null && !empEmlNm.isEmpty()) {
                        resData.put("encptEmpEmlNm", xCrypto.Encrypt("normal", empEmlNm,"UTF-8"));
                    }
                    if (cnstnMbcmtRrno != null && !cnstnMbcmtRrno.isEmpty()) {
                        resData.put("encptCnstnMbcmtRrno", xCrypto.Encrypt("normal", cnstnMbcmtRrno,"UTF-8"));
                    }
                    if (cnstnMbcmtActno != null && !cnstnMbcmtActno.isEmpty()) {
                        resData.put("encptCnstnMbcmtActno", xCrypto.Encrypt("normal", cnstnMbcmtActno,"UTF-8"));
                    }
                    // ── hash 암호화 ───────────────────────────────────────────────────
                    if (mbrPswd != null && !mbrPswd.isEmpty()) {
                        resData.put("encptMbrPswd", xCrypto.Encrypt("hash", mbrPswd,"UTF-8"));
                    }
                    if (mngrPswd != null && !mngrPswd.isEmpty()) {
                        resData.put("encptMngrPswd", xCrypto.Encrypt("hash", mngrPswd,"UTF-8"));
                    }
                    // 추가 신규 필드 암호화
                    if (reqVO.getBfrPswd() != null) resData.put("encptBfrPswd", xCrypto.Encrypt("hash", reqVO.getBfrPswd(),"UTF-8"));
                    if (reqVO.getCmntPswd() != null) resData.put("encptCmntPswd", xCrypto.Encrypt("hash", reqVO.getCmntPswd(),"UTF-8"));
                    if (reqVO.getPicTelno() != null) resData.put("encptPicTelno", xCrypto.Encrypt("normal", reqVO.getPicTelno(),"UTF-8"));
                    if (reqVO.getSttyAgtTelno() != null) resData.put("encptSttyAgtTelno", xCrypto.Encrypt("normal", reqVO.getSttyAgtTelno(),"UTF-8"));
                    if (reqVO.getExprtFlnm() != null) resData.put("encptExprtFlnm", xCrypto.Encrypt("normal", reqVO.getExprtFlnm(),"UTF-8"));
                    if (reqVO.getExprtInstEmlNm() != null) resData.put("encptExprtInstEmlNm", xCrypto.Encrypt("normal", reqVO.getExprtInstEmlNm(),"UTF-8"));
                    if (reqVO.getWrtrFlnm() != null) resData.put("encptWrtrFlnm", xCrypto.Encrypt("normal", reqVO.getWrtrFlnm(),"UTF-8"));
                    if (reqVO.getWrtrTelno() != null) resData.put("encptWrtrTelno", xCrypto.Encrypt("normal", reqVO.getWrtrTelno(),"UTF-8"));
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
        xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
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

                 log.info("encMbrFlnm:::00::::"+ encMbrFlnm);
                 String decryptMbrEmlNm = xCrypto.Decrypt("normal", reqVO.getEncptMbrFlnm(),"UTF-8");
                 log.info("decryptMbrEmlNm_test::::1::::"+ decryptMbrEmlNm);
                 resData.put("decptMbrFlnm", decryptMbrEmlNm);

                result.setMsg("회원명 복호화 완료");
            }else if (encMbrEmlNm != null && !encMbrEmlNm.isEmpty()) {
                String decryptMbrEmlNm = xCrypto.Decrypt("normal", encMbrEmlNm,"UTF-8");
                resData.put("decptMbrEmlNm", decryptMbrEmlNm);
                result.setMsg("회원 이메일 복호화 완료");
            }else if (encMbrTelno != null && !encMbrTelno.isEmpty()) {
                String decryptMbrTelno = xCrypto.Decrypt("normal", encMbrTelno,"UTF-8");
                resData.put("decptMbrTelno", decryptMbrTelno);
                result.setMsg("회원 전화번호 복호화 완료");
            }else if (encEmpTelno != null && !encEmpTelno.isEmpty()) {
                String decptEncEmpTelno = xCrypto.Decrypt("normal", encEmpTelno,"UTF-8");
                resData.put("decptEncEmpTelno", decptEncEmpTelno);
                result.setMsg("직원 전화번호 복호화 완료");
            }else if (encEmpEmlNm != null && !encEmpEmlNm.isEmpty()) {
                String decptEmpEmlNm = xCrypto.Decrypt("normal", encEmpEmlNm,"UTF-8");
                resData.put("decptEmpEmlNm", decptEmpEmlNm);
                result.setMsg("직원 전화번호 복호화 완료");
            }else if (encptCnstnMbcmtRrno != null && !encptCnstnMbcmtRrno.isEmpty()) {
                String decptCnstnMbcmtRrno = xCrypto.Decrypt("normal", encptCnstnMbcmtRrno,"UTF-8");
                resData.put("decptCnstnMbcmtRrno", decptCnstnMbcmtRrno);
                result.setMsg("자문위원 정보 암호화 완료");
            }else if (encptCnstnMbcmtActno != null && !encptCnstnMbcmtActno.isEmpty()) {
                 String decptCnstnMbcmtActno = xCrypto.Decrypt("normal", encptCnstnMbcmtActno,"UTF-8");
                 resData.put("decptCnstnMbcmtActno", decptCnstnMbcmtActno);
                 result.setMsg("자문위원 계좌정보 암호화 완료");
                 // --- 복호화 불가 항목 (Hash) ---
            }else if (encptPicTelno != null && !encptPicTelno.isEmpty()) {
                  resData.put("decptPicTelno", xCrypto.Decrypt("normal", encptPicTelno,"UTF-8"));
                  result.setMsg("담당자전화번호 복호화 완료");
            } else if (encptSttyAgtTelno != null && !encptSttyAgtTelno.isEmpty()) {
                   resData.put("decptSttyAgtTelno", xCrypto.Decrypt("normal", encptSttyAgtTelno,"UTF-8"));
                   result.setMsg("법정대리인전화번호 복호화 완료");
            } else if (encptExprtFlnm != null && !encptExprtFlnm.isEmpty()) {
                   resData.put("decptExprtFlnm", xCrypto.Decrypt("normal", encptExprtFlnm,"UTF-8"));
                   result.setMsg("전문가성명 복호화 완료");
            } else if (encptExprtInstEmlNm != null && !encptExprtInstEmlNm.isEmpty()) {
                   resData.put("decptExprtInstEmlNm", xCrypto.Decrypt("normal", encptExprtInstEmlNm,"UTF-8"));
                   result.setMsg("전문가기관이메일 복호화 완료");
            } else if (encptWrtrFlnm != null && !encptWrtrFlnm.isEmpty()) {
                   resData.put("decptWrtrFlnm", xCrypto.Decrypt("normal", encptWrtrFlnm,"UTF-8"));
                   result.setMsg("작성자성명 복호화 완료");
            } else if (encptWrtrTelno != null && !encptWrtrTelno.isEmpty()) {
                   resData.put("decptWrtrTelno", xCrypto.Decrypt("normal", encptWrtrTelno,"UTF-8"));
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
            xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
            xCrypto.RegisterEx("hash",   2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "hash");

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
                        resData.put("decptMbrFlnm", xCrypto.Decrypt("normal", encptMbrFlnm,"UTF-8"));
                    }
                    if (encMbrEmlNm != null && !encMbrEmlNm.isEmpty()) {
                        resData.put("decptMbrEmlNm", xCrypto.Decrypt("normal", encMbrEmlNm,"UTF-8"));
                    }
                    if (encptMbrTelno != null && !encptMbrTelno.isEmpty()) {
                        resData.put("decptMbrTelno", xCrypto.Decrypt("normal", encptMbrTelno,"UTF-8"));
                    }
                    if (encptEmpTelno != null && !encptEmpTelno.isEmpty()) {
                        resData.put("decptEmpTelno", xCrypto.Decrypt("normal", encptEmpTelno,"UTF-8"));
                    }
                    if (encptEmpEmlNm != null && !encptEmpEmlNm.isEmpty()) {
                        resData.put("decptEmpEmlNm", xCrypto.Decrypt("normal", encptEmpEmlNm,"UTF-8"));
                    }
                    if (encptCnstnMbcmtRrno != null && !encptCnstnMbcmtRrno.isEmpty()) {
                        resData.put("decptCnstnMbcmtRrno", xCrypto.Decrypt("normal", encptCnstnMbcmtRrno,"UTF-8"));
                    }
                    if (encptCnstnMbcmtActno != null && !encptCnstnMbcmtActno.isEmpty()) {
                        resData.put("decptCnstnMbcmtActno", xCrypto.Decrypt("normal", encptCnstnMbcmtActno,"UTF-8"));
                    }
                    if (reqVO.getEncptPicTelno() != null) resData.put("decptPicTelno", xCrypto.Decrypt("normal", reqVO.getEncptPicTelno(),"UTF-8"));
                    if (reqVO.getEncptSttyAgtTelno() != null) resData.put("decptSttyAgtTelno", xCrypto.Decrypt("normal", reqVO.getEncptSttyAgtTelno(),"UTF-8"));
                    if (reqVO.getEncptExprtFlnm() != null) resData.put("decptExprtFlnm", xCrypto.Decrypt("normal", reqVO.getEncptExprtFlnm(),"UTF-8"));
                    if (reqVO.getEncptExprtInstEmlNm() != null) resData.put("decptExprtInstEmlNm", xCrypto.Decrypt("normal", reqVO.getEncptExprtInstEmlNm(),"UTF-8"));
                    if (reqVO.getEncptWrtrFlnm() != null) resData.put("decptWrtrFlnm", xCrypto.Decrypt("normal", reqVO.getEncptWrtrFlnm(),"UTF-8"));
                    if (reqVO.getEncptWrtrTelno() != null) resData.put("decptWrtrTelno", xCrypto.Decrypt("normal", reqVO.getEncptWrtrTelno(),"UTF-8"));
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

        String raw = xCrypto.Decrypt("normal", encryptedValue);
        return new String(raw.getBytes("EUC-KR"), StandardCharsets.UTF_8);
    }


}
