package com.github.patkovskyi.springbootstartupflamegraph.domain;

import java.util.List;
import java.util.Map;

public record StartupStep(int id, Integer parentId, String name, List<Map<String, String>> tags) {
}
