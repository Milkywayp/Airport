package airport.model.repositories;

import airport.model.Flight; // CORREGIDO: import correcto

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightRepository {

    private final Map<String, Flight> flights = new HashMap<>();

    public boolean addFlight(Flight flight) {
        if (flights.containsKey(flight.getId())) {
            return false;
        }
        flights.put(flight.getId(), flight);
        return true;
    }

    public Flight getFlight(String id) {
        return flights.get(id);
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights.values());
    }

    public void removeFlight(String id) {
        flights.remove(id);
    }

    public boolean exists(String id) {
        return flights.containsKey(id);
    }

    public void clear() {
        flights.clear();
    }
}

