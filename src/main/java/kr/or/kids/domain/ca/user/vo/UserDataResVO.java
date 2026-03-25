package kr.or.kids.domain.ca.user.vo;

import lombok.Data;

@Data
public class UserDataResVO {

    private Integer userId;         // 사용자번호
    private String userLoginId;     // 사용자로그인ID
    private String userName;        // 사용자명
    private String userEmail;       // 사용자이메일
    private String userTel;         // 사용자전화번호
    private String genderTypeCd;    // 사용자 성별
    private String userStatusCd;    // 사용자 상태코드
    private String mbshSeCd;        // 회원 파트
    private String userTypeCd;      // 사용자 구분 코드
    private String delYn;           // 삭제 여부
    private Integer inAdminId;      // 등록 관리자 번호
    private String inDatetime;      // 등록 시간
    private Integer upAdminId;      // 수정 관리자 번호
    private String upDatetime;      // 수정 시간

}
