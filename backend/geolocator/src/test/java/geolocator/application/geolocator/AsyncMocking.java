package geolocator.application.geolocator;

import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class AsyncMocking {
    @Async
    public void mockAsyncMethod() {
        try {
            Thread.sleep(10000);
            System.out.println("here1");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
