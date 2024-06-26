package kunuz.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryService emailHistoryService;

    @Value("${spring.mail.username}")
    String fromMailAddress;

    public void sendEmail(String profileId, String email){
        String url = "http://localhost:8081/auth/verifyWithEmail/" + profileId;
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please button lick below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" >This is a link</a>" +
                "    </div>";
        String text = String.format(formatText, url);
        send(email, "Complete registration", text);
        emailHistoryService.save(email, url);           /// create email history
    }

    public void send(String toAccount, String subject, String text){

//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom(fromMailAddress);
//        msg.setTo(toAccount);
//        msg.setSubject(subject);
//        msg.setText(text);

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromMailAddress);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
