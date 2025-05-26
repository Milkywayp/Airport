package airport;

import airport.controller.*;
import airport.model.Flight;
import airport.model.Location;
import airport.model.Passenger;
import airport.model.Plane;
import airport.model.repositories.*;
import airport.view.tabs.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Mostrar en consola los datos cargados desde JSON
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

        // Crear GUI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Airport System");
            frame.setSize(1000, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Crear controladores
            PassengerController passengerController = new PassengerController(passengerRepo);
            PlaneController planeController = new PlaneController(planeRepo);
            LocationController locationController = new LocationController(locationRepo);
            FlightController flightController = new FlightController(flightRepo, planeRepo, locationRepo, passengerRepo);

            // Crear tabs y pasar controladores
            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Pasajeros", new PassengerTab(passengerController));
            tabs.addTab("Aviones", new PlaneTab(planeController));
            tabs.addTab("Localizaciones", new LocationTab(locationController));
            tabs.addTab("Vuelos", new FlighTab(flightController, planeController, locationController));
            tabs.addTab("Add to Flight", new AddToFlightTab(flightController, passengerController));

            frame.add(tabs, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null); // Centrar ventana
            frame.setVisible(true);
        });
    }
}

