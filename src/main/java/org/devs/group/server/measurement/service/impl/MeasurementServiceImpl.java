package org.devs.group.server.measurement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devs.group.server.measurement.dto.MeasurementRequest;
import org.devs.group.server.measurement.model.MeasurementModel;
import org.devs.group.server.measurement.repository.MeasurementRepository;
import org.devs.group.server.measurement.service.MeasurementService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Override
    public MeasurementModel add(MeasurementRequest request, Instant measurementDate) {
        MeasurementModel measurementModel = MeasurementModel.builder()
                .value(request.getValue())
                .raining(request.getRaining())
                .measurementDate(measurementDate)
                .build();

        return measurementRepository.save(measurementModel);
    }
}
