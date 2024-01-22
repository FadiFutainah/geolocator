package geolocator.application.geolocator.exception;

import geolocator.application.geolocator.exception.CommonExceptions.ResourceNotFoundException;
import geolocator.application.geolocator.exception.CommonExceptions.InternalServerException;
import geolocator.application.geolocator.dto.osmgeocode.OsmGeocode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class OSMClientErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        throw new InternalServerException("Service call failed");
    }

    public void handleErrorAfterParsing(ResponseEntity<OsmGeocode[]> response) {
        try {
            List<OsmGeocode> osmGeocodeList = List.of(Objects.requireNonNull(response.getBody()));
            if (osmGeocodeList.isEmpty()) {
                throw new ResourceNotFoundException("The provided address was not found");
            }
        } catch (Exception e) {
            throw new InternalServerException("Service call failed");
        }
    }
}
