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

    public Response<Passenger> getPassengerId(long id) {
        return repo.findById(id)
                .map(p -> new Response<>(StatusCode.SUCCESS, "Encontrado", p.clone()))
                .orElseGet(() -> new Response<>(StatusCode.NOT_FOUND, "Pasajero no encontrado", null));
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

        if (firstname != null && !firstname.isBlank()) {
            p.setFirstname(firstname);
        }
        if (lastname != null && !lastname.isBlank()) {
            p.setLastname(lastname);
        }
        if (birthDate != null) {
            p.setBirthDate(birthDate);
        }
        if (countryPhoneCode > 0) {
            p.setCountryPhoneCode(countryPhoneCode);
        }
        if (phone > 0) {
            p.setPhone(phone);
        }
        if (country != null && !country.isBlank()) {
            p.setCountry(country);
        }

        return new Response<>(StatusCode.SUCCESS, "Pasajero actualizado", p);
    }

    public Response<Passenger> registerPassenger(long id, String firstname, String lastname,
                                                 LocalDate birthDate, int countryPhoneCode,
                                                 long phone, String country) {
        
        if (id <= 0) {
            return new Response<>(StatusCode.FAILURE, "ID inválido.", null);
        }
        if (firstname == null || firstname.isBlank()) {
            return new Response<>(StatusCode.FAILURE, "Nombre inválido.", null);
        }
        if (lastname == null || lastname.isBlank()) {
            return new Response<>(StatusCode.FAILURE, "Apellido inválido.", null);
        }
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            return new Response<>(StatusCode.FAILURE, "Fecha de nacimiento inválida.", null);
        }
        if (countryPhoneCode <= 0) {
            return new Response<>(StatusCode.FAILURE, "Código de país inválido.", null);
        }
        if (phone <= 0) {
            return new Response<>(StatusCode.FAILURE, "Número de teléfono inválido.", null);
        }
        if (country == null || country.isBlank()) {
            return new Response<>(StatusCode.FAILURE, "País inválido.", null);
        }

        
        if (repo.exists(id)) {
            return new Response<>(StatusCode.FAILURE, "El pasajero con ese ID ya existe.", null);
        }

        
        Passenger passenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
        boolean added = repo.addPassenger(passenger);

        if (added) {
            return new Response<>(StatusCode.SUCCESS, "Pasajero registrado con éxito.", passenger);
        } else {
            return new Response<>(StatusCode.FAILURE, "Error al registrar el pasajero.", null);
        }
    }
}


