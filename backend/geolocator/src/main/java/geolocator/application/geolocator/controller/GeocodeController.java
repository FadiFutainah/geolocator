package geolocator.application.geolocator.controller;

import geolocator.application.geolocator.dto.GeocodeRequestDto;
import geolocator.application.geolocator.dto.ResponseDto;
import geolocator.application.geolocator.model.Geocode;
import geolocator.application.geolocator.service.GeocodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geocode")
@RequiredArgsConstructor
@CrossOrigin
public class GeocodeController {
    private final GeocodeService geocodeService;

    @PostMapping
    public ResponseEntity<ResponseDto<List<Geocode>>> getGeocodeByAddress(
            @Valid @RequestBody GeocodeRequestDto geocodeRequestDto) {
        return geocodeService.getGeocodeByAddress(geocodeRequestDto);
    }
}
