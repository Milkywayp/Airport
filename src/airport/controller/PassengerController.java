package airport.controller;

import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Passenger;
import airport.model.repositories.PassengerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PassengerController {
    private final PassengerRepository repo;

    public PassengerController(PassengerRepository repo) {
        this.repo = repo;
    }

    public Response<Passenger> registerPassenger(long id, String firstname, String lastname,
                                                 LocalDate birthDate, int countryPhoneCode,
                                                 long phone, String country) {
        if (firstname == null || firstname.isBlank() ||
            lastname == null || lastname.isBlank() ||
            birthDate == null || country == null || country.isBlank()) {
            return new Response<>(StatusCode.BAD_REQUEST, "Datos inválidos", null);
        }

        if (repo.getPassenger(id) != null) {
            return new Response<>(StatusCode.BAD_REQUEST, "Pasajero ya registrado", null);
        }

        Passenger passenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
        repo.addPassenger(passenger);

        return new Response<>(StatusCode.SUCCESS, "Pasajero registrado", passenger);
    }

    public Response<List<Passenger>> getAllPassengers() {
        List<Passenger> list = repo.getAllPassengers()
                                   .stream()
                                   .collect(Collectors.toList());
        return new Response<>(StatusCode.SUCCESS, "Lista de pasajeros", list);
    }

    public Response<Passenger> updatePassenger(long id, String firstname, String lastname,
                                               LocalDate birthDate, int countryPhoneCode,
                                               long phone, String country) {
        Passenger p = repo.getPassenger(id);
        if (p == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Pasajero no encontrado", null);
        }

        if (firstname != null && !firstname.isBlank()) p.setFirstname(firstname);
        if (lastname != null && !lastname.isBlank()) p.setLastname(lastname);
        if (birthDate != null) p.setBirthDate(birthDate);
        if (countryPhoneCode > 0) p.setCountryPhoneCode(countryPhoneCode);
        if (phone > 0) p.setPhone(phone);
        if (country != null && !country.isBlank()) p.setCountry(country);

        return new Response<>(StatusCode.SUCCESS, "Pasajero actualizado", p);
    }
}

