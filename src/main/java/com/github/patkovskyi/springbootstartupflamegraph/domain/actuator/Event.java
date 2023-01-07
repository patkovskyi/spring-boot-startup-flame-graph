package com.github.patkovskyi.springbootstartupflamegraph.domain.actuator;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Duration;
import java.time.Instant;

public record Event(
        @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
        Instant startTime,
        @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
        Instant endTime,
        @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
        Duration duration,
        StartupStep startupStep) {
}
