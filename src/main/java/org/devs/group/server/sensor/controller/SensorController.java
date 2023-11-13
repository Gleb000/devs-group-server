package org.devs.group.server.sensor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devs.group.server.measurement.dto.MeasurementRequest;
import org.devs.group.server.measurement.dto.MeasurementResponse;
import org.devs.group.server.sensor.dto.RegisterSensorRequest;
import org.devs.group.server.sensor.dto.RegisterSensorResponse;
import org.devs.group.server.sensor.dto.SensorResponse;
import org.devs.group.server.sensor.dto.SensorWithMeasurementResponse;
import org.devs.group.server.sensor.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/registration")
    public ResponseEntity<RegisterSensorResponse> registerSensor(
            @RequestBody @Valid RegisterSensorRequest request
    ) {
        RegisterSensorResponse response = sensorService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{key}/measurements")
    public ResponseEntity<Void> addMeasurement(
            @PathVariable String key,
            @RequestBody @Valid MeasurementRequest request
    ) {
        sensorService.addMeasurementToSensor(key, request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SensorResponse>> getAllActiveSensors() {
        List<SensorResponse> response = sensorService.getAll();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{key}/measurements")
    public ResponseEntity<List<MeasurementResponse>> getLastMeasurements(
            @PathVariable String key
    ) {
        List<MeasurementResponse> response = sensorService.getLastTwentyMeasurements(key);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/measurements")
    public ResponseEntity<List<SensorWithMeasurementResponse>> getActualSensorsInfo() {
        List<SensorWithMeasurementResponse> response = sensorService.getActualSensorsMeasurements();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
