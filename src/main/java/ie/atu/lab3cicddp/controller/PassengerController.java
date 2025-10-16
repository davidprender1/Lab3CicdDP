package ie.atu.lab3cicddp.controller;

import ie.atu.lab3cicddp.model.Passenger;
import ie.atu.lab3cicddp.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passengers") // Base URL for all passenger endpoints
public class PassengerController {

    private final PassengerService service; // constructor DI

    // Constructor Injection (recommended for Spring Boot)
    public PassengerController(PassengerService service) {
        this.service = service;
    }

    // GET all passengers
    @GetMapping
    public ResponseEntity<List<Passenger>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // GET passenger by ID
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getOne(@PathVariable String id) {
        Optional<Passenger> maybe = service.findById(id);
        if (maybe.isPresent()) {
            return ResponseEntity.ok(maybe.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST add passenger (with validation)
    @PostMapping
    public ResponseEntity<Passenger> create(@Valid @RequestBody Passenger p) {
        Passenger created = service.create(p);
        return ResponseEntity
                .created(URI.create("/api/passengers/" + created.getPassengerID()))
                .body(created);
    }
}

