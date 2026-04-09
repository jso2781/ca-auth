package kr.or.kids.global.system.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailContentVO extends CommonVO {

    private String CONTENTS;
    private String TITLE;
    private String FROM_ADDRESS;
    private String[] TO_ADDRESS;
    private String[] CC_ADDRESS;
    private String[] BCC_ADDRESS;

    // Builder 커스터마이징
    public static class MailContentVOBuilder {
        public MailContentVOBuilder TO_ADDRESS(String[] TO_ADDRESS) {
            this.TO_ADDRESS = TO_ADDRESS == null ? null : TO_ADDRESS.clone();
            return this;
        }
        public MailContentVOBuilder CC_ADDRESS(String[] CC_ADDRESS) {
            this.CC_ADDRESS = CC_ADDRESS == null ? null : CC_ADDRESS.clone();
            return this;
        }
        public MailContentVOBuilder BCC_ADDRESS(String[] BCC_ADDRESS) {
            this.BCC_ADDRESS = BCC_ADDRESS == null ? null : BCC_ADDRESS.clone();
            return this;
        }
    }

    // getter/setter 오버라이드
    public String[] getTO_ADDRESS() {
        return TO_ADDRESS == null ? null : TO_ADDRESS.clone();
    }
    public void setTO_ADDRESS(String[] tO_ADDRESS) {
        this.TO_ADDRESS = tO_ADDRESS == null ? null : tO_ADDRESS.clone();
    }
    public String[] getCC_ADDRESS() {
        return CC_ADDRESS == null ? null : CC_ADDRESS.clone();
    }
    public void setCC_ADDRESS(String[] cC_ADDRESS) {
        this.CC_ADDRESS = cC_ADDRESS == null ? null : cC_ADDRESS.clone();
    }
    public String[] getBCC_ADDRESS() {
        return BCC_ADDRESS == null ? null : BCC_ADDRESS.clone();
    }
    public void setBCC_ADDRESS(String[] bCC_ADDRESS) {
        this.BCC_ADDRESS = bCC_ADDRESS == null ? null : bCC_ADDRESS.clone();
    }
}