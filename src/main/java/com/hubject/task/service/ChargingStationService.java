package com.hubject.task.service;

import com.hubject.task.dao.ChargingStationRepo;
import com.hubject.task.exception.ChargingStationException;
import com.hubject.task.model.ChargingStation;
import com.hubject.task.model.Geolocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChargingStationService {

    @Autowired
    ChargingStationRepo chargingStationRepo;

    /**
     * Get charging stations based on the supplied parameters.
     * If none of the parameters are present, return all stations.
     * If the parameter zipCode is present, return stations filtered by the provided zipCode;
     * else find and return stations along a given radius to specified coordinates.
     */
    public List<ChargingStation> getChargingStations(Long zipCode, Double latitude, Double longitude, Float radius)
            throws ChargingStationException {
        if (zipCode == null && latitude == null && longitude == null && radius == null) {
            return chargingStationRepo.findAll();
        } else if (zipCode != null) {
            return chargingStationRepo.findByZipCode(zipCode);
        } else {
            if (latitude == null || longitude == null || radius == null) {
                throw new ChargingStationException("Missing Coordinate Parameter(s)",
                        "One or more of the parameters latitude, longitude or radius is/are missing",
                        "Provide all the three parameters latitude, longitude & radius");
            }
            return getChargingStationsInGivenRadius(latitude, longitude, radius);
        }
    }

    private List<ChargingStation> getChargingStationsInGivenRadius(Double latitude, Double longitude, Float radius) {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(latitude);
        geolocation.setLongitude(longitude);

        List<ChargingStation> stationsInGivenRadius = new ArrayList<>();
        for (ChargingStation existingChargingStation : chargingStationRepo.findAll()) {
            if (distanceBetweenStations(geolocation, existingChargingStation.getGeolocation()) <= radius) {
                stationsInGivenRadius.add(existingChargingStation);
            }
        }
        return stationsInGivenRadius;
    }

    // Implementation of Haversine formula to calculate distance between two coordinates
    private double distanceBetweenStations(Geolocation givenGeoLocation, Geolocation existingGeoLocation) {
        if (existingGeoLocation.getLatitude() == givenGeoLocation.getLatitude() &&
            existingGeoLocation.getLongitude() == givenGeoLocation.getLongitude()) {
            return 0;
        } else {
            final int radiusOfEarth = 6371;
            double latDistanceRadStations = toRad(existingGeoLocation.getLatitude() - givenGeoLocation.getLatitude());
            double lonDistanceRadStations = toRad(existingGeoLocation.getLongitude() - givenGeoLocation.getLongitude());
            double intermediateCalculation = Math.sin(latDistanceRadStations / 2) * Math.sin(latDistanceRadStations / 2) +
                    Math.cos(toRad(givenGeoLocation.getLatitude())) * Math.cos(toRad(existingGeoLocation.getLatitude())) *
                            Math.sin(lonDistanceRadStations / 2) * Math.sin(lonDistanceRadStations / 2);
            double c = 2 * Math.atan2(Math.sqrt(intermediateCalculation), Math.sqrt(1 - intermediateCalculation));
            return radiusOfEarth * c;
        }
    }

    // Convert to radians
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public Optional<ChargingStation> getChargingStation(String id) {
        return chargingStationRepo.findByStationId(id);
    }

    // Add a charging station
    public ChargingStation addChargingStation(ChargingStation chargingStation) {
        if (chargingStationRepo.existsByStationId(chargingStation.getStationId())) {
            throw new ChargingStationException("Non-unique ID",
                    "The ID you have chosen is not unique",
                    "Try with a new ID");
        } else {
            try {
                return chargingStationRepo.save(chargingStation);
            } catch (DataIntegrityViolationException ex) {
                throw new ChargingStationException("Non-unique geolocation",
                        "The geolocation coordinates you are entering are not unique",
                        "We already have a station at the given geolocation. Try with new coordinates");
            } catch (Exception ex) {
                throw new ChargingStationException(ex.getMessage(), ex.getLocalizedMessage(), "");
            }
        }
    }
}
