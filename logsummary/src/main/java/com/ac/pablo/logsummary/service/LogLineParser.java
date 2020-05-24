package com.ac.pablo.logsummary.service;

import com.ac.pablo.logsummary.service.model.LogLine;
import com.ac.pablo.logsummary.service.model.Severity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

class LogLineParser {

    private static final Logger LOG = LoggerFactory.getLogger(LogLineParser.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * Parses the log line with the following format
     * <pre>
     *     2016-09-20 16:23:10,994 INFO  Some info message
     * </pre>
     *
     * @param line The line to parse
     * @return An instance of LogLine
     */
    static LogLine parse(String line) {
        if (line == null) {
            return null;
        }
        try {
            Optional<Severity> severity = parseSeverity(line);
            if (severity.isPresent()) {
                String time = line.substring(0, line.indexOf(severity.get().name()));
                return new LogLine(parseTime(time), severity.get());
            }
        } catch (Exception e) {
            LOG.error("Ignoring line: " + e.getMessage());
        }
        return null;
    }

    private static Optional<Severity> parseSeverity(String line) {
        return Arrays.stream(Severity.values()).map(Enum::name)
                .filter(line::contains)
                .findFirst()
                .map(Severity::valueOf);
    }

    private static Instant parseTime(String time) throws ParseException {
        return new SimpleDateFormat(DATE_FORMAT).parse(time).toInstant();
    }
}
