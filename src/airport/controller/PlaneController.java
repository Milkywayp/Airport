package airport.controller;

import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Plane;
import airport.model.repositories.PlaneRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PlaneController {

    private final PlaneRepository repo;

    public PlaneController(PlaneRepository repo) {
        this.repo = repo;
    }

    public Response<Plane> registerPlane(String id, String brand, String model, int maxCapacity, String airline) {
        if (id == null || id.isBlank() || brand == null || brand.isBlank() ||
            model == null || model.isBlank() || airline == null || airline.isBlank() ||
            maxCapacity <= 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "Datos inválidos", null);
        }

        if (repo.exists(id)) {
            return new Response<>(StatusCode.BAD_REQUEST, "El avión ya existe", null);
        }

        Plane plane = new Plane(id, brand, model, maxCapacity, airline);
        repo.addPlane(plane);

        return new Response<>(StatusCode.SUCCESS, "Avión registrado", plane);
    }

    public Response<List<Plane>> getAllPlanes() {
        List<Plane> planes = repo.getAllPlanes()
            .stream()
            .map(p -> new Plane(p.getId(), p.getBrand(), p.getModel(), p.getMaxCapacity(), p.getAirline()))
            .collect(Collectors.toList());

        return new Response<>(StatusCode.SUCCESS, "Lista de aviones", planes);
    }

    public Response<Plane> getPlane(String id) {
        Plane plane = repo.getPlane(id);
        if (plane == null)
            return new Response<>(StatusCode.NOT_FOUND, "Avión no encontrado", null);

        return new Response<>(StatusCode.SUCCESS, "Avión encontrado", plane);
    }
}


