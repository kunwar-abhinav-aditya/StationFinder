package com.hubject.task.service;

import com.hubject.task.dao.ChargingStationRepo;
import com.hubject.task.model.ChargingStation;
import com.hubject.task.model.Geolocation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChargingStationServiceTest {

    @Mock
    private ChargingStationRepo chargingStationRepo;

    @InjectMocks
    private ChargingStationService chargingStationService;

    @Test
    public void testAddChargingStation() {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(11.0);
        geolocation.setLongitude(15.0);
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setStationId("123");
        chargingStation.setZipCode(Long.valueOf(12345));
        chargingStation.setGeolocation(geolocation);

        when(chargingStationRepo.save(any(ChargingStation.class))).thenReturn(chargingStation);

        ChargingStation cs = chargingStationService.addChargingStation(chargingStation);
        assertEquals(chargingStation, cs);
        assertEquals("123", cs.getStationId());
        assertEquals(java.util.Optional.of(11.0).get(), cs.getGeolocation().getLatitude());
        assertEquals(java.util.Optional.of(15.0).get(), cs.getGeolocation().getLongitude());
    }

    @Test
    public void testGetChargingStations_NoParams() {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(11.0);
        geolocation.setLongitude(15.0);
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setStationId("123");
        chargingStation.setZipCode(Long.valueOf(12345));
        chargingStation.setGeolocation(geolocation);

        Geolocation geolocation2 = new Geolocation();
        geolocation2.setLatitude(15.0);
        geolocation2.setLongitude(18.0);
        ChargingStation chargingStation2 = new ChargingStation();
        chargingStation2.setStationId("234");
        chargingStation2.setZipCode(Long.valueOf(23456));
        chargingStation2.setGeolocation(geolocation2);

        List<ChargingStation> csList = new ArrayList<>();
        csList.add(chargingStation);
        csList.add(chargingStation2);

        when(chargingStationRepo.findAll()).thenReturn(csList);

        List<ChargingStation> csListReturn = chargingStationService.getChargingStations(
                null, null, null, null);
        assertEquals(2, csListReturn.size());
    }

    @Test
    public void testGetChargingStations_ZipCodeParam() {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(11.0);
        geolocation.setLongitude(15.0);
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setStationId("123");
        chargingStation.setZipCode(Long.valueOf(12345));
        chargingStation.setGeolocation(geolocation);

        Geolocation geolocation2 = new Geolocation();
        geolocation2.setLatitude(15.0);
        geolocation2.setLongitude(18.0);
        ChargingStation chargingStation2 = new ChargingStation();
        chargingStation2.setStationId("234");
        chargingStation2.setZipCode(Long.valueOf(23456));
        chargingStation2.setGeolocation(geolocation2);

        List<ChargingStation> csListOnlyOneZip = new ArrayList<>();
        csListOnlyOneZip.add(chargingStation);

        when(chargingStationRepo.findByZipCode(any(Long.class))).thenReturn(csListOnlyOneZip);

        List<ChargingStation> csListReturnZip = chargingStationService.getChargingStations(
                Long.valueOf(12345), null, null, null);
        assertEquals(1, csListReturnZip.size());
        assertEquals("123", csListReturnZip.get(0).getStationId());
    }

    @Test
    public void testGetChargingStations_CoordinateParams() {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(11.0);
        geolocation.setLongitude(15.0);
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setStationId("123");
        chargingStation.setZipCode(Long.valueOf(12345));
        chargingStation.setGeolocation(geolocation);

        Geolocation geolocation2 = new Geolocation();
        geolocation2.setLatitude(15.0);
        geolocation2.setLongitude(18.0);
        ChargingStation chargingStation2 = new ChargingStation();
        chargingStation2.setStationId("234");
        chargingStation2.setZipCode(Long.valueOf(23456));
        chargingStation2.setGeolocation(geolocation2);

        List<ChargingStation> csList = new ArrayList<>();
        csList.add(chargingStation);
        csList.add(chargingStation2);

        when(chargingStationRepo.findAll()).thenReturn(csList);

        List<ChargingStation> csListReturnCoord1 = chargingStationService.getChargingStations(
                null, 10.0, 12.0, new Float(350));
        assertEquals(1, csListReturnCoord1.size());
        assertEquals("123", csListReturnCoord1.get(0).getStationId());

        List<ChargingStation> csListReturnCoord2 = chargingStationService.getChargingStations(
                null, 10.0, 12.0, new Float(860));
        assertEquals(2, csListReturnCoord2.size());
    }

    @Test
    public void testGetChargingStation() {
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(11.0);
        geolocation.setLongitude(15.0);
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setStationId("123");
        chargingStation.setZipCode(Long.valueOf(12345));
        chargingStation.setGeolocation(geolocation);

        when(chargingStationRepo.findByStationId(any(String.class))).thenReturn(java.util.Optional.of(chargingStation));

        Optional<ChargingStation> cs = chargingStationService.getChargingStation("1");
        assertEquals("123", cs.get().getStationId());
        assertEquals(Long.valueOf(12345), cs.get().getZipCode());
    }
}
