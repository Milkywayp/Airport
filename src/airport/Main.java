package airport;

import airport.controller.*;
import airport.model.Flight;
import airport.model.Location;
import airport.model.Passenger;
import airport.model.Plane;
import airport.model.repositories.*;
import airport.view.AirportFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        // Mostrar datos cargados en consola
        PassengerRepository passengerRepo = new PassengerRepository();
        for (Passenger p : passengerRepo.getAllPassengers()) {
            System.out.println(p);
        }

        PlaneRepository planeRepo = new PlaneRepository();
        for (Plane plane : planeRepo.getAllPlanes()) {
            System.out.println(plane);
        }

        LocationRepository locationRepo = new LocationRepository();
        for (Location loc : locationRepo.getAllAirports()) {
            System.out.println(loc);
        }

        FlightRepository flightRepo = new FlightRepository();
        for (Flight f : flightRepo.getAllFlights()) {
            System.out.println(f);
        }

        // Crear controladores
        PassengerController passengerController = new PassengerController(passengerRepo);
        PlaneController planeController = new PlaneController(planeRepo);
        LocationController locationController = new LocationController(locationRepo);
        FlightController flightController = new FlightController(flightRepo, planeRepo, locationRepo, passengerRepo);

        // Mostrar ventana principal pasando los controladores
        SwingUtilities.invokeLater(() -> {
            AirportFrame frame = new AirportFrame(
                passengerController,
                planeController,
                locationController,
                flightController
            );
            frame.setVisible(true);
        });
    }
}


