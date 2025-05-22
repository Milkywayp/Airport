package airport.model.repositories;

import airport.model.Passenger;
import java.util.*;

public class PassengerRepository {
    private final Map<Long, Passenger> passengers = new HashMap<>();

    public boolean addPassenger(Passenger p) {
        if (p == null || passengers.containsKey(p.getId())) return false;
        passengers.put(p.getId(), p);
        return true;
    }

    public Passenger getPassenger(long id) {
        return passengers.get(id);
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> list = new ArrayList<>(passengers.values());
        list.sort(Comparator.comparingLong(Passenger::getId));
        return list;
    }

    public boolean exists(long id) {
        return passengers.containsKey(id);
    }

    public boolean removePassenger(long id) {
        return passengers.remove(id) != null;
    }
}

