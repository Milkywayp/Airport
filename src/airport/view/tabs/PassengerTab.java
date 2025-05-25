package airport.view.tabs;

import airport.controller.PassengerController;
import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Passenger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PassengerTab extends JPanel {

    private final PassengerController controller;

    private JTextField txtId, txtNombre, txtApellido, txtFechaNac, txtCodigoPais, txtTelefono, txtPais;
    private JButton btnRegistrar;
    private JTable tblPasajeros;
    private DefaultTableModel tableModel;
    private JLabel lblMensaje;

    public PassengerTab(PassengerController controller) {
        this.controller = controller;
        initUI();
        cargarPasajeros();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Registrar Pasajero");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Formulario
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtFechaNac = new JTextField("1990-01-01");
        txtCodigoPais = new JTextField("57");
        txtTelefono = new JTextField("3001234567");
        txtPais = new JTextField("Colombia");

        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Apellido:"));
        formPanel.add(txtApellido);
        formPanel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):"));
        formPanel.add(txtFechaNac);
        formPanel.add(new JLabel("Código País (Ej: 57):"));
        formPanel.add(txtCodigoPais);
        formPanel.add(new JLabel("Teléfono (Ej: 3001234567):"));
        formPanel.add(txtTelefono);
        formPanel.add(new JLabel("País:"));
        formPanel.add(txtPais);

        btnRegistrar = new JButton("Registrar Pasajero");
        formPanel.add(btnRegistrar);

        lblMensaje = new JLabel("");
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblMensaje);

        add(formPanel, BorderLayout.CENTER);

        // Tabla
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido"}, 0);
        tblPasajeros = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblPasajeros);
        add(scrollPane, BorderLayout.SOUTH);

        // Acción del botón
        btnRegistrar.addActionListener(e -> registrarPasajero());
    }

    private void registrarPasajero() {
        try {
            long id = Long.parseLong(txtId.getText().trim());
            String firstname = txtNombre.getText().trim();
            String lastname = txtApellido.getText().trim();
            LocalDate birthDate = LocalDate.parse(txtFechaNac.getText().trim());
            int countryPhoneCode = Integer.parseInt(txtCodigoPais.getText().trim());
            long phone = Long.parseLong(txtTelefono.getText().trim());
            String country = txtPais.getText().trim();

            Response<Passenger> response = controller.registerPassenger(
                    id, firstname, lastname, birthDate, countryPhoneCode, phone, country);

            if (response.getStatusCode() == StatusCode.SUCCESS) {
                lblMensaje.setText("Pasajero registrado con éxito.");
                lblMensaje.setForeground(Color.GREEN);
                limpiarCampos();
                agregarPasajeroATabla(response.getData());
            } else {
                lblMensaje.setText("Error: " + response.getMessage());
                lblMensaje.setForeground(Color.RED);
            }
        } catch (NumberFormatException | DateTimeParseException ex) {
            lblMensaje.setText("Error en los datos ingresados: " + ex.getMessage());
            lblMensaje.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtFechaNac.setText("1990-01-01");
        txtCodigoPais.setText("57");
        txtTelefono.setText("3001234567");
        txtPais.setText("Colombia");
    }

    private void cargarPasajeros() {
        Response<List<Passenger>> response = controller.getAllPassengers();
        if (response.getStatusCode() == StatusCode.SUCCESS) {
            for (Passenger p : response.getData()) {
                agregarPasajeroATabla(p);
            }
        }
    }

    private void agregarPasajeroATabla(Passenger p) {
        tableModel.addRow(new Object[]{p.getId(), p.getFirstname(), p.getLastname()});
    }
}
