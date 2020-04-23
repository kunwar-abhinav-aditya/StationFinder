package com.stationfinder.task.dao;

import com.stationfinder.task.model.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChargingStationRepo extends JpaRepository<ChargingStation, UUID> {

    boolean existsByStationId(String stationId);

    List<ChargingStation> findByZipCode(long zipCode);

    Optional<ChargingStation> findByStationId(String id);
}
