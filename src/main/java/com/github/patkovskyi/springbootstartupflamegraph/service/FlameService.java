package com.github.patkovskyi.springbootstartupflamegraph.service;

import com.github.patkovskyi.springbootstartupflamegraph.domain.actuator.Event;
import com.github.patkovskyi.springbootstartupflamegraph.domain.actuator.Startup;
import com.github.patkovskyi.springbootstartupflamegraph.domain.d3.D3FlameNode;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlameService {
    public D3FlameNode convertToD3Flame(Startup startup) {
        Map<Long, Event> eventMap = startup.timeline().events()
                .stream()
                .collect(Collectors.toMap(e -> e.startupStep().id(), e -> e));

        Map<Long, D3FlameNode> d3NodeMap = startup.timeline().events()
                .stream()
                .collect(Collectors.toMap(e -> e.startupStep().id(), this::convertEvent));

        D3FlameNode root = new D3FlameNode("startup", getTotalStartupTime(startup));

        // set children
        for (Map.Entry<Long, Event> entry : eventMap.entrySet()) {
            Long id = entry.getKey();
            Event event = entry.getValue();
            Long parentId = event.startupStep().parentId();

            if (parentId == null) {
                root.children().add(d3NodeMap.get(id));
            } else {
                d3NodeMap.get(parentId).children().add(d3NodeMap.get(id));
            }
        }

        return root;
    }

    public double getTotalStartupTime(Startup startup) {
        return 1e-9 * startup.timeline().events().stream()
                .filter(e -> e.startupStep().parentId() == null)
                .mapToLong(e -> e.duration().toNanos()).sum();
    }

    private D3FlameNode convertEvent(Event e) {
        String displayName = e.startupStep().name();

        // for bean instantiations, print tags first
        if (Objects.equals(displayName, "spring.beans.instantiate")) {
            StringBuilder sb = new StringBuilder();
            List<Map<String, String>> sortedTags = e.startupStep().tags().stream()
                    .sorted(Comparator.comparing(m -> m.getOrDefault("key", ""))).toList();

            for (Map<String, String> tag : sortedTags) {
                sb.append(tag.getOrDefault("value", "") + " (" + tag.getOrDefault("key", "") + ") | ");
            }

            displayName = sb + displayName;
        }

        return new D3FlameNode(displayName, e.duration().toNanos() * 1e-9);
    }
}
