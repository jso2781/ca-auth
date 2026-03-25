package kr.or.kids.domain.ca.user.vo;

import lombok.Data;

@Data
public class UserDeleteReqVO {

    private Long userId;          // 사용자 일련번호
    private Integer upAdminId;    // 수정자 ID

}
