package airport.model.repositories;

import airport.model.Plane;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class PlaneRepository {

    private final Map<String, Plane> planes = new HashMap<>();

    public boolean addPlane(Plane plane) {
        if (planes.containsKey(plane.getId())) {
            return false; // Ya existe
        }
        planes.put(plane.getId(), plane);
        return true;
    }

    public Plane getPlane(String id) {
        return planes.get(id);
    }

    public List<Plane> getAllPlanes() {
        return new ArrayList<>(planes.values());
    }

    public boolean exists(String id) {
        return planes.containsKey(id);
    }

    public boolean removePlane(String id) {
        if (!planes.containsKey(id)) return false;
        planes.remove(id);
        return true;
    }

    public void clear() {
        planes.clear();
    }
}

