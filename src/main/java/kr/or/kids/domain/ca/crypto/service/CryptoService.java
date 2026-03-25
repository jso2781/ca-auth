package kr.or.kids.domain.ca.crypto.service;

import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import kr.or.kids.global.system.common.vo.ApiPrnDto;

public interface CryptoService {

    /**
     *  아이템 암호화
     * @return API 응답 DTO
     */
    public ApiPrnDto encrypto(MbrTokenPVO input);

    /**
     *  아이템 복호화
     * @return API 응답 DTO
     */
    public ApiPrnDto decrypto(MbrTokenPVO input);

}
