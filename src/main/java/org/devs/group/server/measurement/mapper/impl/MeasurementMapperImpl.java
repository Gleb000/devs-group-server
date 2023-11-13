package org.devs.group.server.measurement.mapper.impl;

import org.devs.group.server.measurement.dto.MeasurementResponse;
import org.devs.group.server.measurement.mapper.MeasurementMapper;
import org.devs.group.server.measurement.model.MeasurementModel;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapperImpl implements MeasurementMapper {

    @Override
    public MeasurementResponse mapMeasurementModelToMeasurementResponse(MeasurementModel measurementModel) {
        return MeasurementResponse.builder()
                .value(measurementModel.getValue())
                .raining(measurementModel.isRaining())
                .measurementDate(measurementModel.getMeasurementDate())
                .build();
    }
}
