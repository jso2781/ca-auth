package kr.or.kids.domain.ca.user.controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.softforum.xdbe.xCrypto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {

    private final MessageSource messageSource;

    public HelloController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 1) CookieLocaleResolver + lang 파라미터로 Locale 변경됨
     * 2) 또는 Accept-Language 헤더를 참고해서 Locale을 만들어 직접 조회도 가능
     *
     * 예:
     *  - /hello
     *  - /hello?lang=en
     *  - /hello?name=Jin&lang=ko
     */

    @GetMapping("/hello")
    public String helloCrypto(Model model) {
        String sString = "1234567890123";
        String name = "User"; // 예시 데이터

        try {
            long nStartTime = System.currentTimeMillis();

            // 암호화 초기화 및 실행
            xCrypto.RegisterEx("normal", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "normal");
            xCrypto.RegisterEx("hash", 2, "/app/xecuredb/conf/xdsp_pool.properties", "pool1", "drugsafe_db", "drugsafe_ow", "drugsafe_tb", "hash");

            String sOutput = xCrypto.Encrypt("normal", sString); // 주민번호 및 개인정보 암호화
            String sDecoded = xCrypto.Decrypt("normal", sOutput); // 복호화
            String sOutput_H = xCrypto.Encrypt("hash", sString);  // password  암호화 복호화 안됨

            long nEndTime = System.currentTimeMillis();
            long nDuration = nEndTime - nStartTime;

            System.out.println("name:::::"+ name);
            System.out.println("sString:::::"+ sString);
            System.out.println("sOutput:::::"+ sOutput);
            System.out.println("sOutput_H:::::"+ sOutput_H);

            // 뷰로 전달할 데이터 설정
            model.addAttribute("name", name);
            model.addAttribute("sString", sString);
            model.addAttribute("sOutput", sOutput);
            model.addAttribute("sDecoded", sDecoded);
            model.addAttribute("sOutput_H", sOutput_H);
            model.addAttribute("nDuration", nDuration);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }

        return "hello"; // hello.html 호출
    }


}
