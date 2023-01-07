package com.github.patkovskyi.springbootstartupflamegraph.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.patkovskyi.springbootstartupflamegraph.domain.actuator.Startup;
import com.github.patkovskyi.springbootstartupflamegraph.domain.d3.D3FlameNode;
import com.github.patkovskyi.springbootstartupflamegraph.service.FlameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FlameController {
    private final FlameService flameService;
    private final ObjectMapper objectMapper;

    public FlameController(FlameService flameService, ObjectMapper objectMapper) {
        this.flameService = flameService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping(value = "/flame", consumes = {"application/x-www-form-urlencoded"})
    public String flame(Model model, @RequestParam String startupText) throws JsonProcessingException {
        Startup startup = objectMapper.readValue(startupText, Startup.class);
        D3FlameNode flameData = flameService.convertToD3Flame(startup);
        model.addAttribute("flameData", flameData);
        return "flame.html";
    }
}
