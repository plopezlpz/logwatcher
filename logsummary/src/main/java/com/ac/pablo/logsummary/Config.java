package com.ac.pablo.logsummary;

import com.ac.pablo.logsummary.service.AggregatorService;
import com.ac.pablo.logsummary.service.LogReader;
import com.ac.pablo.logsummary.service.Notifier;
import com.ac.pablo.logsummary.service.SeverityAggregator;
import com.ac.pablo.logsummary.utils.TimeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class Config {

    @Value("${app.logaggregation.intervalinseconds.default}")
    private long defaultIntervalInSeconds;

    @Bean
    public File observedFile(@Value("${app.logaggregation.logfile.path}") String filePath) throws FileNotFoundException {
        return ResourceUtils.getFile(filePath);
    }

    @Bean
    public LogReader logReader(File observedFile, TimeProvider time) {
        return new LogReader(observedFile, time);
    }

    @Bean
    public AggregatorService fileObserver(File observedFile, LogReader reader, Notifier notifier) {
        return new SeverityAggregator(observedFile, reader, notifier, defaultIntervalInSeconds);
    }

    @Bean
    public TimeProvider timeProvider() {
        return new TimeProvider();
    }
}
