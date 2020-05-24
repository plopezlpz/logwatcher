package com.ac.pablo.logsummary.service;

import com.ac.pablo.logsummary.service.model.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;

public class SeverityAggregator implements AggregatorService{

    private static final Logger LOG = LoggerFactory.getLogger(SeverityAggregator.class);

    private final File file;
    private final LogReader reader;
    private final Notifier messenger;

    private Aggregate aggregate;
    private volatile long intervalInSecs;
    private volatile long fileLastModified = -1;

    public SeverityAggregator(File file, LogReader reader, Notifier messenger, long intervalInSecs) {
        this.file = file;
        this.reader = reader;
        this.messenger = messenger;
        this.intervalInSecs = intervalInSecs;
    }

    public void setIntervalAndReaggregate(long intervalInSecs) {
        LOG.info("Changed interval from " + this.intervalInSecs + ", to: " + intervalInSecs);
        this.intervalInSecs = intervalInSecs;
        // reset it to force rerunning
        this.fileLastModified = -1;
    }

    public long getIntervalInSecs() {
        return intervalInSecs;
    }

    public Aggregate getLastAggregate() {
        return aggregate;
    }

    @Scheduled(fixedDelay = 500)
    void watchAndAggregate() throws IOException {
        if (file.lastModified() != fileLastModified) {
            fileLastModified = file.lastModified();

            aggregate = new Aggregate();
            reader.readLinesWithinInterval(intervalInSecs, line -> {
                aggregate.add(line.getSeverity());
                LOG.trace(line.toString());
            });
            // TODO send aggregate resultsto  MQ
            messenger.notifyAggregate(aggregate);
            LOG.debug(aggregate.toString());
        }
    }
}
