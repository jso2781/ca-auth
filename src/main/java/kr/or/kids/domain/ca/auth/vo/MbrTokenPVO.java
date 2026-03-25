package kr.or.kids.domain.ca.auth.vo;

import java.math.BigInteger;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "대국민포털_회원_TOKEN", description = "대국민포털_회원_TOKEN Search Parameter VO")
public class MbrTokenPVO
{
    /**
     * JWT토큰일련번호
     */
    @Schema(description = "JWT토큰일련번호", type = "BigInteger")
    private BigInteger tokenSn;

    /**
     * 회원아이디
     */
    @Schema(description = "회원아이디", type = "String")
    private String mbrId;

    /**
     * 회원암호화비밀번호
     */
    @Schema(description = "회원암호화비밀번호", type = "String")
    private String encptMbrPswd;

    /**
     * 애플리케이션ID
     */
    @Schema(description = "애플리케이션ID", type = "String")
    private String prgrmId;

    /**
     * JWT_Refresh_Token
     */
    @Schema(description = "JWT_Refresh_Token", type = "String")
    private String updtTokenCn;

    /**
     * JWT_Access_Token
     */
    @Schema(description = "JWT_Access_Token", type = "String")
    private String acsTokenCn;

    /**
     * 등록자아이디
     */
    @Schema(description = "등록자아이디", type = "String")
    private String rgtrId;

    /**
     * 등록일시
     */
    @Schema(description = "등록일시", type = "String")
    private String regDt;

    /**
     * 수정자아이디
     */
    @Schema(description = "수정자아이디", type = "String")
    private String mdfrId;

    /**
     * 수정일시
     */
    @Schema(description = "수정일시", type = "String")
    private String mdfcnDt;


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
     * 회원전화번호
     */
    @Schema(description = "회원비밀번호", type = "String")
    private String mbrTelno;



    /**
     * 회원성명
     */
    @Schema(description = "암호화 회원성명", type = "String")
    private String encryptMbrFlnm;

    /**
     * 회원이메일
     */
    @Schema(description = "암호화 회원이메일", type = "String")
    private String encryptMbrEmlNm;

    /**
     * 회원비밀번호
     */
    @Schema(description = "암호화 회원비밀번호", type = "String")
    private String encryptMbrPswd;

    /**
     * 회원전화번호
     */
    @Schema(description = "암호화 회원비밀번호", type = "String")
    private String encryptMbrTelno;

    public BigInteger getTokenSn()
    {
        return tokenSn;
    }
    public void setTokenSn(BigInteger tokenSn)
    {
        this.tokenSn = tokenSn;
    }
    public String getMbrId()
    {
        return mbrId;
    }
    public void setMbrId(String mbrId)
    {
        this.mbrId = mbrId;
    }
    public String getPrgrmId()
    {
        return prgrmId;
    }
    public void setPrgrmId(String prgrmId)
    {
        this.prgrmId = prgrmId;
    }
    public String getUpdtTokenCn()
    {
        return updtTokenCn;
    }
    public void setUpdtTokenCn(String updtTokenCn)
    {
        this.updtTokenCn = updtTokenCn;
    }
    public String getAcsTokenCn()
    {
        return acsTokenCn;
    }
    public void setAcsTokenCn(String acsTokenCn)
    {
        this.acsTokenCn = acsTokenCn;
    }
    public String getRgtrId()
    {
        return rgtrId;
    }
    public void setRgtrId(String rgtrId)
    {
        this.rgtrId = rgtrId;
    }
    public String getRegDt()
    {
        return regDt;
    }
    public void setRegDt(String regDt)
    {
        this.regDt = regDt;
    }
    public String getMdfrId()
    {
        return mdfrId;
    }
    public void setMdfrId(String mdfrId)
    {
        this.mdfrId = mdfrId;
    }
    public String getMdfcnDt()
    {
        return mdfcnDt;
    }
    public void setMdfcnDt(String mdfcnDt)
    {
        this.mdfcnDt = mdfcnDt;
    }

    public String getMbrFlnm()
    {
        return mbrFlnm;
    }
    public void setMbrFlnm(String mbrFlnm)
    {
        this.mbrFlnm = mbrFlnm;
    }

    public String getMbrEmlNm()
    {
        return mbrEmlNm;
    }
    public void setMbrEmlNm(String mbrFlnm)
    {
        this.mbrEmlNm = mbrEmlNm;
    }

    public String getMbrPswd()
    {
        return mbrPswd;
    }
    public void setMbrPswd(String mbrPswd)
    {
        this.mbrPswd = mbrPswd;
    }

    public String getMbrTelno()
    {
        return mbrTelno;
    }
    public void setMbrTelno(String mbrTelno)
    {
        this.mbrTelno = mbrTelno;
    }

    public String getEncryptMbrFlnm()
    {
        return encryptMbrFlnm;
    }
    public void setEncryptMbrFlnm(String encryptMbrFlnm)
    {
        this.encryptMbrFlnm = encryptMbrFlnm;
    }

    public String getEncryptMbrPswd()
    {
        return encryptMbrPswd;
    }
    public void setEncryptMbrPswd(String encryptMbrPswd)
    {
        this.encryptMbrPswd = encryptMbrPswd;
    }

    public String getEncryptMbrEmlNm()
    {
        return encryptMbrEmlNm;
    }
    public void setEncryptMbrEmlNm(String encryptMbrEmlNm)
    {
        this.encryptMbrEmlNm = encryptMbrEmlNm;
    }

    public String getEncryptMbrTelno()
    {
        return encryptMbrTelno;
    }
    public void setEncryptMbrTelno(String encryptMbrTelno)
    {
        this.encryptMbrTelno = encryptMbrTelno;
    }


}