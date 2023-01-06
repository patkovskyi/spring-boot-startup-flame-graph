package com.github.patkovskyi.springbootstartupflamegraph;

import com.github.patkovskyi.springbootstartupflamegraph.domain.Event;
import com.github.patkovskyi.springbootstartupflamegraph.domain.Startup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;

@SpringBootApplication
@RestController
public class SpringBootStartupFlameGraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartupFlameGraphApplication.class, args);
    }

    @GetMapping("/hello/{user}")
    public String hello(@PathVariable String user) {
        return "Hello, " + user;
    }

    @PostMapping("/longest")
    public Optional<Event> longest(@RequestBody Startup startup) {
        return startup.timeline().events().stream().max(Comparator.comparing(Event::duration));
    }
}
