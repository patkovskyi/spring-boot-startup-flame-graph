package com.github.patkovskyi.springbootstartupflamegraph.domain.d3;

import java.util.ArrayList;
import java.util.List;

public record D3FlameNode(String name,
                          /*
                            duration in seconds
                           */
                          double value,
                          List<D3FlameNode> children) {
    public D3FlameNode(String name, double value) {
        this(name, value, new ArrayList<>());
    }
}
