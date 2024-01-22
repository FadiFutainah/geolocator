package geolocator.application.geolocator.service;

import geolocator.application.geolocator.model.Geocode;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendGeocodeByEmail(String to, List<Geocode> geocodeList) {
        String subject = "Location information";
        StringBuilder body = new StringBuilder();
        for (Geocode geocode : geocodeList) {
            body.append("address information:\n")
                    .append("name: ").append(geocode.getName()).append("\n")
                    .append("type: ").append(geocode.getType()).append("\n")
                    .append("category: ").append(geocode.getCategory()).append("\n")
                    .append("latitude: ").append(geocode.getLatitude()).append("\n")
                    .append("longitude: ").append(geocode.getLongitude()).append("\n")
                    .append("display name: ").append(geocode.getDisplayName()).append("\n")
                    .append("address type: ").append(geocode.getAddresstype()).append("\n")
                    .append("______________________\n");
        }
        sendEmail(to, subject, body.toString());
    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
    }
}
