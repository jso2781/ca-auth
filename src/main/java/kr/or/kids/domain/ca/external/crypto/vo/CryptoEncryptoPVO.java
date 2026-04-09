package kr.or.kids.domain.ca.external.crypto.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "대국민포털_회원_암호화", description = "대국민포털_회원_암호화 Parameter VO")
public class CryptoEncryptoPVO {

    /**
     * 회원성명
     */
    @Schema(description = "회원성명", type = "String")
    private String mbrFlnm;

    /**
     * 회원이메일
     */
    @Schema(description = "회원이메일", type = "String")
    private String mbrEmlNm;

    /**
     * 회원비밀번호
     */
    @Schema(description = "회원비밀번호", type = "String")
    private String mbrPswd;


    /**
     * 직원전화번호
     */
    @Schema(description = "직원전화번호", type = "String")
    private String empTelno;


    /**
     * 회원전화번호
     */
    @Schema(description = "회원비밀번호", type = "String")
    private String mbrTelno;



    /**
     * 직원이메일명
     */
    @Schema(description = "직원이메일명", type = "String")
    private String empEmlNm;

    /**
     * 관리자 비밀번호
     */
    @Schema(description = "관리자비밀번호", type = "String")
    private String mngrPswd;

    /**
     * 암호화 회원성명
     */
    @Schema(description = "암호화 회원성명", type = "String")
    private String encptMbrFlnm;

    /**
     * 암호화 회원이메일
     */
    @Schema(description = "암호화 회원이메일", type = "String")
    private String encptMbrEmlNm;

    /**
     * 암호화 회원비밀번호
     */
    @Schema(description = "암호화 회원비밀번호", type = "String")
    private String encptMbrPswd;

    /**
     * 암호화 회원전화번호
     */
    @Schema(description = "암호화 회원비밀번호", type = "String")
    private String encptMbrTelno;

    /**
     * 암호화직원전화번호
     */
    @Schema(description = "암호화직원전화번호", type = "String")
    private String encptEmpTelno;

    /**
     * 암호화직원이메일명
     */
    @Schema(description = "암호화직원이메일명", type = "String")
    private String encptEmpEmlNm;

    /**
     * 암호화관리자비밀번호
     */
    @Schema(description = "암호화관리자비밀번호", type = "String")
    private String encptMngrPswd;

    /**
     * 자문위원주민등록번호
     */
    @Schema(description = "자문위원주민등록번호", type = "String")
    private String cnstnMbcmtRrno;

    /**
     * 자문위원계좌번호
     */
    @Schema(description = "자문위원주민등록번호", type = "String")
    private String cnstnMbcmtActno;

    /**
     * 암호화자문위원주민등록번호
     */
    @Schema(description = "자문위원주민등록번호", type = "String")
    private String encptCnstnMbcmtRrno;

    /**
     * 암호화자문위원계좌번호
     */
    @Schema(description = "자문위원주민등록번호", type = "String")
    private String encptCnstnMbcmtActno;

    /**
     * 이전비밀번호
     */
    @Schema(description = "이전비밀번호", type = "String")
    private String bfrPswd;

    /**
     * 암호화이전비밀번호
     */
    @Schema(description = "암호화이전비밀번호", type = "String")
    private String encptBfrPswd;

    /**
     * 복호화이전비밀번호
     */
    @Schema(description = "암호화이전비밀번호", type = "String")
    private String decptBfrPswd;


    /**
     * 댓글비밀번호
     */
    @Schema(description = "댓글비밀번호", type = "String")
    private String cmntPswd;

    /**
     * 암호화댓글비밀번호
     */
    @Schema(description = "암호화댓글비밀번호", type = "String")
    private String encptCmntPswd;

    /**
     * 복호화댓글비밀번호
     */
    @Schema(description = "복호화댓글비밀번호", type = "String")
    private String decptCmntPswd;

    /**
     * 담당자전화번호
     */
    @Schema(description = "담당자전화번호", type = "String")
    private String picTelno;

    /**
     * 암호화담당자전화번호
     */
    @Schema(description = "암호화담당자전화번호", type = "String")
    private String encptPicTelno;

    /**
     * 복호화담당자전화번호
     */
    @Schema(description = "암호화담당자전화번호", type = "String")
    private String decptPicTelno;

    /**
     * 법정대리인전화번호
     */
    @Schema(description = "법정대리인전화번호", type = "String")
    private String sttyAgtTelno;

    /**
     * 암호화법정대리인전화번호
     */
    @Schema(description = "암호화법정대리인전화번호", type = "String")
    private String encptSttyAgtTelno;

    /**
     * 복호화법정대리인전화번호
     */
    @Schema(description = "암호화법정대리인전화번호", type = "String")
    private String decptSttyAgtTelno;

    /**
     * 전문가성명
     */
    @Schema(description = "전문가성명", type = "String")
    private String exprtFlnm;


    /**
     * 암호화전문가성명
     */
    @Schema(description = "암호화전문가성명", type = "String")
    private String encptExprtFlnm;

    /**
     * 복호화전문가성명
     */
    @Schema(description = "복호화전문가성명", type = "String")
    private String decptExprtFlnm;

    /**
     * 전문가기관이메일명
     */
    @Schema(description = "전문가기관이메일명", type = "String")
    private String exprtInstEmlNm;

    /**
     * 암호화전문가기관이메일명
     */
    @Schema(description = "암호화전문가기관이메일명", type = "String")
    private String encptExprtInstEmlNm;

    /**
     * 복호화전문가기관이메일명
     */
    @Schema(description = "복호화전문가기관이메일명", type = "String")
    private String decptExprtInstEmlNm;

    /**
     * 작성자성명
     */
    @Schema(description = "작성자성명", type = "String")
    private String wrtrFlnm;

    /**
     * 암호화작성자성명
     */
    @Schema(description = "작성자성명", type = "String")
    private String encptWrtrFlnm;

    /**
     * 복호화작성자성명
     */
    @Schema(description = "복호화작성자성명", type = "String")
    private String decptWrtrFlnm;

    /**
     * 작성자전화번호
     */
    @Schema(description = "작성자전화번호", type = "String")
    private String wrtrTelno;

    /**
     * 암호화작성자전화번호
     */
    @Schema(description = "암호화작성자전화번호", type = "String")
    private String encptWrtrTelno;


    /**
     * 복호화작성자전화번호
     */
    @Schema(description = "복호화작성자전화번호", type = "String")
    private String decptWrtrTelno;
}
