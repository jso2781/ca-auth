package kr.or.kids.domain.ca.crypto.service;

import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptListPVO;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptPVO;
import kr.or.kids.global.system.common.vo.ApiPrnDto;

import java.util.List;

public interface CryptoService {

    /**
     *  아이템 암호화
     * @return API 응답 DTO
     */
    public ApiPrnDto encrypto(MbrEncryptPVO input);

    /**
     *  아이템  암호화 리스트형
     * @return API 응답 DTO
     */
    public ApiPrnDto encryptoList(MbrEncryptListPVO input);

    /**
     *  아이템 복호화
     * @return API 응답 DTO
     */
    public ApiPrnDto decrypto(MbrEncryptPVO input);

    /**
     *  아이템  복호화 리스트형
     * @return API 응답 DTO
     */
    public ApiPrnDto decryptoList(MbrEncryptListPVO input);

}
