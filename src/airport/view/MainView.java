package airport.view;

import airport.controller.*;
import airport.model.repositories.*;
import airport.view.tabs.*;

import javax.swing.*;
import java.awt.*;

public class MainView {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Airport System");
            frame.setSize(1000, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            
            PassengerRepository passengerRepo = new PassengerRepository();
            PlaneRepository planeRepo = new PlaneRepository();
            LocationRepository locationRepo = new LocationRepository();
            FlightRepository flightRepo = new FlightRepository();

            
            PassengerController passengerController = new PassengerController(passengerRepo);
            PlaneController planeController = new PlaneController(planeRepo);
            LocationController locationController = new LocationController(locationRepo);
            FlightController flightController = new FlightController(
                flightRepo, planeRepo, locationRepo, passengerRepo
            );

            
            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Pasajeros", new PassengerTab(passengerController));
            tabs.addTab("Aviones", new PlaneTab(planeController));
            tabs.addTab("Localizaciones", new LocationTab(locationController));
            tabs.addTab("Vuelos", new FlighTab(flightController, planeController, locationController));
            tabs.addTab("Add to Flight", new AddToFlightTab(flightController, passengerController));

            
            frame.add(tabs, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);
        });
    }
}


