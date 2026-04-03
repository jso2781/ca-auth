package kr.or.kids.domain.ca.crypto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "대국민포털_회원_암호화목록", description = "대국민포털_회원_암호화 Parameter LIST VO")
public class MbrEncryptListPVO {
    private List<MbrEncryptPVO> listItems;
}
