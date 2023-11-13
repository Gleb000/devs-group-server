package org.devs.group.server.sensor.repository;

import org.devs.group.server.config.validation.exception.EntityNotFoundException;
import org.devs.group.server.sensor.model.SensorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<SensorModel, Long> {

    boolean existsByName(String name);

    Optional<SensorModel> findByKey(String key);

    default SensorModel findByKeyOrThrow(String key) {
        return findByKey(key).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Sensor with key [%s] not found", key)
                )
        );
    }
}
