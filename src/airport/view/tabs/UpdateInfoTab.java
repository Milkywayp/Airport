package airport.view.tabs;

import airport.controller.PassengerController;
import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Passenger;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class UpdateInfoTab extends JPanel {

    private final PassengerController controller;

    private JTextField txtIdBuscar, txtNombre, txtApellido, txtFechaNac, txtCodigoPais, txtTelefono, txtPais;
    private JButton btnBuscar, btnActualizar;
    private JLabel lblMensaje;

    public UpdateInfoTab(PassengerController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Actualizar Información de Pasajero");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        txtIdBuscar = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtFechaNac = new JTextField();
        txtCodigoPais = new JTextField();
        txtTelefono = new JTextField();
        txtPais = new JTextField();

        formPanel.add(new JLabel("ID del pasajero a buscar:"));
        formPanel.add(txtIdBuscar);

        btnBuscar = new JButton("Buscar");
        formPanel.add(btnBuscar);
        formPanel.add(new JLabel()); // vacío

        formPanel.add(new JLabel("Nombre:")); formPanel.add(txtNombre);
        formPanel.add(new JLabel("Apellido:")); formPanel.add(txtApellido);
        formPanel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):")); formPanel.add(txtFechaNac);
        formPanel.add(new JLabel("Código País:")); formPanel.add(txtCodigoPais);
        formPanel.add(new JLabel("Teléfono:")); formPanel.add(txtTelefono);
        formPanel.add(new JLabel("País:")); formPanel.add(txtPais);

        btnActualizar = new JButton("Actualizar Datos");
        formPanel.add(btnActualizar);

        lblMensaje = new JLabel("");
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblMensaje);

        add(formPanel, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarPasajero());
        btnActualizar.addActionListener(e -> actualizarPasajero());
    }

    private void buscarPasajero() {
        try {
            long id = Long.parseLong(txtIdBuscar.getText().trim());
            Response<Passenger> response = controller.getPassengerId(id);
            if (response.getStatusCode() == StatusCode.SUCCESS) {
                Passenger p = response.getData();
                txtNombre.setText(p.getFirstname());
                txtApellido.setText(p.getLastname());
                txtFechaNac.setText(p.getBirthDate().toString());
                txtCodigoPais.setText(String.valueOf(p.getCountryPhoneCode()));
                txtTelefono.setText(String.valueOf(p.getPhone()));
                txtPais.setText(p.getCountry());
                lblMensaje.setText("Pasajero encontrado");
                lblMensaje.setForeground(Color.BLUE);
            } else {
                lblMensaje.setText("No encontrado: " + response.getMessage());
                lblMensaje.setForeground(Color.RED);
            }
        } catch (Exception ex) {
            lblMensaje.setText("Error: " + ex.getMessage());
            lblMensaje.setForeground(Color.RED);
        }
    }

    private void actualizarPasajero() {
        try {
            long id = Long.parseLong(txtIdBuscar.getText().trim());
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            LocalDate fechaNac = LocalDate.parse(txtFechaNac.getText().trim());
            int codPais = Integer.parseInt(txtCodigoPais.getText().trim());
            long telefono = Long.parseLong(txtTelefono.getText().trim());
            String pais = txtPais.getText().trim();

            Response<Passenger> response = controller.updatePassenger(id, nombre, apellido, fechaNac, codPais, telefono, pais);
            if (response.getStatusCode() == StatusCode.SUCCESS) {
                lblMensaje.setText("Datos actualizados correctamente");
                lblMensaje.setForeground(Color.GREEN);
            } else {
                lblMensaje.setText("Error al actualizar: " + response.getMessage());
                lblMensaje.setForeground(Color.RED);
            }
        } catch (Exception ex) {
            lblMensaje.setText("Error: " + ex.getMessage());
            lblMensaje.setForeground(Color.RED);
        }
    }
}
