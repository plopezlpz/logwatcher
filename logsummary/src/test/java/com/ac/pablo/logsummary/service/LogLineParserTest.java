package com.ac.pablo.logsummary.service;

import com.ac.pablo.logsummary.service.model.LogLine;
import com.ac.pablo.logsummary.service.model.Severity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogLineParserTest {

    @Test
    void parse() {
        LogLine line = LogLineParser.parse("2016-09-20 16:23:10,994 INFO  Some info message");

        assertThat(line).isNotNull();
        assertThat(line.getTimestamp().toString()).isEqualTo("2016-09-20T15:23:10.994Z");
        assertThat(line.getSeverity()).isEqualTo(Severity.INFO);
    }

    @Test
    void ignoreOnException() {
        // dot instead of comma
        LogLine line = LogLineParser.parse("2016-09-20 16:23:10.994 INFO  Some info message");

        assertThat(line).isNull();
    }
}
