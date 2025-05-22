/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.repositories;
import airport.model.Location;
import java.util.*;
/**
 *
 * @author bbelt
 */
public class LocationRepository {
    private final Map<String, Location> airports = new HashMap<>();

    public boolean addAirport(Location airport) {
    if (airport == null || airport.getAirportId() == null) return false;
    if (airports.containsKey(airport.getAirportId())) return false;
    airports.put(airport.getAirportId(), airport);
    return true;
    }

    public Location getAirport(String id) {
        return airports.get(id);
    }

    public List<Location> getAllAirports() {
        List<Location> list = new ArrayList<>(airports.values());
        list.sort(Comparator.comparing(Location::getAirportId));
        return list;
    }

    public boolean exists(String id) {
        return airports.containsKey(id);
    }
}
