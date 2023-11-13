package org.devs.group.server.measurement.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "measurement")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measurement_value")
    private double value;

    @Column(name = "raining")
    private boolean raining;

    @Column(name = "created_at")
    private Instant measurementDate;
}
