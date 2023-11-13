package org.devs.group.server.sensor.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devs.group.server.config.validation.exception.NotUniqueEntityException;
import org.devs.group.server.measurement.comparator.MeasurementComparator;
import org.devs.group.server.measurement.dto.MeasurementRequest;
import org.devs.group.server.measurement.dto.MeasurementResponse;
import org.devs.group.server.measurement.mapper.MeasurementMapper;
import org.devs.group.server.measurement.model.MeasurementModel;
import org.devs.group.server.measurement.service.MeasurementService;
import org.devs.group.server.sensor.dto.RegisterSensorRequest;
import org.devs.group.server.sensor.dto.RegisterSensorResponse;
import org.devs.group.server.sensor.dto.SensorResponse;
import org.devs.group.server.sensor.dto.SensorWithMeasurementResponse;
import org.devs.group.server.sensor.mapper.SensorMapper;
import org.devs.group.server.sensor.model.SensorModel;
import org.devs.group.server.sensor.repository.SensorRepository;
import org.devs.group.server.sensor.service.SensorService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private static final long MINUTE_IN_SECONDS = 60;
    private static final long MEASUREMENTS_DISPLAY_LIMIT = 20;

    private final SensorMapper sensorMapper;
    private final SensorRepository sensorRepository;
    private final MeasurementMapper measurementMapper;
    private final MeasurementService measurementService;

    @Override
    public RegisterSensorResponse register(RegisterSensorRequest request) {
        if (sensorRepository.existsByName(request.getName())) {
            throw new NotUniqueEntityException(
                    String.format("Sensor with name [%s] is already exists", request.getName())
            );
        }

        String key = UUID.randomUUID().toString();
        SensorModel sensorModel = SensorModel.builder()
                .name(request.getName())
                .key(key)
                .updatedAt(Instant.now())
                .build();

        sensorRepository.save(sensorModel);
        log.info("New sensor with name [{}] is registered", request.getName());

        return RegisterSensorResponse.builder()
                .key(key)
                .build();
    }

    @Override
    public void addMeasurementToSensor(String key, MeasurementRequest request) {
        Instant measurementDate = Instant.now();

        SensorModel sensorModel = sensorRepository.findByKeyOrThrow(key);
        MeasurementModel measurementModel = measurementService.add(request, measurementDate);

        sensorModel.getMeasurements().add(measurementModel);
        sensorModel.setUpdatedAt(measurementDate);

        sensorRepository.save(sensorModel);

        log.info(
                "Measurement with date [{}] was added to sensor with key [{}]",
                measurementDate,
                sensorModel.getKey()
        );
    }

    @Override
    public List<SensorResponse> getAll() {
        List<SensorModel> sensorModels = sensorRepository.findAll();
        Instant validityDate = Instant.now().minusSeconds(MINUTE_IN_SECONDS);

        log.info("[{}] sensors was retrieved", sensorModels.size());

        List<SensorResponse> activeSensors = sensorModels.stream()
                .filter(model -> !model.getUpdatedAt().isBefore(validityDate))
                .map(sensorMapper::mapSensorModelToSensorResponse)
                .collect(Collectors.toList());

        log.info(
                "[{}] sensors are active, [{}] sensors are inactive",
                activeSensors.size(),
                sensorModels.size() - activeSensors.size()
        );

        return activeSensors;
    }

    @Override
    public List<MeasurementResponse> getLastTwentyMeasurements(String key) {
        SensorModel sensorModel = sensorRepository.findByKeyOrThrow(key);
        List<MeasurementModel> measurementModels = sensorModel.getMeasurements();

        List<MeasurementResponse> lastMeasurements = measurementModels.stream()
                .map(measurementMapper::mapMeasurementModelToMeasurementResponse)
                .sorted(new MeasurementComparator())
                .limit(MEASUREMENTS_DISPLAY_LIMIT)
                .collect(Collectors.toList());

        log.info(
                "[{}] measurements were found for sensor with key [{}]",
                lastMeasurements.size(),
                key
        );

        return lastMeasurements;
    }

    @Override
    public List<SensorWithMeasurementResponse> getActualSensorsMeasurements() {
        List<SensorModel> sensorModels = sensorRepository.findAll();
        Instant validityDate = Instant.now().minusSeconds(MINUTE_IN_SECONDS);

        log.info("[{}] sensors was retrieved", sensorModels.size());

        List<SensorWithMeasurementResponse> activeSensors = sensorModels.stream()
                .filter(model -> !model.getUpdatedAt().isBefore(validityDate))
                .map(model -> sensorMapper.mapSensorModelToSensorWithMeasurementResponse(
                        model,
                        validityDate)
                )
                .collect(Collectors.toList());

        log.info(
                "[{}] sensors are active, [{}] sensors are inactive",
                activeSensors.size(),
                sensorModels.size() - activeSensors.size()
        );

        return activeSensors;
    }
}
