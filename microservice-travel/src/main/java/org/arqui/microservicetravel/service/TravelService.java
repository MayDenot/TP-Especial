package org.arqui.microservicetravel.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.repository.TravelRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelService {
    private TravelRepository travelRepository;
}
