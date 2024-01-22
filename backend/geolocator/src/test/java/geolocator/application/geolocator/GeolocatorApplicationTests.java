package geolocator.application.geolocator;

import geolocator.application.geolocator.client.OSMClient;
import geolocator.application.geolocator.dto.osmgeocode.OsmGeocode;
import geolocator.application.geolocator.service.EmailService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@SpringBootTest
@EnableAsync
class GeolocatorApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(GeolocatorApplication.class);

    private final OSMClient osmClient = new OSMClient(new RestTemplateBuilder());

    private final AsyncMocking asyncMocking = new AsyncMocking();


    private final EmailService emailService = new EmailService(new JavaMailSenderImpl());

    @Test
    void contextLoads() {
    }

    @Test
    void testOSMGecodeApi() {
        try {
            List<OsmGeocode> osmGeocodeList = List.of(osmClient.getGeocodeByAddress("Los Angeles"));
            log.info(osmGeocodeList.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    @Test
    void testEmailSending() {
//        emailService.sendEmail("FadiFutainah@gmail.com", "test", "test email");
    }

    @Test
    void testAsyncMethod() throws InterruptedException {
        asyncMocking.mockAsyncMethod();
        System.out.println("here2");
        Thread.sleep(2000);
        System.out.println("here3");
    }
}
