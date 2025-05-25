package airport.controller;

import airport.core.Response;
import airport.core.StatusCode;
import airport.model.*;
import airport.model.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightController {
    private final FlightRepository flightRepo;
    private final PlaneRepository planeRepo;
    private final LocationRepository locationRepo;
    private final PassengerRepository passengerRepo;

    public FlightController(FlightRepository flightRepo, PlaneRepository planeRepo, LocationRepository locationRepo, PassengerRepository passengerRepo) {
        this.flightRepo = flightRepo;
        this.planeRepo = planeRepo;
        this.locationRepo = locationRepo;
        this.passengerRepo = passengerRepo;
    }

    public Response<Flight> createFlight(String id, String planeId, String departureId, String scaleId,String arrivalId,
                                         LocalDateTime departureDate, int hArrival, int mArrival) {
        
        if (flightRepo.exists(id))
            return new Response<>(StatusCode.BAD_REQUEST, "El vuelo ya existe", null);

        Plane plane = planeRepo.getPlane(planeId);
        Location departure = locationRepo.getAirport(departureId);
        Location arrival = locationRepo.getAirport(arrivalId);

        if (plane == null || departure == null || arrival == null)
            return new Response<>(StatusCode.NOT_FOUND, "Datos del vuelo no válidos", null);

        Flight flight = new Flight(id, plane, departure, arrival, departureDate, hArrival, mArrival);
        flightRepo.addFlight(flight);
        return new Response<>(StatusCode.SUCCESS, "Vuelo creado", flight);
    }

    public Response<Flight> createFlightWithScale(String id, String planeId, String departureId, String scaleId, String arrivalId,
                                                  LocalDateTime departureDate, int hArrival, int mArrival,
                                                  int hScale, int mScale) {
        if (flightRepo.exists(id))
            return new Response<>(StatusCode.BAD_REQUEST, "El vuelo ya existe", null);

        Plane plane = planeRepo.getPlane(planeId);
        Location departure = locationRepo.getAirport(departureId);
        Location scale = locationRepo.getAirport(scaleId);
        Location arrival = locationRepo.getAirport(arrivalId);

        if (plane == null || departure == null || scale == null || arrival == null)
            return new Response<>(StatusCode.NOT_FOUND, "Datos del vuelo no válidos", null);

        Flight flight = new Flight(id, plane, departure, scale, arrival, departureDate, hArrival, mArrival, hScale, mScale);
        flightRepo.addFlight(flight);
        return new Response<>(StatusCode.SUCCESS, "Vuelo con escala creado", flight);
    }

    public Response<List<Flight>> getAllFlights() {
        List<Flight> list = flightRepo.getAllFlights().stream().collect(Collectors.toList());
        return new Response<>(StatusCode.SUCCESS, "Lista de vuelos", list);
    }

    public Response<Flight> getFlight(String id) {
        Flight flight = flightRepo.getFlight(id);
        if (flight == null)
            return new Response<>(StatusCode.NOT_FOUND, "Vuelo no encontrado", null);
        return new Response<>(StatusCode.SUCCESS, "Vuelo encontrado", flight);
    }

    public Response<Flight> addPassengerToFlight(String flightId, long passengerId) {
        Flight flight = flightRepo.getFlight(flightId);
        Passenger passenger = passengerRepo.getPassenger(passengerId);

        if (flight == null || passenger == null)
            return new Response<>(StatusCode.NOT_FOUND, "Vuelo o pasajero no encontrado", null);

        flight.addPassenger(passenger);
        passenger.addFlight(flight);
        return new Response<>(StatusCode.SUCCESS, "Pasajero agregado al vuelo", flight);
    }

    public Response<Flight> delayFlight(String id, int hours, int minutes) {
        Flight flight = flightRepo.getFlight(id);
        if (flight == null)
            return new Response<>(StatusCode.NOT_FOUND, "Vuelo no encontrado", null);

        flight.delay(hours, minutes);
        return new Response<>(StatusCode.SUCCESS, "Vuelo retrasado", flight);
    }
}

