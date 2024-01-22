package geolocator.application.geolocator.repository;

import geolocator.application.geolocator.model.Geocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeocodeRepository extends JpaRepository<Geocode, Long> {
    @Query("SELECT e FROM Geocode e WHERE :address LIKE CONCAT('%', e.name, '%')")
    List<Geocode> searchByName(@Param("address") String address);
}
