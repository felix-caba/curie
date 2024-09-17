package org.cosm.curie.servicios;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.ClickTrackingSetting;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.TrackingSettings;
import org.cosm.curie.entidades.PasswordResetToken;
import org.cosm.curie.entidades.Usuario;
import org.cosm.curie.repositorios.PasswordResetTokenRepository;
import org.cosm.curie.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.time.LocalDateTime;


@Service
public class PasswordResetService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepositorio userRepository;
    @Autowired
    private SendGrid sendGrid;

    @Value("${sendgrid.from-email}")
    private String fromEmail;


    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void createPasswordResetTokenForUser(Usuario user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUsuario(user);
        myToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(myToken);
    }

    public void sendPasswordResetEmail(String toEmail, String token) {

        Email from = new Email(fromEmail);
        String subject = "Solicitud de restablecimiento de contraseña";

        Email to = new Email(toEmail);



        Content content = new Content("text/plain", "Para restablecer tu contraseña, haz clic en el siguiente enlace:\n"
                + "http://localhost:8080/reset-password?token=" + token);

        Mail mail = new Mail(from, subject, to, content);

        TrackingSettings trackingSettings = new TrackingSettings();
        ClickTrackingSetting clickTrackingSetting = new ClickTrackingSetting();
        clickTrackingSetting.setEnable(false);
        trackingSettings.setClickTrackingSetting(clickTrackingSetting);
        mail.setTrackingSettings(trackingSettings);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            // Manejar la excepción (log, throw, etc.)
            ex.printStackTrace();
        }

    }

    public String validatePasswordResetToken(String token) {

        PasswordResetToken passToken = tokenRepository.findByToken(token);
        if (passToken == null) {
            return "invalidToken";
        }

        if (passToken.isExpired()) {
            tokenRepository.delete(passToken);
            return "expired";
        }

        return "valid";

    }

    public Usuario getUserByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token).getUsuario();
    }

    public void changeUserPassword(Usuario user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    public void deletePasswordResetToken(String token) {
        tokenRepository.delete(tokenRepository.findByToken(token));
    }

}

