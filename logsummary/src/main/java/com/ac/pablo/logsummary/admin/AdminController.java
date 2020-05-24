package com.ac.pablo.logsummary.admin;

import com.ac.pablo.logsummary.admin.dto.Interval;
import com.ac.pablo.logsummary.service.AggregatorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private final AggregatorService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    /**
     * Change interval of the summary
     */
    @PostMapping("/interval")
    public ResponseEntity<Interval> setInterval(@RequestBody Interval interval) {
        service.setIntervalAndReaggregate(interval.getIntervalInSeconds());
        return ResponseEntity.ok(new Interval(service.getIntervalInSecs()));
    }

    /**
     * Get interval of the summary
     */
    @GetMapping("/interval")
    public ResponseEntity<Interval> getInterval() {
        return ResponseEntity.ok(new Interval(service.getIntervalInSecs()));
    }

}
