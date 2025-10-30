package org.arqui.microservicetravel.service;

import org.arqui.microservicetravel.repository.TravelRepository;

@Service
@RequieredArgsConstructor
public class TravelService {
    private TravelRepository travelRepository;
}
