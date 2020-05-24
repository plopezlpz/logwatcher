package com.ac.pablo.logsummary.service.model;

import lombok.Data;

@Data
public class Aggregate {
    private int info;
    private int warning;
    private int error;

    public void add(Severity severity) {
        switch (severity) {
            case INFO:
                info++;
                break;
            case WARNING:
                warning++;
                break;
            case ERROR:
                error++;
                break;
        }
    }
}
