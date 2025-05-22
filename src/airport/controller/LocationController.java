package airport.controller;

import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Location;
import airport.model.repositories.LocationRepository;
import airport.view.utils.Validator;
import java.util.List;
import java.util.stream.Collectors;

public class LocationController {
    private final LocationRepository repo;

    public LocationController(LocationRepository repo) {
        this.repo = repo;
    }

    public Response<Location> createLocation(String id, String name, String city, String country, double lat, double lon) {
        if (!Validator.isValidAirportId(id))
            return new Response<>(StatusCode.BAD_REQUEST, "ID inválido", null);
        if (!Validator.isValidCoordinates(lat, lon))
            return new Response<>(StatusCode.BAD_REQUEST, "Coordenadas inválidas", null);
        if (name == null || name.isBlank() ||
            city == null || city.isBlank() ||
            country == null || country.isBlank()) {
            return new Response<>(StatusCode.BAD_REQUEST, "Nombre, ciudad o país inválidos", null);
        }

        Location location = new Location(id, name, city, country, lat, lon);
        boolean success = repo.addAirport(location);

        if (!success)
            return new Response<>(StatusCode.BAD_REQUEST, "La localización ya existe", null);

        return new Response<>(StatusCode.SUCCESS, "Localización creada", location.copy());
    }

    public Response<Location> getLocationById(String id) {
        if (id == null || id.isBlank())
            return new Response<>(StatusCode.BAD_REQUEST, "ID nulo o vacío", null);

        Location location = repo.getAirport(id);

        if (location == null)
            return new Response<>(StatusCode.NOT_FOUND, "Localización no encontrada", null);

        return new Response<>(StatusCode.SUCCESS, "Localización encontrada", location.copy());
    }

    public Response<List<Location>> getAllLocations() {
        List<Location> locations = repo.getAllAirports()
            .stream()
            .map(Location::copy)
            .collect(Collectors.toList());

        return new Response<>(StatusCode.SUCCESS, "Lista de localizaciones", locations);
    }

    public Response<Location> updateLocation(String id, String name, String city, double lat, double lon) {
        if (!repo.exists(id))
            return new Response<>(StatusCode.NOT_FOUND, "Localización no encontrada", null);
        if (!Validator.isValidCoordinates(lat, lon))
            return new Response<>(StatusCode.BAD_REQUEST, "Coordenadas inválidas", null);
        if (name == null || name.isBlank() || city == null || city.isBlank()) {
            return new Response<>(StatusCode.BAD_REQUEST, "Nombre o ciudad inválidos", null);
        }

        Location location = repo.getAirport(id);
        location.setName(name);
        location.setCity(city);
        location.setLatitude(lat);
        location.setLongitude(lon);

        return new Response<>(StatusCode.SUCCESS, "Localización actualizada", location.copy());
    }
}

