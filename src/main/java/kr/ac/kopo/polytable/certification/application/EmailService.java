package kr.ac.kopo.polytable.certification.application;

import kr.ac.kopo.polytable.certification.error.CannotSendEmailExecption;
import kr.ac.kopo.polytable.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

import static javax.mail.Message.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender sender;
    private final RedisUtil redisUtil;

    private MimeMessage createMessage(String to)throws Exception{

        String certificationToken = generateCertificationToken();

        log.info("메일 생성 진입");
        log.info("수신처 : [{}],[{}]", to, certificationToken);

        MimeMessage message = sender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("PolyTable 에서 발송된 인증메일 입니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> PolyTable </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래의 링크로 계정이 활성화됩니다.<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>인증 만료 시간 (30분)</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "LINK : <strong>";
        msgg+= "http://localhost:9090/api/public/certifications?email="+to+"&token="+certificationToken+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("zoodisx@gmail.com","PolyTable"));//보내는 사람

        redisUtil.setExpire(to, certificationToken, 30);

        return message;
    }

    public void sendMail(String to) {
        try {
            MimeMessage message = createMessage(to);
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CannotSendEmailExecption("이메일 전송에 실패했습니다.");
        }
    }


    public String generateCertificationToken() {
        String uuid = UUID.randomUUID().toString();
        String token = uuid.replaceAll("-", "");
        return token.substring(0, 8);
    }
}
