package com.ac.pablo.logsummary.service;

import com.ac.pablo.logsummary.service.model.Aggregate;

public interface AggregatorService {

    void setIntervalAndReaggregate(long intervalInSecs);

    long getIntervalInSecs();

    Aggregate getLastAggregate();
}
