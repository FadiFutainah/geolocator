package geolocator.application.geolocator.service;

import geolocator.application.geolocator.client.OSMClient;
import geolocator.application.geolocator.dto.GeocodeRequestDto;
import geolocator.application.geolocator.dto.ResponseDto;
import geolocator.application.geolocator.dto.osmgeocode.OsmGeocode;
import geolocator.application.geolocator.model.Geocode;
import geolocator.application.geolocator.repository.GeocodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeocodeService {
    private final OSMClient osmClient;
    private final GeocodeRepository geocodeRepository;
    private final EmailService emailService;

    public List<Geocode> fetchGeocodeFromOSMClient(String address) {
        List<Geocode> geocodeList = new ArrayList<>();
        List<OsmGeocode> osmGeocodeList = List.of(
                osmClient.getGeocodeByAddress(address)
        );
        for (OsmGeocode osmGeocode : osmGeocodeList) {
            geocodeList.add(new Geocode(
                    osmGeocode.id(), osmGeocode.name(), osmGeocode.displayName(), osmGeocode.latitude(),
                    osmGeocode.longitude(), osmGeocode.addresstype(), osmGeocode.category(), osmGeocode.type(),
                    osmGeocode.placeRank()
            ));
        }
        return geocodeList;
    }

    public ResponseEntity<ResponseDto<List<Geocode>>> getGeocodeByAddress(GeocodeRequestDto geocodeRequestDto) {
        List<Geocode> geocodeList = geocodeRepository.searchByName(geocodeRequestDto.address());
        if (geocodeList.isEmpty()) {
            geocodeList = fetchGeocodeFromOSMClient(geocodeRequestDto.address());
            geocodeRepository.saveAll(geocodeList);
        }
        if (geocodeRequestDto.email() != null) {
            emailService.sendGeocodeByEmail(geocodeRequestDto.email(), geocodeList);
        }
        return ResponseDto.response(geocodeList);
    }
}
