package com.hubject.task.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
public class ChargingStation {
    @Id
    private String stationId;

    @NotNull
    private Long zipCode;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "GEOLOCATION_ID")
    @Valid
    @NotNull
    private Geolocation geolocation;
}
