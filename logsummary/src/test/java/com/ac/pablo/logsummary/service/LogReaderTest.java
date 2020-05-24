package com.ac.pablo.logsummary.service;

import com.ac.pablo.logsummary.service.model.Severity;
import com.ac.pablo.logsummary.utils.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.ac.pablo.logsummary.service.model.Severity.ERROR;
import static com.ac.pablo.logsummary.service.model.Severity.WARNING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogReaderTest {

    private Path filePath;

    @BeforeEach
    void beforeEach(@TempDir Path tempDir) throws IOException {
        filePath = Files.createFile(tempDir.resolve("test.log"));
    }

    @Test
    void readUntilEndOfFile() throws Exception {
        TimeProvider time = mock(TimeProvider.class);
        when(time.now()).thenReturn(getTime("2016-09-20T16:23:15.994Z"));

        writeLine(filePath, "2016-09-20 16:23:13,994 WARNING  Some other warning message");
        writeLine(filePath, "2016-09-20 16:23:14,994 ERROR  Some error message");

        LogReader reader = new LogReader(filePath.toFile(), time);

        List<Severity> actual = new ArrayList<>();
        reader.readLinesWithinInterval(99, line -> {
            actual.add(line.getSeverity());
        });
        assertThat(actual).containsExactly(ERROR, WARNING);
    }

    @Test
    void readUpUntilInterval() throws Exception {
        TimeProvider time = mock(TimeProvider.class);
        when(time.now()).thenReturn(getTime("2016-09-20T16:23:15.993Z"));

        writeLine(filePath, "2016-09-20 16:23:10,994 WARNING  Some other warning message");
        writeLine(filePath, "2016-09-20 16:23:11,994 WARNING  Some other warning message");
        writeLine(filePath, "2016-09-20 16:23:12,994 WARNING  Some other warning message");
        writeLine(filePath, "2016-09-20 16:23:13,994 WARNING  Some other warning message");
        writeLine(filePath, "2016-09-20 16:23:14,994 ERROR  Some error message");

        LogReader reader = new LogReader(filePath.toFile(), time);

        List<Severity> actual = new ArrayList<>();
        reader.readLinesWithinInterval(3, line -> {
            actual.add(line.getSeverity());
        });
        assertThat(actual).containsExactly(ERROR, WARNING, WARNING);
    }

    @Test
    void readIgnoringGarbageLines() throws Exception {
        TimeProvider time = mock(TimeProvider.class);
        when(time.now()).thenReturn(getTime("2016-09-20T16:23:15.993Z"));

        writeLine(filePath, "2016-09-20 16:23:11,994 WARNING  Some other warning message");
        writeLine(filePath, "2016-09-20 16:23:12,994 WARNING  Some other warning message");
        writeLine(filePath, "2016-09-20 16:23:13,994 WARNING  Some other warning message");
        writeLine(filePath, "ljfdksjfkdsjlfkdsjsa");
        writeLine(filePath, "");
        writeLine(filePath, "morkdkd");
        writeLine(filePath, "2016-09-20 16:23:14,994 ERROR  Some error message");

        LogReader reader = new LogReader(filePath.toFile(), time);

        List<Severity> actual = new ArrayList<>();
        reader.readLinesWithinInterval(2, line -> {
            actual.add(line.getSeverity());
        });
        assertThat(actual).containsExactly(ERROR, WARNING);
    }


    private static void writeLine(Path path, String line) throws IOException {
        String s = line + System.lineSeparator();
        Files.write(path, s.getBytes(), StandardOpenOption.APPEND);
    }

    private static Instant getTime(String time) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(time).toInstant();
    }

}
