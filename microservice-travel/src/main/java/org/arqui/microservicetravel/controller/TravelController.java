package org.arqui.microservicetravel.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.service.TravelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {
    private TravelService travelService;
}
