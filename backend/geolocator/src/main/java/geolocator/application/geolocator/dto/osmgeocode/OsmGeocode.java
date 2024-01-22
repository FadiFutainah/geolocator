package geolocator.application.geolocator.dto.osmgeocode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OsmGeocode(String addresstype, List<String> boundingbox, String category, String name,
                         @JsonProperty("display_name") String displayName, Long importance, String type,
                         @JsonProperty("lat") String latitude, @JsonProperty("place_id") Long id,
                         @JsonProperty("lon") String longitude, @JsonProperty("osm_type") String osmType,
                         @JsonProperty("place_rank") String placeRank) {
}