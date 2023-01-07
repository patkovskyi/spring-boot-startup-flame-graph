package com.github.patkovskyi.springbootstartupflamegraph.domain.actuator;

import java.util.List;
import java.util.Map;

public record StartupStep(long id, Long parentId, String name, List<Map<String, String>> tags) {
}
