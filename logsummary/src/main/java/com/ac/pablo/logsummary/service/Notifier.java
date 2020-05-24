package com.ac.pablo.logsummary.service;

import com.ac.pablo.logsummary.service.model.Aggregate;

public interface Notifier {

    void notifyAggregate(Aggregate aggregate);
}
