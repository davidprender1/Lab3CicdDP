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

    @PutMapping("/updateName")
    public ResponseEntity<Passenger> updateName(@Valid @RequestBody Passenger p) {
        Optional<Passenger> maybe = service.findById(p.getPassengerID());
        if (maybe.isPresent()) {
            Passenger updated = maybe.get();
            updated.setName(p.getName());
            updated.setEmail(p.getEmail());
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/ID")
    public ResponseEntity<?> delete(@Valid @RequestBody Passenger p) {
        Optional<Passenger> maybe = service.findById(p.getPassengerID());
        if (maybe.isPresent()) {
            Passenger updated = maybe.get();
            return ResponseEntity.ok().build();
        }
        else  {
            return ResponseEntity.notFound().build();
        }
    }
}

