# Description
This project allows you to build a [Flame Graph](https://www.brendangregg.com/flamegraphs.html) 
from Spring Boot Actuator Startup output to analyze startup performance of your Spring Boot app and visually identify slow steps.

# Background
Starting from Spring Boot 2.4, you can analyze your app's startup performance by exposing `/actuator/startup` endpoint, 
which prints duration of all startup steps (e.g., time it took to instantiate every bean). The problem is that [this JSON output](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/#startup.retrieving.snapshot)
is hard to analyze by hand or with simple bash scripts, so you need some visualization to understand what's happening.
I think Flame Graph is perfect for this task.

# How to use this project
1. Copy JSON output from `/actuator/startup` endpoint of your Spring Boot (2.4+) app.
2. Clone & launch this project (`./gradlew bootRun`). It should auto-download JDK17 if you don't have it.
3. http://localhost:8042/ -> paste JSON -> get Flame Graph.

To enable `/actuator/startup` endpoint in your app, follow these steps:
1. Add `spring-boot-starter-actuator` dependency to your app ([doc](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)).
2. Set your applicationStartup to `BufferingApplicationStartup` ([doc](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application.startup-tracking)), for example:
```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
    }
}
```
3. Enable Startup endpoint and expose it over HTTP ([doc](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)), for example:
```yaml
# in your application.properties file
management.endpoint.startup.enabled=true
management.endpoints.web.exposure.include=startup
```
**WARNING:** make sure you understand security implications of exposing Actuator endpoints before enabling them in production.

# Future work
Convert this project into a Single Page App similar to https://alexey-lapin.github.io/spring-boot-startup-analyzer,
so it can be hosted on Github and doesn't require cloning/building to use.

# Inspired by:
* https://github.com/brendangregg/FlameGraph (original Flame Graph-generating software written in Perl; can't use directly in this project because it expects callstacks, not json with durations)
* https://github.com/spiermar/d3-flame-graph (JS library I use to render interactive Flame Graphs from a simple json structure)
* https://github.com/lwaddicor/spring-startup-analysis (tree map visualization of Spring startup; works for non-Boot Spring projects, but only analyzes bean creation)
* https://github.com/alexey-lapin/spring-boot-startup-analyzer (a nice SPA that allows to sort startup events, but does not provide any visualizations)
