package kr.or.kids.domain.ca.crypto.controller;

import com.softforum.xdbe.xCrypto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kids.domain.ca.crypto.service.CryptoService;
import kr.or.kids.domain.ca.auth.vo.MbrTokenPVO;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptListPVO;
import kr.or.kids.domain.ca.crypto.vo.MbrEncryptPVO;
import kr.or.kids.global.system.common.vo.ApiPrnDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "CryptoController", description = "대국민포털_암복호화 모듈 관리")
@RestController
@RequestMapping(value="/api/ca/auth/crypto")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @Operation(summary = "암호화처리", description = "암호화를 한다")
    @PostMapping(value="/encrypto")
    @ResponseBody
    public ResponseEntity<ApiPrnDto> encrypto(@RequestBody MbrEncryptPVO input){

        ApiPrnDto apiPrnDto = cryptoService.encrypto(input);

        if("0".equals(apiPrnDto.getCode())){
            return ResponseEntity.ok(apiPrnDto);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiPrnDto);
        }
    }

    @Operation(summary = "목록형 암호화처리", description = "목록 단위 암호화를 처리한다")
    @PostMapping(value = "/encryptoList")
    @ResponseBody
    public ResponseEntity<ApiPrnDto> encrypto(@RequestBody MbrEncryptListPVO inputs) {

        ApiPrnDto apiPrnDto = cryptoService.encryptoList(inputs);

        if ("0".equals(apiPrnDto.getCode())) {
            return ResponseEntity.ok(apiPrnDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiPrnDto);
        }
    }

    @Operation(summary = "복호화처리", description = "비번 암호화를 한다")
    @PostMapping(value="/decrypto")
    @ResponseBody
    public ResponseEntity<ApiPrnDto> decrypto(@RequestBody MbrEncryptPVO input){

        ApiPrnDto apiPrnDto = cryptoService.decrypto(input);

        if("0".equals(apiPrnDto.getCode())){
            return ResponseEntity.ok(apiPrnDto);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiPrnDto);
        }
    }

    @Operation(summary = "목록형 복호화처리", description = "목록 단위 복호화를 처리한다")
    @PostMapping(value = "/decryptoList")
    @ResponseBody
    public ResponseEntity<ApiPrnDto> decryptoList(@RequestBody MbrEncryptListPVO inputs) {

        ApiPrnDto apiPrnDto = cryptoService.decryptoList(inputs);

        if ("0".equals(apiPrnDto.getCode())) {
            return ResponseEntity.ok(apiPrnDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiPrnDto);
        }
    }

}
