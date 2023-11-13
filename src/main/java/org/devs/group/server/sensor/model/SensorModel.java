package org.devs.group.server.sensor.model;

import lombok.*;
import org.devs.group.server.measurement.model.MeasurementModel;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "sensor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_name")
    private String name;

    @Column(name = "sensor_key")
    private String key;

    @ManyToMany
    @JoinTable(
            name = "sensor_measurement",
            joinColumns = @JoinColumn(name = "sensor_id"),
            inverseJoinColumns = @JoinColumn(name = "measurement_id")
    )
    private List<MeasurementModel> measurements;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
