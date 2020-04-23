package com.stationfinder.task.service;

import com.stationfinder.task.exception.ChargingStationException;
import com.stationfinder.task.model.ChargingStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stations")
public class ChargingStationController {

    @Autowired
    ChargingStationService chargingStationService;

    @PostMapping
    public ChargingStation addChargingStation(@Valid @RequestBody ChargingStation chargingStation) throws ChargingStationException {
        return chargingStationService.addChargingStation(chargingStation);
    }

    @GetMapping
    public List<ChargingStation> getChargingStations(@RequestParam(required = false) Long zipCode,
                                                     @RequestParam(required = false) Double latitude,
                                                     @RequestParam(required = false) Double longitude,
                                                     @RequestParam(required = false) Float radius) throws ChargingStationException {
        return chargingStationService.getChargingStations(zipCode, latitude, longitude, radius);
    }

    @GetMapping(path = "/{id}")
    public Optional<ChargingStation> getChargingStation(@PathVariable("id") String id) {
        return chargingStationService.getChargingStation(id);
    }
}
