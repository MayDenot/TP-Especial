package org.example.microservicestop.controller;

import lombok.RequiredArgsConstructor;
import org.example.microservicestop.service.StopService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stop")
@RequiredArgsConstructor
public class StopController {
    private StopService stopService;
}
