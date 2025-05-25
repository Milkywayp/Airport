package airport.view.tabs;

import airport.controller.FlightController;
import airport.controller.PassengerController;
import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Flight;
import airport.model.Passenger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddToFlightTab extends JPanel {

    private final FlightController flightController;
    private final PassengerController passengerController;

    private JComboBox<String> flightBox;
    private JComboBox<String> passengerBox;
    private JButton btnAdd;
    private JLabel lblMensaje;

    public AddToFlightTab(FlightController flightController, PassengerController passengerController) {
        this.flightController = flightController;
        this.passengerController = passengerController;
        initUI();
        cargarVuelosYPasajeros();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Agregar Pasajero a Vuelo");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        formPanel.add(new JLabel("Vuelo (ID):"));
        flightBox = new JComboBox<>();
        formPanel.add(flightBox);

        formPanel.add(new JLabel("Pasajero (ID - Nombre):"));
        passengerBox = new JComboBox<>();
        formPanel.add(passengerBox);

        btnAdd = new JButton("Agregar al Vuelo");
        formPanel.add(btnAdd);

        lblMensaje = new JLabel("");
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblMensaje);

        add(formPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> agregarPasajero());
    }

    private void cargarVuelosYPasajeros() {
        // Cargar vuelos
        Response<List<Flight>> vuelosResp = flightController.getAllFlights();
        if (vuelosResp.getStatusCode() == StatusCode.SUCCESS) {
            for (Flight f : vuelosResp.getData()) {
                flightBox.addItem(f.getId());
            }
        }

        // Cargar pasajeros
        Response<List<Passenger>> pasResp = passengerController.getAllPassengers();
        if (pasResp.getStatusCode() == StatusCode.SUCCESS) {
            for (Passenger p : pasResp.getData()) {
                passengerBox.addItem(p.getId() + " - " + p.getFullname());
            }
        }
    }

    private void agregarPasajero() {
        String vueloId = (String) flightBox.getSelectedItem();
        String pasajeroSeleccionado = (String) passengerBox.getSelectedItem();

        if (vueloId == null || pasajeroSeleccionado == null) {
            lblMensaje.setText("Selecciona un vuelo y un pasajero");
            lblMensaje.setForeground(Color.RED);
            return;
        }

        long pasajeroId = Long.parseLong(pasajeroSeleccionado.split(" - ")[0]);

        Response<Flight> response = flightController.addPassengerToFlight(vueloId, pasajeroId);

        if (response.getStatusCode() == StatusCode.SUCCESS) {
            lblMensaje.setText("Pasajero añadido correctamente");
            lblMensaje.setForeground(Color.GREEN);
        } else {
            lblMensaje.setText("Error: " + response.getMessage());
            lblMensaje.setForeground(Color.RED);
        }
    }
}

