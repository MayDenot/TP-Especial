package org.example.microservicestop.service;

import lombok.RequiredArgsConstructor;
import org.example.microservicestop.repository.StopRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopService {
    private StopRepository stopRepository;
}
