package ie.atu.lab3cicddp.service;


import ie.atu.lab3cicddp.model.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PassengerServiceTest {

    private PassengerService service;

    @BeforeEach
    void setup() {
        service = new PassengerService(); // Fresh instance before each test
    }

    @Test
    void createThenFindById() {
        Passenger p = Passenger.builder()
                .passengerID("P1")
                .name("Paul")
                .email("paul@atu.ie")
                .build();

        service.create(p);

        Optional<Passenger> found = service.findById("P1");
        assertTrue(found.isPresent()); // Check that it exists
        assertEquals("Paul", found.get().getName()); // Validate stored data
    }

    @Test
    void duplicateIdThrows() {
        service.create(Passenger.builder()
                .passengerID("P2")
                .name("Bob")
                .email("bob@atu.ie")
                .build());

        assertThrows(IllegalArgumentException.class, () ->
                service.create(Passenger.builder()
                        .passengerID("P2")
                        .name("Bobby")
                        .email("bob@xx.com")
                        .build())
        );
    }
}

