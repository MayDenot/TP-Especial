package org.arqui.microservicetravel.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.repository.StopRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopService {
    private StopRepository stopRepository;
}
