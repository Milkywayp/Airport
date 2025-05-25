package airport.view.tabs;

import airport.controller.LocationController;
import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Location;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LocationTab extends JPanel {

    private LocationController controller;
    private JTable locationTable;
    private DefaultTableModel locationTableModel;

    public LocationTab(LocationController controller) {
        this.controller = controller;
        initComponents();
        loadLocations();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Columnas basadas en atributos de Location (ID, name, city, country, latitude, longitude)
        String[] columns = {
            "ID", "Nombre", "Ciudad", "País", "Latitud", "Longitud"
        };

        locationTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        locationTable = new JTable(locationTableModel);
        locationTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(locationTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadLocations() {
        locationTableModel.setRowCount(0);

        Response<List<Location>> response = controller.getAllLocations();

        if (response.getStatusCode() == StatusCode.SUCCESS) {
            List<Location> locations = response.getData();

            for (Location loc : locations) {
                locationTableModel.addRow(new Object[]{
                    loc.getAirportId(),
                    loc.getAirportName(),
                    loc.getAirportCity(),
                    loc.getAirportCountry(),
                    loc.getAirportLatitude(),
                    loc.getAirportLongitude()
                });
            }

            JOptionPane.showMessageDialog(this,
                "Lista de localizaciones cargada correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this,
                "Error al cargar localizaciones: " + response.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

}

