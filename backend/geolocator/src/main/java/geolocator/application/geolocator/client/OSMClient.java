package geolocator.application.geolocator.client;

import geolocator.application.geolocator.dto.osmgeocode.OsmGeocode;
import geolocator.application.geolocator.exception.OSMClientErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OSMClient {
    private final RestTemplate restTemplate;
    private final OSMClientErrorHandler osmClientErrorHandler = new OSMClientErrorHandler();
    private static String baseUrl = "https://nominatim.openstreetmap.org/search?";

    @Autowired
    public OSMClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(osmClientErrorHandler)
                .build();
        baseUrl += "format=json";
        baseUrl += "&limit=8";
    }

    public OsmGeocode[] getGeocodeByAddress(String address) {
        String url = baseUrl + "&q=" + address;
        ResponseEntity<OsmGeocode[]> response = restTemplate
                .getForEntity(url, OsmGeocode[].class);
        osmClientErrorHandler.handleErrorAfterParsing(response);
        return response.getBody();
    }
}
