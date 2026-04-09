package kr.or.kids.domain.ca.external.crypto.client;

import kr.or.kids.domain.ca.external.core.ExternalApiClient;
import kr.or.kids.domain.ca.external.crypto.vo.CryptoEncryptoListPVO;
import kr.or.kids.domain.ca.external.crypto.vo.CryptoEncryptoPVO;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CryptoClient {
    private static final String SERVICE_KEY = "crypto";
    private final ExternalApiClient api;

    public CryptoClient(ExternalApiClient api) {
        this.api = api;
    }

    public ApiPrnDto encrypto(CryptoEncryptoPVO req) {
        return api.post(
                SERVICE_KEY,
                "/api/ca/auth/crypto/encrypto",
                Map.of("X-Client", "ca-auth"),
                null,
                req,
                ApiPrnDto.class
        );
    }

    public ApiPrnDto encryptoList(CryptoEncryptoListPVO req) {
        return api.post(
                SERVICE_KEY,
                "/api/ca/auth/crypto/encryptoList",
                Map.of("X-Client", "ca-auth"),
                null,
                req,
                ApiPrnDto.class
        );
    }

    public ApiPrnDto decrypto(CryptoEncryptoPVO req) {
        return api.post(
                SERVICE_KEY,
                "/api/ca/auth/crypto/decrypto",
                Map.of("X-Client", "ca-auth"),
                null,
                req,
                ApiPrnDto.class
        );
    }

    public ApiPrnDto decryptoList(CryptoEncryptoListPVO req) {
        return api.post(
                SERVICE_KEY,
                "/api/ca/auth/crypto/decryptoList",
                Map.of("X-Client", "ca-auth"),
                null,
                req,
                ApiPrnDto.class
        );
    }
}
