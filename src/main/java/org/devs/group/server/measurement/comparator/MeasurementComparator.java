package org.devs.group.server.measurement.comparator;

import org.devs.group.server.measurement.dto.MeasurementResponse;

import java.util.Comparator;

public class MeasurementComparator implements Comparator<MeasurementResponse> {

    @Override
    public int compare(MeasurementResponse firstMeasurement, MeasurementResponse secondMeasurement) {
        if (firstMeasurement.getMeasurementDate().equals(secondMeasurement.getMeasurementDate())) {
            return 0;
        } else if (firstMeasurement.getMeasurementDate().isAfter(secondMeasurement.getMeasurementDate())) {
            return -1;
        } else {
            return 1;
        }
    }
}
