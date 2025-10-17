package ie.atu.lab3cicddp.service;

import ie.atu.lab3cicddp.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring-managed service
public class PassengerService {

    // In-memory list to store passengers (no database yet)
    private final List<Passenger> store = new ArrayList<>();

    // Return a defensive copy of the list (prevents external modification)
    public List<Passenger> findAll() {
        return new ArrayList<>(store);
    }

    // Find passenger by ID using Optional to handle not-found cases safely
    public Optional<Passenger> findById(String id) {
        for (Passenger p : store) {
            if (p.getPassengerID().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    // Add new passenger — prevent duplicates using ID check
    public Passenger create(Passenger p) {
        if (findById(p.getPassengerID()).isPresent()) {
            throw new IllegalArgumentException("PassengerId already exists");
        }
        store.add(p);
        return p;
    }

    public Optional<Passenger> update(Passenger p) {
        if (findById(p.getPassengerID()).isPresent()) {
            throw new IllegalArgumentException("PassengerId with ID "+ p.getPassengerID() +"already exists");
        }
        store.add(p);
        return Optional.of(p);
    }
}
