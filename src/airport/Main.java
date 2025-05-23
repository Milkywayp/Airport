/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport;
import airport.model.Flight;
import airport.model.Location;
import airport.model.Passenger;
import airport.model.Plane;
import airport.model.repositories.FlightRepository;
import airport.model.repositories.LocationRepository;
import airport.model.repositories.PassengerRepository;
import airport.model.repositories.PlaneRepository;
import airport.view.MainView;
/**
 *
 * @author bbelt
 */
public class Main {
    public static void main(String[] args) {
        MainView.main(args);
          PassengerRepository rep = new PassengerRepository();

            for (Passenger p : rep.getAllPassengers()) {
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



            
    }
}
