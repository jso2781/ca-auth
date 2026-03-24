package kr.or.kids.domain.ca.connecionlog.vo;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConnectionLogInsertReqVO {
    /**
     * 접속로그일련번호
     */
    @Schema(description = "접속로그일련번호", type = "Long")
    private Long sessLogSn;

    /**
     * 로그인구분코드(1 : ID 로그인 , 2 : 애니아이디 로그인)
     */
    @Schema(description = "로그인구분코드(1 : ID 로그인 , 2 : 애니아이디 로그인)", type = "Long")
    private String lgnSeCd;

    /**
     * 네트워크 구분코드(1 : 내부망, 2 : 외부망)
     */
    @Schema(description = "네트워크 구분코드(1 : 내부망, 2 : 외부망)", type = "String")
    private String netSeCd;

    /**
     * 서비스사용자아이디
     */
    @Schema(description = "서비스사용자아이디", type = "String")
    private String srvcUserId;

    /**
     * 요청자IP주소
     */
    @Schema(description = "요청자IP주소", type = "String")
    private String rqstrIpAddr;

    /**
     * 접속구분번호(1 : 로그인, 2:로그아웃) 
     */
    @Schema(description = "접속구분번호(1 : 로그인, 2:로그아웃) ", type = "String")
    private String cntnSeNo;

    /**
     * 접속발생일시(14자리 20260210004742)
     */
    @Schema(description = "접속발생일시", type = "String")
    private String cntnOcrnDt;

    /**
     * 접속 상세 설명
     */
    @Schema(description = "접속 상세 설명", type = "String")
    private String cntnDtlExpln;

    /**
     * 인증토큰값
     */
    @Schema(description = "인증토큰값", type = "String")
    private String certTokenVl;

    /**
     * 토큰생성시간(6자리)
     */
    @Schema(description = "토큰생성시간(6자리)", type = "String")
    private String tokenCrtHr;

    /**
     * 세션만료예정시간(6자리)
     */
    @Schema(description = "세션만료예정시간(6자리)", type = "String")
    private String sessExpryPrnmntHr;

    /**
     * 서비스명
     */
    @Schema(description = "서비스명", type = "String")
    private String  srvcNm;

    /**
     * 수행시간(6자리)
     */
    @Schema(description = "수행시간(6자리)", type = "String")
    private String  flfmtHr;

    /**
     * 업무구분코드(CA, PP, CDM,..)
     */
    @Schema(description = "업무구분코드(CA, PP, CDM,..)", type = "String")
    private String taskSeCd;

    /**
     * 로그아웃일시
     */
    @Schema(description = "로그아웃일시", type = "Timestamp")
    private Timestamp lgtDt;   

    /**
     * 등록일시
     */
    @Schema(description = "등록일시", type = "Timestamp")
    private Timestamp regDt;

    /**
     * 등록자 아이디
     */
    @Schema(description = "등록자 아이디", type = "String")
    private String rgtrId;

    /**
     * 수정일시
     */
    @Schema(description = "수정일시", type = "Timestamp")
    private Timestamp mdfcnDt;

    /**
     * 수정자 아이디
     */
    @Schema(description = "수정자 아이디", type = "String")
    private String  mdfrId;

}
