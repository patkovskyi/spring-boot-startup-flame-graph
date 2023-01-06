package com.github.patkovskyi.springbootstartupflamegraph.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public record Timeline(
        @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
        Instant startTime, List<Event> events) {
}
