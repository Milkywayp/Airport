package airport.view.tabs;

import airport.controller.FlightController;
import airport.controller.LocationController;
import airport.controller.PlaneController;
import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Flight;
import airport.model.Location;
import airport.model.Plane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlighTab extends JPanel {

    private final FlightController flightController;
    private final PlaneController planeController;
    private final LocationController locationController;

    private JTextField txtFlightId, txtPlaneId, txtDepartureId, txtArrivalId, txtScaleId, txtFecha, txtHora, txtDuracionVueloHoras, txtDuracionVueloMinutos, txtDuracionEscalaHoras, txtDuracionEscalaMinutos;
    private JButton btnCrearVuelo;
    private JTable tablaVuelos;
    private DefaultTableModel flightTableModel;
    private JLabel lblMensaje;

    public FlighTab(FlightController flightController, PlaneController planeController, LocationController locationController) {
        this.flightController = flightController;
        this.planeController = planeController;
        this.locationController = locationController;
        initUI();
        cargarVuelos();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Crear Vuelo");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 10, 60));

        txtFlightId = new JTextField();
        txtPlaneId = new JTextField();
        txtDepartureId = new JTextField();
        txtArrivalId = new JTextField();
        txtScaleId = new JTextField();
        txtFecha = new JTextField("2025-06-01");
        txtHora = new JTextField("14:30");
        txtDuracionVueloHoras = new JTextField("2");
        txtDuracionVueloMinutos = new JTextField("30");
        txtDuracionEscalaHoras = new JTextField("0");
        txtDuracionEscalaMinutos = new JTextField("0");

        formPanel.add(new JLabel("ID Vuelo:")); formPanel.add(txtFlightId);
        formPanel.add(new JLabel("ID Avión:")); formPanel.add(txtPlaneId);
        formPanel.add(new JLabel("Salida (ID aeropuerto):")); formPanel.add(txtDepartureId);
        formPanel.add(new JLabel("Llegada (ID aeropuerto):")); formPanel.add(txtArrivalId);
        formPanel.add(new JLabel("Escala (opcional):")); formPanel.add(txtScaleId);
        formPanel.add(new JLabel("Fecha (YYYY-MM-DD):")); formPanel.add(txtFecha);
        formPanel.add(new JLabel("Hora (HH:mm):")); formPanel.add(txtHora);
        formPanel.add(new JLabel("Duración vuelo (h y min):"));
        JPanel durVueloPanel = new JPanel(new GridLayout(1, 2));
        durVueloPanel.add(txtDuracionVueloHoras);
        durVueloPanel.add(txtDuracionVueloMinutos);
        formPanel.add(durVueloPanel);
        formPanel.add(new JLabel("Duración escala (h y min):"));
        JPanel durEscalaPanel = new JPanel(new GridLayout(1, 2));
        durEscalaPanel.add(txtDuracionEscalaHoras);
        durEscalaPanel.add(txtDuracionEscalaMinutos);
        formPanel.add(durEscalaPanel);

        btnCrearVuelo = new JButton("Crear Vuelo");
        formPanel.add(btnCrearVuelo);

        lblMensaje = new JLabel("");
        formPanel.add(lblMensaje);

        add(formPanel, BorderLayout.CENTER);

        flightTableModel = new DefaultTableModel(new Object[]{"ID", "Avión", "Salida", "Llegada", "Fecha Salida"}, 0);
        tablaVuelos = new JTable(flightTableModel);
        JScrollPane scrollPane = new JScrollPane(tablaVuelos);
        add(scrollPane, BorderLayout.SOUTH);

        btnCrearVuelo.addActionListener(e -> crearVuelo());
    }

    private void crearVuelo() {
        try {
            String flightId = txtFlightId.getText().trim();
            String planeId = txtPlaneId.getText().trim();
            String departureId = txtDepartureId.getText().trim();
            String arrivalId = txtArrivalId.getText().trim();
            String scaleId = txtScaleId.getText().trim();
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();

            LocalDateTime fechaSalida = LocalDateTime.parse(fecha + "T" + hora);
            int durVueloHoras = Integer.parseInt(txtDuracionVueloHoras.getText().trim());
            int durVueloMinutos = Integer.parseInt(txtDuracionVueloMinutos.getText().trim());
            int durEscalaHoras = Integer.parseInt(txtDuracionEscalaHoras.getText().trim());
            int durEscalaMinutos = Integer.parseInt(txtDuracionEscalaMinutos.getText().trim());

            Response<Flight> response = flightController.createFlight(
                flightId, planeId,
                departureId, scaleId, arrivalId,
                fechaSalida, durVueloHoras * 60 + durVueloMinutos, durEscalaHoras * 60 + durEscalaMinutos
            );

            if (response.getStatusCode() == StatusCode.SUCCESS) {
                lblMensaje.setText("Vuelo creado correctamente");
                lblMensaje.setForeground(Color.GREEN);
                agregarVueloATabla(response.getData());
            } else {
                lblMensaje.setText("Error: " + response.getMessage());
                lblMensaje.setForeground(Color.RED);
            }
        } catch (Exception ex) {
            lblMensaje.setText("Error en los datos: " + ex.getMessage());
            lblMensaje.setForeground(Color.RED);
        }
    }

    private void cargarVuelos() {
        Response<List<Flight>> response = flightController.getAllFlights();
        if (response.getStatusCode() == StatusCode.SUCCESS) {
            for (Flight f : response.getData()) {
                agregarVueloATabla(f);
            }
        }
    }

    private void agregarVueloATabla(Flight f) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        flightTableModel.addRow(new Object[]{
                f.getId(),
                f.getPlane().getId(),
                f.getDepartureLocation().getAirportId(),
                f.getArrivalLocation().getAirportId(),
                f.getDepartureDate().format(fmt)
        });
    }
}

