package com.ac.pablo.logsummary.service;

import com.ac.pablo.logsummary.service.model.LogLine;
import com.ac.pablo.logsummary.utils.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class LogReader {
    private static final Logger LOG = LoggerFactory.getLogger(LogReader.class);

    private final File file;
    private final TimeProvider time;

    public void readLinesWithinInterval(long intervalInSecs, Consumer<LogLine> onLineWithinInterval) throws IOException {
        Instant from = time.now().minusSeconds(intervalInSecs);
        try (ReversedLinesFileReader fr = new ReversedLinesFileReader(file, StandardCharsets.UTF_8)) {

            for (String lineStr = fr.readLine(); lineStr != null; lineStr = fr.readLine()) {
                LogLine line = LogLineParser.parse(lineStr);
                if (line != null) {
                    if (line.getTimestamp().isAfter(from)) {
                        onLineWithinInterval.accept(line);
                    } else {
                        // reached end of interval
                        return;
                    }
                }
            }

        }
    }
}
