package kr.or.kids.domain.ca.user.vo;

import lombok.Data;

@Data
public class UserInsertReqVO {

    private Long userId;           // 사용자 일련번호
    private String userLoginId;    // 사용자로그인 ID
    private String userPw;         // 사용자로그인 비번
    private String userName;       // 사용자명
    private String userTel;        // 사용자전화번호
    private String userEmail;      // 사용자 이메일
    private String genderTypeCd;   // 사용자 성별
    private String userStatusCd;   // 사용자 상태코드
    private String mbshSeCd;       // 회원 파트
    private String userTypeCd;     // 사용자 구분 코드
    private String lastIp;         // 접속 아이피
    private String delYn;          // 삭제
    private Integer inAdminId;     // 등록 관리자 ID (Integer로 변경)
    private String inDatetime;     // 등록일시
    private Integer upAdminId;     // 수정자 ID (Integer로 변경)
    private String upDatetime;     // 수정일시

}
