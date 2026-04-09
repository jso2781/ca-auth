package kr.or.kids.domain.ca.external.crypto.controller;

import kr.or.kids.domain.ca.external.crypto.client.CryptoClient;
import kr.or.kids.domain.ca.external.crypto.vo.CryptoEncryptoListPVO;
import kr.or.kids.domain.ca.external.crypto.vo.CryptoEncryptoPVO;
import kr.or.kids.global.system.common.ApiResultCode;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/ca/crypto")
public class CryptoController {

    private final CryptoClient cryptoClient;

    @PostMapping(value = "/encrypto")
    public ResponseEntity<ApiPrnDto> encrypto(@RequestBody CryptoEncryptoPVO req) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        result.setMsg("FAIL");

        try {
            result = cryptoClient.encrypto(req);
            result.setMsg("SUCCESS");
        } catch (Exception e) {
            result.setMsg("ERROR");
        }
        ApiResultCode resultCode = ApiResultCode.fromCode(result.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(result);
    }

    @PostMapping(value = "/encryptoList")
    public ResponseEntity<ApiPrnDto> encryptoList(@RequestBody CryptoEncryptoListPVO req) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        result.setMsg("FAIL");

        try {
            result = cryptoClient.encryptoList(req);
            result.setMsg("SUCCESS");
        } catch (Exception e) {
            result.setMsg("ERROR");
        }
        ApiResultCode resultCode = ApiResultCode.fromCode(result.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(result);
    }

    @PostMapping(value = "/decrypto")
    public ResponseEntity<ApiPrnDto> decrypto(@RequestBody CryptoEncryptoPVO req) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        result.setMsg("FAIL");

        try {
            result = cryptoClient.decrypto(req);
            result.setMsg("SUCCESS");
        } catch (Exception e) {
            result.setMsg("ERROR");
        }
        ApiResultCode resultCode = ApiResultCode.fromCode(result.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(result);
    }

    @PostMapping(value = "/decryptoList")
    public ResponseEntity<ApiPrnDto> decryptoList(@RequestBody CryptoEncryptoListPVO req) {
        ApiPrnDto result = new ApiPrnDto(ApiResultCode.SUCCESS);
        result.setMsg("FAIL");

        try {
            result = cryptoClient.decryptoList(req);
            result.setMsg("SUCCESS");
        } catch (Exception e) {
            result.setMsg("ERROR");
        }
        ApiResultCode resultCode = ApiResultCode.fromCode(result.getCode());
        return ResponseEntity.status(resultCode.getHttpStatus()).body(result);
    }
}
