package com.ac.pablo.logsummary.service.model;

import lombok.Data;

import java.time.Instant;

@Data
public class LogLine {
    private final Instant timestamp;
    private final Severity severity;
}
