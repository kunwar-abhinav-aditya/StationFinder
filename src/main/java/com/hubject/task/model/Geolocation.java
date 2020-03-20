package com.hubject.task.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"LATITUDE", "LONGITUDE"})
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
public class Geolocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GEOLOCATION_ID")
    private int geolocationId;

    @NotNull
    @Column(name = "LATITUDE")
    private Double latitude;

    @NotNull
    @Column(name = "LONGITUDE")
    private Double longitude;
}
